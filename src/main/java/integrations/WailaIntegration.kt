package com.famous5000.chaos.integrations

import com.famous5000.chaos.interfaces.IWailaDataProvider
import com.famous5000.chaos.logger
import mcp.mobius.waila.api.*
import net.minecraft.item.ItemStack

@Suppress("unused")
@WailaPlugin
class WailaIntegration : IWailaPlugin, mcp.mobius.waila.api.IWailaDataProvider {
	override fun register(registrar: IWailaRegistrar) {
		logger.info("Registering WAILA plugin!")

		registrar.registerBodyProvider(this, IWailaDataProvider::class.java)
	}

	override fun getWailaBody(itemStack: ItemStack, tooltip: MutableList<String>,
	                          accessor: IWailaDataAccessor, config: IWailaConfigHandler): MutableList<String> =
		(accessor.tileEntity as? IWailaDataProvider)?.getWailaBody(itemStack, tooltip, accessor, config)
		?: tooltip
}