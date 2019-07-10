package com.famous5000.chaos

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

@Suppress("unused")
@Mod(
	modid = modid,
	name = name,
	version = version,
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	dependencies = "required-before:forgelin"
)
object Chaos {
	val networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(modid)!!

	enum class NetworkDiscriminators {
		ChaosEnergyStorage
	}

	@JvmStatic
	@Mod.EventHandler
	fun onPreInit(event: FMLPreInitializationEvent) {
		logger = event.modLog

		Registry.onPreInit(event)
	}
}