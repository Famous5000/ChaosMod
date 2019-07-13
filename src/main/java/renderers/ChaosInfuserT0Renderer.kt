package com.famous5000.chaos.renderers

import com.famous5000.chaos.renderers.helpers.RenderHelperCube
import com.famous5000.chaos.renderers.helpers.RenderHelperUV
import com.famous5000.chaos.tileentities.ChaosInfuserT0TileEntity
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11.GL_QUADS

object ChaosInfuserT0Renderer : TileEntitySpecialRenderer<ChaosInfuserT0TileEntity>() {
	override fun render(te: ChaosInfuserT0TileEntity, x: Double, y: Double, z: Double,
	                    partialTicks: Float, destroyStage: Int, alpha: Float) {
		bindTexture(TileEntityBeaconRenderer.TEXTURE_BEACON_BEAM)

		GlStateManager.disableLighting()
		renderThingy(x, y, z, partialTicks, destroyStage, alpha)
		GlStateManager.enableLighting()

		super.render(te, x, y, z, partialTicks, destroyStage, alpha)
	}

	private fun renderThingy(x: Double, y: Double, z: Double, partialTicks: Float,
	                         destroyStage: Int, alpha: Float) {
		val tessellator = Tessellator.getInstance()
		val bufferBuilder = tessellator.buffer

		bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX)

		val totalTicks = rendererDispatcher.world.totalWorldTime + partialTicks.toDouble()

		RenderHelperCube(
			x + 0.5,
			y + 0.625,
			z + 0.5,
			1.0 / 16,
			0.25,
			1.0 / 16
		)
			.setFacesEnabled(
				topFace = false,
				bottomFace = false
			)
			.setFacesUV(
				northFaceUV = RenderHelperUV(
					v = totalTicks / 20 % 1,
					vw = 4.0
				),
				southFaceUV = RenderHelperUV(
					v = totalTicks / 20 % 1,
					vw = 4.0
				),
				eastFaceUV = RenderHelperUV(
					v = totalTicks / 20 % 1,
					vw = 4.0
				),
				westFaceUV = RenderHelperUV(
					v = totalTicks / 20 % 1,
					vw = 4.0
				)
			)
			.setFacesDoubleSided(
				northFaceDoubleSided = true,
				southFaceDoubleSided = true,
				eastFaceDoubleSided = true,
				westFaceDoubleSided = true
			)
			.regenerateVertices()
			.rotateCenterY(Math.toRadians(totalTicks * 2))
			.drawVerticesTo(bufferBuilder)

		tessellator.draw()
	}
}