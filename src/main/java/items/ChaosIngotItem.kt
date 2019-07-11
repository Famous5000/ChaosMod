package com.famous5000.chaos.items

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.enums.ChaosTier
import com.famous5000.chaos.interfaces.IItemHasSubtypes
import com.famous5000.chaos.modid
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation

object ChaosIngotItem : Item(), IItemHasSubtypes {
	init {
		unlocalizedName = "$modid.chaos_ingot"
		registryName = ResourceLocation(modid, "chaos_ingot")
		hasSubtypes = true
		creativeTab = ChaosTab
	}

	override fun getRarity(stack: ItemStack): EnumRarity =
		when (stack.metadata) {
			0, 1 -> EnumRarity.UNCOMMON
			2, 3 -> EnumRarity.RARE
			4, 5 -> EnumRarity.EPIC
			else -> EnumRarity.COMMON
		}

	override fun getUnlocalizedName(stack: ItemStack): String {
		return super.getUnlocalizedName() + "_t${stack.metadata}"
	}

	override fun getSubItems(tab: CreativeTabs, items: NonNullList<ItemStack>) {
		if (this.isInCreativeTab(tab)) {
			for (chaosTier in ChaosTier.values()) {
				items.add(ItemStack(this, 1, chaosTier.ordinal))
			}
		}
	}

	override fun getAllModels(): List<Pair<Int, ResourceLocation>> =
		ChaosTier.values().map {
			Pair(
				it.ordinal,
				ResourceLocation(modid, "chaos_ingot_t${it.ordinal}")
			)
		}
}