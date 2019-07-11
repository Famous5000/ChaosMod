package com.famous5000.chaos.interfaces

import net.minecraft.util.ResourceLocation

interface IItemHasSubtypes {
	/**
	 * Returns a NonNullList containing <item metadata, ResourceLocation> pairs
	 * denoting which models to register
	 */
	fun getAllModels(): List<Pair<Int, ResourceLocation>>
}