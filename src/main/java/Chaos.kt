package com.famous5000.chaos

import com.famous5000.chaos.subscribers.ChaosRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

@Suppress("unused")
@Mod(
	modid = modid,
	name = name,
	version = version,
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	dependencies = "required-before:forgelin@[1.6.0,);" +
	               "after:waila@[1.8.26,)"
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

		ChaosRegistry.onPreInit(event)
	}
}