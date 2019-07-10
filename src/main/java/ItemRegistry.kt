package com.famous5000.chaos

import com.famous5000.chaos.blocks.ChaosInfuserT0Block
import com.famous5000.chaos.items.ChaosFragmentItem
import com.famous5000.chaos.items.ChaosInfuserT0ItemBlock
import com.famous5000.chaos.items.ChaosStarItem
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object ItemRegistry {
	private val items by lazy {
		arrayListOf(
			ChaosStarItem,
			ChaosFragmentItem,
			ChaosInfuserT0ItemBlock
		)
	}

	private val blocks by lazy {
		arrayListOf(
			ChaosInfuserT0Block
		)
	}

	@JvmStatic
	@SubscribeEvent
	fun registerItems(event: RegistryEvent.Register<Item>) {
		for (item in items) {
			event.registry.register(item)
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun registerBlocks(event: RegistryEvent.Register<Block>) {
		for (block in blocks) {
			event.registry.register(block)
		}
	}

	@Suppress("unused_parameter")
	@JvmStatic
	@SubscribeEvent
	fun registerRenders(event: ModelRegistryEvent) {
		for (index in items.indices) {
			val item = items[index]

			val resourceLocation = item.registryName as ResourceLocation
			val modelResourceLocation = ModelResourceLocation(resourceLocation, "inventory")

			ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation)
		}
	}
}