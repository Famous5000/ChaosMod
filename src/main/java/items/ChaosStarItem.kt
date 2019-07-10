package com.famous5000.chaos

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.thread.SidedThreadGroups.CLIENT
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly



object ChaosStarItem: Item() {
	init {
		unlocalizedName = "chaos_star"
		registryName = ResourceLocation(modid, "chaos_star")
		creativeTab = ChaosTab
	}
	@SideOnly(Side.CLIENT)
	override fun hasEffect(stack: ItemStack?): Boolean {
		return true
	}
}