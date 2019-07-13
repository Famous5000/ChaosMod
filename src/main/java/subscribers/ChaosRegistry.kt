package com.famous5000.chaos.subscribers

import com.famous5000.chaos.blocks.ChaosBlock
import com.famous5000.chaos.blocks.ChaosInfuserT0Block
import com.famous5000.chaos.capability.ChaosEnergyStorage
import com.famous5000.chaos.interfaces.IItemHasSubtypes
import com.famous5000.chaos.items.*
import com.famous5000.chaos.modid
import com.famous5000.chaos.renderers.ChaosInfuserT0Renderer
import com.famous5000.chaos.tileentities.ChaosInfuserT0TileEntity
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object ChaosRegistry {
	private val items by lazy {
		arrayListOf(
			ChaosStarItem,
			ChaosFragmentItem,

			ChaosIngotItem,
			ChaosNuggetItem,

			ChaosBlockItem,
			ChaosInfuserT0ItemBlock
		)
	}

	private val blocks by lazy {
		arrayListOf(
			ChaosBlock,
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

	private val specialRenderers by lazy {
		mapOf(
			ChaosInfuserT0TileEntity::class.java to ChaosInfuserT0Renderer
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
		for (item in items) {
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

	@Suppress("unused_parameter")
	private fun registerSpecialRenderers(event: FMLPreInitializationEvent) {
		for ((tileEntityClass, specialRendererInstance) in specialRenderers) {
			ClientRegistry.bindTileEntitySpecialRenderer(
				tileEntityClass,
				specialRendererInstance
			)
		}
	}

	fun onPreInit(event: FMLPreInitializationEvent) {
		registerCapabilities(event)

		if (event.side == Side.CLIENT) {
			registerSpecialRenderers(event)
		}
	}
}