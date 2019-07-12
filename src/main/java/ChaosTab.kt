package com.famous5000.chaos

import com.famous5000.chaos.items.ChaosStarItem
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object ChaosTab : CreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.size, modid) {
	private val icon = ItemStack(ChaosStarItem)

	override fun getTabIconItem() = icon
}