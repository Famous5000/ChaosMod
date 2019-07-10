package com.famous5000.chaos.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.energy.IEnergyStorage

interface IChaosEnergyStorage : IEnergyStorage, INBTSerializable<NBTTagCompound> {
	var chaosEnergyCapacity: Int
	var chaosEnergyStored: Int
}