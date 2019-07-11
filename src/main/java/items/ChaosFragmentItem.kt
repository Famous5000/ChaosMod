package com.famous5000.chaos.items

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.modid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

object ChaosFragmentItem : Item() {
	init {
		unlocalizedName = "$modid.chaos_fragment"
		registryName = ResourceLocation(modid, "chaos_fragment")
		creativeTab = ChaosTab
		maxStackSize = 4

		this.addPropertyOverride(
			ResourceLocation(modid, "chaos_fragment")
		) { stack, _, _ -> stack.count / 4F }
	}

	override fun hasEffect(stack: ItemStack?) = true
}