//package com.famous5000.chaos
//
//import net.minecraft.item.Item
//import net.minecraft.item.ItemStack
//import net.minecraft.item.crafting.Ingredient
//import net.minecraft.util.ResourceLocation
//import net.minecraftforge.fml.common.registry.GameRegistry
//
//fun registerRecipes(){
//	GameRegistry.addShapelessRecipe(
//		ChaosFragmentItem.registryName,
//		ChaosFragmentItem.registryName,
//		ItemStack(ChaosFragmentItem, 4),
//		Ingredient.fromItem(ChaosStarItem)
//	)
//	GameRegistry.addShapelessRecipe(
//		ChaosStarItem.registryName,
//		ChaosStarItem.registryName,
//		ItemStack(ChaosStarItem, 1),
//		Ingredient.fromItem(ChaosFragmentItem),
//		Ingredient.fromItem(ChaosFragmentItem),
//		Ingredient.fromItem(ChaosFragmentItem),
//		Ingredient.fromItem(ChaosFragmentItem)
//	)
//	GameRegistry.addShapedRecipe(
//		ResourceLocation(modid, "nether_star"),
//		ChaosStarItem.registryName,
//		ItemStack(Chaos.netherStar),
//		" F ",
//		"FSF",
//		" F ",
//		'F', ChaosFragmentItem,
//		'S', ChaosStarItem
//	)
//}

// YOU ARE LOOKING AT BAD DEPRECATED CODE. PLEASE USE THE JSON FORMAT.