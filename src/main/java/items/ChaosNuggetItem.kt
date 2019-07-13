package com.famous5000.chaos.items

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.enums.ChaosTier
import com.famous5000.chaos.interfaces.IItemHasChaosTier
import com.famous5000.chaos.interfaces.IItemHasSubtypes
import com.famous5000.chaos.modid
import net.minecraft.client.resources.I18n
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.EnumRarity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation

object ChaosNuggetItem : Item(), IItemHasSubtypes, IItemHasChaosTier {
	init {
		unlocalizedName = "$modid.chaos_nugget"
		registryName = ResourceLocation(modid, "chaos_nugget")
		hasSubtypes = true
		creativeTab = ChaosTab
	}

	override fun getRarity(stack: ItemStack): EnumRarity =
		getChaosTier(stack)?.rarity ?: EnumRarity.COMMON

	override fun getItemStackDisplayName(stack: ItemStack): String =
		I18n.format("${this.unlocalizedName}.name", stack.metadata)

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
				ResourceLocation(modid, "chaos_nuggets/chaos_nugget_t${it.ordinal}")
			)
		}
}