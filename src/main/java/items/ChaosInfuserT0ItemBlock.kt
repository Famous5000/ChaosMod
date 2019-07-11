package com.famous5000.chaos.items

import com.famous5000.chaos.blocks.ChaosInfuserT0Block
import com.famous5000.chaos.classes.ItemRegistriedBlock
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

object ChaosInfuserT0ItemBlock : ItemRegistriedBlock(ChaosInfuserT0Block) {
	override fun getRarity(stack: ItemStack): EnumRarity {
		return EnumRarity.UNCOMMON
	}
}