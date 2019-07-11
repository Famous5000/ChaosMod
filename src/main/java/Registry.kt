package com.famous5000.chaos

import com.famous5000.chaos.blocks.ChaosInfuserT0Block
import com.famous5000.chaos.capability.ChaosEnergyStorage
import com.famous5000.chaos.interfaces.IItemHasSubtypes
import com.famous5000.chaos.items.ChaosFragmentItem
import com.famous5000.chaos.items.ChaosInfuserT0ItemBlock
import com.famous5000.chaos.items.ChaosIngotItem
import com.famous5000.chaos.items.ChaosStarItem
import com.famous5000.chaos.tileentities.ChaosInfuserT0TileEntity
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object Registry {
	private val items by lazy {
		arrayListOf(
			ChaosStarItem,
			ChaosFragmentItem,
			ChaosInfuserT0ItemBlock,
			ChaosIngotItem
		)
	}

	private val blocks by lazy {
		arrayListOf(
			ChaosInfuserT0Block
		)
	}

	private val tileEntities by lazy {
		mapOf(
			ChaosInfuserT0Block.registryName to ChaosInfuserT0TileEntity::class.java
		)
	}

	private val capabilities by lazy {
		arrayListOf(
			ChaosEnergyStorage
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

		for ((registryName, tileEntity) in tileEntities) {
			GameRegistry.registerTileEntity(tileEntity, registryName)
		}
	}

	@Suppress("unused_parameter")
	@JvmStatic
	@SubscribeEvent
	fun registerRenders(event: ModelRegistryEvent) {
		for (index in items.indices) {
			val item = items[index]

			if (item is IItemHasSubtypes) {
				for ((metadata, resourceLocation) in item.getAllModels()) {
					registerModel(item, metadata, resourceLocation)
				}
			} else {
				val resourceLocation = item.registryName as ResourceLocation

				registerModel(item, 0, resourceLocation)
			}
		}
	}

	private fun registerModel(item: Item, metadata: Int, resourceLocation: ResourceLocation) {
		val modelResourceLocation = ModelResourceLocation(resourceLocation, "inventory")

		ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation)
	}

	@Suppress("unused_parameter")
	private fun registerCapabilities(event: FMLPreInitializationEvent) {
		for (capability in capabilities) {
			capability.register()
		}
	}

	fun onPreInit(event: FMLPreInitializationEvent) {
		registerCapabilities(event)
	}
}