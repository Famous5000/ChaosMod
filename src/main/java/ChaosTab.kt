package com.famous5000.chaos

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object ChaosTab : CreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.size, "chaos") {
	override fun getTabIconItem(): ItemStack {
		return ItemStack(ChaosStarItem)
	}
}