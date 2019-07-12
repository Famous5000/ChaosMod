package com.famous5000.chaos.items

import com.famous5000.chaos.blocks.ChaosBlock
import com.famous5000.chaos.classes.ItemRegistriedBlock
import com.famous5000.chaos.enums.ChaosTier
import com.famous5000.chaos.interfaces.IItemHasChaosTier
import com.famous5000.chaos.interfaces.IItemHasSubtypes
import com.famous5000.chaos.modid
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

object ChaosBlockItem : ItemRegistriedBlock(ChaosBlock), IItemHasSubtypes, IItemHasChaosTier {
	init {
		hasSubtypes = true
	}

	override fun getRarity(stack: ItemStack) = getChaosTier(stack).rarity

	override fun getUnlocalizedName(stack: ItemStack): String {
		return super.getUnlocalizedName() + "_t${stack.metadata}"
	}

	override fun getMetadata(metadata: Int) = metadata

	override fun getAllModels(): List<Pair<Int, ResourceLocation>> =
		ChaosTier.values().map {
			Pair(
				it.ordinal,
				ResourceLocation(modid, "chaos_blocks/chaos_block_t${it.ordinal}")
			)
		}
}