package com.famous5000.chaos.items

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.modid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation


object ChaosStarItem : Item() {
	init {
		unlocalizedName = "chaos_star"
		registryName = ResourceLocation(modid, "chaos_star")
		creativeTab = ChaosTab
	}

	override fun hasEffect(stack: ItemStack) = true
}