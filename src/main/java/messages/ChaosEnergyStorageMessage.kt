package com.famous5000.chaos.messages

import com.famous5000.chaos.capability.ChaosEnergyStorage
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class ChaosEnergyStorageMessage : IMessage {
	var identifier = 0
	lateinit var blockPos: BlockPos
	var chaosEnergyStored = 0
	var chaosEnergyCapacity = 0

	private fun useChaosEnergyStorage(chaosEnergyStorage: ChaosEnergyStorage) {
		chaosEnergyStored = chaosEnergyStorage.chaosEnergyStored
		chaosEnergyCapacity = chaosEnergyStorage.chaosEnergyCapacity
	}

	@Suppress("unused")
	constructor()

	constructor(blockPos: BlockPos, chaosEnergyStorage: ChaosEnergyStorage) {
		identifier = 1

		this.blockPos = blockPos
		useChaosEnergyStorage(chaosEnergyStorage)
	}

	private fun writeBlockPos(buf: ByteBuf) {
		buf.writeInt(blockPos.x)
		buf.writeInt(blockPos.y)
		buf.writeInt(blockPos.z)
	}

	private fun writeChaosEnergyStorage(buf: ByteBuf) {
		buf.writeInt(chaosEnergyStored)
		buf.writeInt(chaosEnergyCapacity)
	}

	override fun toBytes(buf: ByteBuf) {
		buf.writeInt(identifier)

		if (identifier == 1) {
			writeBlockPos(buf)
		}

		writeChaosEnergyStorage(buf)
	}

	private fun readBlockPos(buf: ByteBuf) {
		val x = buf.readInt()
		val y = buf.readInt()
		val z = buf.readInt()

		blockPos = BlockPos(x, y, z)
	}

	private fun readChaosEnergyStorage(buf: ByteBuf) {
		chaosEnergyStored = buf.readInt()
		chaosEnergyCapacity = buf.readInt()
	}

	override fun fromBytes(buf: ByteBuf?) {
		identifier = buf!!.readInt()

		if (identifier == 1) {
			readBlockPos(buf)
		}

		readChaosEnergyStorage(buf)
	}
}