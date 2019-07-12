package com.famous5000.chaos.interfaces

import com.famous5000.chaos.enums.ChaosTier
import net.minecraft.item.ItemStack

interface IItemHasChaosTier {
	fun getChaosTier(itemStack: ItemStack): ChaosTier? {
		return ChaosTier.values().find { it.ordinal == itemStack.metadata }
	}
}