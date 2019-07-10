package com.famous5000.chaos.classes

import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

open class ItemRegistriedBlock(block: Block) : ItemBlock(block) {
	init {
		registryName = block.registryName
	}
}