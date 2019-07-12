package com.famous5000.chaos.interfaces

import com.famous5000.chaos.enums.ChaosTier
import net.minecraft.block.state.IBlockState

interface IBlockHasChaosTier {
	fun getChaosTier(blockState: IBlockState): ChaosTier
}