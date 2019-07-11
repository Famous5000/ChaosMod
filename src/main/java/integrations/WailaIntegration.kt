package com.famous5000.chaos.integrations

import com.famous5000.chaos.logger
import com.famous5000.chaos.tileentities.ChaosEnergyUserTileEntity
import mcp.mobius.waila.api.*
import net.minecraft.item.ItemStack

@Suppress("unused")
@WailaPlugin
class WailaIntegration : IWailaPlugin, IWailaDataProvider {
	override fun register(registrar: IWailaRegistrar) {
		logger.info("Registering HWYLA plugin!")

		registrar.registerBodyProvider(this, ChaosEnergyUserTileEntity::class.java)
	}

	override fun getWailaBody(itemStack: ItemStack, tooltip: MutableList<String>,
	                          accessor: IWailaDataAccessor, config: IWailaConfigHandler): MutableList<String> {
		val tileEntity = accessor.tileEntity

		if (tileEntity is ChaosEnergyUserTileEntity) {
			return tileEntity.getWailaBody(itemStack, tooltip, accessor, config)
		}

		return tooltip
	}
}