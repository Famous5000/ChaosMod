package com.famous5000.chaos

import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry

@Mod(
	modid = modid,
	name = name,
	version = version,
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	dependencies = "required-before:forgelin"
)

object Chaos{
	@JvmStatic
	@GameRegistry.ObjectHolder("minecraft:nether_star")
	val netherStar: Item = Item() // this will get forcefully overwritten by Forge very quickly
	// Recipe Handling is in JSON handles for easier access.
//	@JvmStatic
//	@Mod.EventHandler
//	fun onInitializationStep(event: FMLInitializationEvent){
//		registerRecipes()
//	}
}