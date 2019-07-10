package com.famous5000.chaos.blocks

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.modid
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation

object ChaosInfuserT0Block : Block(Material.ROCK) {
	init {
		unlocalizedName = "$modid.chaos_infuser_t0"
		registryName = ResourceLocation(modid, "chaos_infuser_t0")
		setCreativeTab(ChaosTab)
	}
}