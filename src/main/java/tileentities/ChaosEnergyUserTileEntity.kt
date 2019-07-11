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
	private val chaosTier: ChaosTier
) : TileEntity(), ITickable, IWailaDataProvider, ITileEntityHasChaosTier {
	companion object {
		@JvmStatic
		@CapabilityInject(IChaosEnergyStorage::class)
		lateinit var capChaosEnergyStorage: Capability<IChaosEnergyStorage>
	}

	private val chaosEnergyStorage = ChaosEnergyStorage()
	private var chaosEnergyStored = 0
	private var chaosEnergyCapacity = 0

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

	class ChaosEnergyStorageMessageHandler : IMessageHandler<ChaosEnergyStorageMessage, IMessage> {
		override fun onMessage(message: ChaosEnergyStorageMessage?, ctx: MessageContext?): IMessage? {
			val minecraft = Minecraft.getMinecraft()
			val world = minecraft.world

			if (world.isRemote) {
				minecraft.addScheduledTask {
					if (message!!.identifier == 1 && world.isBlockLoaded(message.blockPos)) {
						val tileEntity = world.getTileEntity(message.blockPos)

						if (tileEntity is ChaosEnergyUserTileEntity) {
							val chaosEnergyStorage =
								tileEntity.getCapability(capChaosEnergyStorage, null)!!

							chaosEnergyStorage.chaosEnergyStored = message.chaosEnergyStored
							chaosEnergyStorage.chaosEnergyCapacity = message.chaosEnergyCapacity
						}
					}
				}
			}

			return null
		}
	}

	override fun onLoad() {
		super.onLoad()

		networkChannel.registerMessage(
			ChaosEnergyStorageMessageHandler::class.java,
			ChaosEnergyStorageMessage::class.java,
			Chaos.NetworkDiscriminators.ChaosEnergyStorage.ordinal,
			Side.CLIENT
		)

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