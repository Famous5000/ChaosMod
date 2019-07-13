package com.famous5000.chaos.renderers.helpers

import net.minecraft.util.NonNullList

/**
 * Represents a single face. By default, only visible from one side (the south
 * side when [regenerateVertices] is first called). Call [setDoubleSided] to
 * change this.
 *
 * See [RenderHelperCube] for an example of how this is used. Note how
 * transforms like [rotateY] or [rotateX] are used to orient the face for the
 * different sides of the cube.
 *
 * @param uv The UV mapping for this face. See [RenderHelperUV] for more info.
 * @param doubleSided Whether or not this face is double-sided. False by default
 * @see IRenderHelperShape
 * @see RenderHelperUV
 */
class RenderHelperFace(
	override var x: Double,
	override var y: Double,
	override var z: Double,
	override var width: Double = 1.0,
	override var height: Double = 1.0,
	private var uv: RenderHelperUV = RenderHelperUV(),
	private var doubleSided: Boolean = false
) : IRenderHelperShape {
	override val vertices: NonNullList<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>> =
		NonNullList.create<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>>()

	/**
	 * This isn't used for faces. Setting this will do nothing.
	 */
	override var depth = 0.0

	/**
	 * Sets the UV mapping of this face. You'll need to [regenerateVertices] for
	 * this to take effect.
	 *
	 * @return This shape, for chaining.
	 * @see RenderHelperUV
	 * @see regenerateVertices
	 */
	fun setUV(uv: RenderHelperUV): RenderHelperFace {
		this.uv = uv

		return this
	}

	/**
	 * Sets whether or not this face is double-sided. By default, when
	 * [regenerateVertices] is first called, only the south side of the face
	 * can be seen (meaning you'd have to be looking north at it). Setting this
	 * to `true` means that, after
	 * [vertices are regenerated][regenerateVertices], you'll be able to see
	 * both sides of the face.
	 *
	 * @return This shape, for chaining.
	 * @see RenderHelperCube.setFacesDoubleSided
	 */
	fun setDoubleSided(doubleSided: Boolean): RenderHelperFace {
		this.doubleSided = doubleSided

		return this
	}

	override fun regenerateVertices(): RenderHelperFace {
		vertices.removeAll(vertices)
		vertices.add(Pair(Triple(width / 2 + x, -height / 2 + y, z), Pair(uv.u + uv.uw, uv.v + uv.vw)))
		vertices.add(Pair(Triple(width / 2 + x, height / 2 + y, z), Pair(uv.u + uv.uw, uv.v)))
		vertices.add(Pair(Triple(-width / 2 + x, height / 2 + y, z), Pair(uv.u, uv.v)))
		vertices.add(Pair(Triple(-width / 2 + x, -height / 2 + y, z), Pair(uv.u, uv.v + uv.vw)))

		if (this.doubleSided) {
			vertices.add(Pair(Triple(-width / 2 + x, -height / 2 + y, z), Pair(uv.u, uv.v + uv.vw)))
			vertices.add(Pair(Triple(-width / 2 + x, height / 2 + y, z), Pair(uv.u, uv.v)))
			vertices.add(Pair(Triple(width / 2 + x, height / 2 + y, z), Pair(uv.u + uv.uw, uv.v)))
			vertices.add(Pair(Triple(width / 2 + x, -height / 2 + y, z), Pair(uv.u + uv.uw, uv.v + uv.vw)))
		}

		return this
	}
}