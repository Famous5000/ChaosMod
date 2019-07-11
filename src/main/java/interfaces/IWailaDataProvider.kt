package com.famous5000.chaos.interfaces

import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.item.ItemStack

interface IWailaDataProvider {
	fun getWailaBody(itemStack: ItemStack, tooltip: MutableList<String>, accessor: IWailaDataAccessor,
	                 config: IWailaConfigHandler): MutableList<String>
}