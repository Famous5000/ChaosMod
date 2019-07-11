package com.famous5000.chaos.enums

import com.famous5000.chaos.LegendaryTier
import net.minecraft.item.EnumRarity

enum class ChaosTier(val rarity: EnumRarity) {
	T0(EnumRarity.UNCOMMON),
	T1(EnumRarity.RARE),
	T2(EnumRarity.RARE),
	T3(EnumRarity.EPIC),
	T4(EnumRarity.EPIC),
	T5(LegendaryTier)
}