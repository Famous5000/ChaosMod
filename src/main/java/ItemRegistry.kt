package com.famous5000.chaos

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

val Items = arrayOf(ChaosStarItem, ChaosFragmentItem)

@Mod.EventBusSubscriber(modid = modid)
object ItemRegistry {
	@JvmStatic
	@SubscribeEvent
	fun registerItems(event: RegistryEvent.Register<Item>) {
		for (i in Items.indices) {
			event.registry.register(Items[i])
		}
	}
	@JvmStatic
	@SubscribeEvent
	fun registerRenders(event: ModelRegistryEvent) {
		for (index in Items.indices) {
			val item = Items[index]

			val resourceLocation = item.registryName as ResourceLocation
			val modelResourceLocation = ModelResourceLocation(resourceLocation, "inventory")

			ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation)
		}
	}
}