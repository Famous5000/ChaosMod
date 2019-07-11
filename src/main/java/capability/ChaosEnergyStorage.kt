package com.famous5000.chaos.capability

import com.famous5000.chaos.interfaces.IRegistersSelf
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager

open class ChaosEnergyStorage : IChaosEnergyStorage {
	companion object : IRegistersSelf {
		override fun register() {
			CapabilityManager.INSTANCE.register(
				IChaosEnergyStorage::class.java,
				object : Capability.IStorage<IChaosEnergyStorage> {
					override fun writeNBT(capability: Capability<IChaosEnergyStorage>?,
					                      instance: IChaosEnergyStorage?, side: EnumFacing?): NBTBase? {
						return instance?.serializeNBT()
					}

					override fun readNBT(capability: Capability<IChaosEnergyStorage>?,
					                     instance: IChaosEnergyStorage?, side: EnumFacing?, nbt: NBTBase?) {
						if (nbt is NBTTagCompound) {
							instance?.deserializeNBT(nbt)
						}
					}
				}
			) { ChaosEnergyStorage() }
		}
	}

	override var chaosEnergyCapacity = 0
	override var chaosEnergyStored = 0

	override fun getEnergyStored() = chaosEnergyStored
	override fun getMaxEnergyStored(): Int = chaosEnergyCapacity

	override fun canExtract() = chaosEnergyStored > 0
	override fun extractEnergy(maxExtract: Int, simulate: Boolean) =
		if (canExtract()) {
			if (chaosEnergyStored <= maxExtract) {
				val previousChaosEnergy = chaosEnergyStored
				if (!simulate) chaosEnergyStored = 0
				previousChaosEnergy
			} else {
				if (!simulate) chaosEnergyStored -= maxExtract
				maxExtract
			}
		} else 0

	override fun canReceive() = true
	override fun receiveEnergy(maxReceive: Int, simulate: Boolean) =
		if (canReceive()) {
			val remainingEnergyStorage = chaosEnergyCapacity - chaosEnergyStored

			if (remainingEnergyStorage > maxReceive) {
				if (!simulate) chaosEnergyStored += maxReceive
				maxReceive
			} else {
				if (!simulate) chaosEnergyStored = chaosEnergyCapacity
				remainingEnergyStorage
			}
		} else 0

	override fun serializeNBT(): NBTTagCompound {
		val tagCompound = NBTTagCompound()
		tagCompound.setInteger("chaosEnergyCapacity", chaosEnergyCapacity)
		tagCompound.setInteger("chaosEnergyStored", chaosEnergyStored)

		return tagCompound
	}

	override fun deserializeNBT(nbt: NBTTagCompound?) {
		if (nbt is NBTTagCompound) {
			chaosEnergyCapacity = nbt.getInteger("chaosEnergyCapacity")
			chaosEnergyStored = nbt.getInteger("chaosEnergyStored")
		}
	}
}