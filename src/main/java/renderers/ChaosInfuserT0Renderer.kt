package com.famous5000.chaos.renderers

import com.famous5000.chaos.tileentities.ChaosInfuserT0TileEntity
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer

object ChaosInfuserT0Renderer : TileEntitySpecialRenderer<ChaosInfuserT0TileEntity>() {
	override fun render(te: ChaosInfuserT0TileEntity, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha)
	}
}