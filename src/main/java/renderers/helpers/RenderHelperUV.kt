package com.famous5000.chaos.renderers.helpers

/**
 * Represents a UV mapping for, for example, a [RenderHelperFace].
 *
 * For example, (0.5, 0.5, 0, 0.5) would use the upper right quadrant of a
 * texture. (0, 0.5, 0, 1) would use the upper half. (1, -1, 0, 1) would flip
 * the texture horizontally. You can use values over 1 and they will wrap the
 * texture.
 *
 * Used for [BufferBuilder][net.minecraft.client.renderer.BufferBuilder]s. See
 * [IRenderHelperShape]s for usages of this, particularly
 * [RenderHelperFace.setUV].
 *
 * @param u Percent (0-1) from the left of the texture the face is mapped to.
 * @param uw Percent (0-1) of the width of the texture the face is mapped to.
 * @param v Percent (0-1) from the top of the texture the face is mapped to.
 * @param vw Percent (0-1) of the height of the texture the face is mapped to.
 */
class RenderHelperUV(
	val u: Double = .0,
	val uw: Double = 1.0,
	val v: Double = .0,
	val vw: Double = 1.0
)