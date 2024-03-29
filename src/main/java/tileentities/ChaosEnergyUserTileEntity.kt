package com.famous5000.chaos.tileentities

import com.famous5000.chaos.Chaos
import com.famous5000.chaos.Chaos.networkChannel
import com.famous5000.chaos.capability.ChaosEnergyStorage
import com.famous5000.chaos.capability.IChaosEnergyStorage
import com.famous5000.chaos.enums.ChaosTier
import com.famous5000.chaos.interfaces.ITileEntityHasChaosTier
import com.famous5000.chaos.interfaces.IWailaDataProvider
import com.famous5000.chaos.messages.ChaosEnergyStorageMessage
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side

open class ChaosEnergyUserTileEntity(
	private val chaosTier: ChaosTier,
	private var chaosEnergyStored: Int = 0,
	private var chaosEnergyCapacity: Int = 0
) : TileEntity(), ITickable, IWailaDataProvider, ITileEntityHasChaosTier {
	companion object {
		@JvmStatic
		@CapabilityInject(IChaosEnergyStorage::class)
		lateinit var capChaosEnergyStorage: Capability<IChaosEnergyStorage>

		class ChaosEnergyStorageMessageHandler : IMessageHandler<ChaosEnergyStorageMessage, IMessage> {
			override fun onMessage(message: ChaosEnergyStorageMessage?, ctx: MessageContext?): IMessage? {
				val minecraft = Minecraft.getMinecraft()
				val world = minecraft.world

				if (!world.isRemote || message !is ChaosEnergyStorageMessage) return null

				minecraft.addScheduledTask {
					if (!world.isBlockLoaded(message.blockPos)) return@addScheduledTask

					(world.getTileEntity(message.blockPos) as? ChaosEnergyUserTileEntity)
						?.getCapability(capChaosEnergyStorage, null)
						?.let {
							it.chaosEnergyStored = message.chaosEnergyStored
							it.chaosEnergyCapacity = message.chaosEnergyCapacity
						}
				}

				return null
			}
		}

		init {
			networkChannel.registerMessage(
				ChaosEnergyStorageMessageHandler::class.java,
				ChaosEnergyStorageMessage::class.java,
				Chaos.NetworkDiscriminators.ChaosEnergyStorage.ordinal,
				Side.CLIENT
			)
		}
	}

	private val chaosEnergyStorage = ChaosEnergyStorage(chaosEnergyStored, chaosEnergyCapacity)

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?) =
		capability == capChaosEnergyStorage

	@Suppress("unchecked_cast")
	override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?) =
		if (capability == capChaosEnergyStorage) chaosEnergyStorage as T
		else null

	override fun serializeNBT(): NBTTagCompound = chaosEnergyStorage.serializeNBT()
	override fun deserializeNBT(nbt: NBTTagCompound?) {
		chaosEnergyStorage.deserializeNBT(nbt)
	}

	override fun getUpdateTag() = chaosEnergyStorage.serializeNBT()
	override fun handleUpdateTag(tag: NBTTagCompound) {
		chaosEnergyStorage.deserializeNBT(tag)
	}

	private fun sendEnergyStorageMessage() {
		networkChannel.sendToAllTracking(
			ChaosEnergyStorageMessage(pos, chaosEnergyStorage),
			NetworkRegistry.TargetPoint(
				world.provider.dimension,
				pos.x.toDouble(),
				pos.y.toDouble(),
				pos.z.toDouble(),
				.0
			)
		)
	}

	private fun updateEnergyVariables() {
		chaosEnergyStored = chaosEnergyStorage.chaosEnergyStored
		chaosEnergyCapacity = chaosEnergyStorage.chaosEnergyCapacity

		sendEnergyStorageMessage()
	}

	override fun onLoad() {
		super.onLoad()

		if (!world.isRemote) {
			updateEnergyVariables()
		}
	}

	override fun update() {
		if (!world.isRemote &&
		    chaosEnergyStorage.chaosEnergyStored != chaosEnergyStored ||
		    chaosEnergyStorage.chaosEnergyCapacity != chaosEnergyCapacity) {
			updateEnergyVariables()
		}
	}

	override fun getWailaBody(itemStack: ItemStack, tooltip: MutableList<String>, accessor: IWailaDataAccessor,
	                          config: IWailaConfigHandler): MutableList<String> {
		tooltip.add("Chaos Energy: $chaosEnergyStorage")

		return tooltip
	}

	override fun getChaosTier(): ChaosTier = chaosTier
}