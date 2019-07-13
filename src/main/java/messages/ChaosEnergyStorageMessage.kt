package com.famous5000.chaos.messages

import com.famous5000.chaos.capability.ChaosEnergyStorage
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class ChaosEnergyStorageMessage : IMessage {
	lateinit var blockPos: BlockPos
	var chaosEnergyStored = 0
	var chaosEnergyCapacity = 0

	@Suppress("unused")
	constructor()

	constructor(blockPos: BlockPos, chaosEnergyStorage: ChaosEnergyStorage) {
		this.blockPos = blockPos
		chaosEnergyStored = chaosEnergyStorage.chaosEnergyStored
		chaosEnergyCapacity = chaosEnergyStorage.chaosEnergyCapacity
	}

	override fun toBytes(buf: ByteBuf) {
		buf.writeInt(blockPos.x)
		buf.writeInt(blockPos.y)
		buf.writeInt(blockPos.z)
		buf.writeInt(chaosEnergyStored)
		buf.writeInt(chaosEnergyCapacity)
	}

	override fun fromBytes(buf: ByteBuf?) {
		buf?.let {
			blockPos = BlockPos(it.readInt(), it.readInt(), it.readInt())
			chaosEnergyStored = buf.readInt()
			chaosEnergyCapacity = buf.readInt()
		}
	}
}