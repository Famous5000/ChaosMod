package com.famous5000.chaos.renderers.helpers

import net.minecraft.util.NonNullList

/**
 * Represents a cube.
 *
 * @constructor Creates a new cube.
 * @property topFace Whether or not the top face will be built.
 * @property bottomFace Whether or not the bottom face will be built.
 * @property northFace Whether or not the north face will be built.
 * @property southFace Whether or not the south face will be built.
 * @property westFace Whether or not the west face will be built.
 * @property eastFace Whether or not the east face will be built.
 * @property topFaceUV The UV for the top face. Depends on [topFace] being
 *           `true`.
 * @property bottomFaceUV The UV for the bottom face. Depends on [bottomFace]
 *           being `true`.
 * @property northFaceUV The UV for the north face. Depends on [northFace] being
 *           `true`.
 * @property southFaceUV The UV for the south face. Depends on [southFace] being
 *           `true`.
 * @property westFaceUV The UV for the west face. Depends on [westFace] being
 *           `true`.
 * @property eastFaceUV The UV for the east face. Depends on [eastFace] being
 *           `true`.
 * @property topFaceDoubleSided True if the top face should be visible from the
 *           inside of the cube. Depends on [topFace] being `true`.
 * @property bottomFaceDoubleSided True if the bottom face should be visible
 *           from the inside of the cube. Depends on [bottomFace] being `true`.
 * @property northFaceDoubleSided True if the north face should be visible from
 *           the inside of the cube. Depends on [northFace] being `true`.
 * @property southFaceDoubleSided True if the south face should be visible from
 *           the inside of the cube. Depends on [southFace] being `true`.
 * @property westFaceDoubleSided True if the west face should be visible from
 *           the inside of the cube. Depends on [westFace] being `true`.
 * @property eastFaceDoubleSided True if the east face should be visible from
 *           the inside of the cube. Depends on [eastFace] being `true`.
 */
class RenderHelperCube(
	override var x: Double,
	override var y: Double,
	override var z: Double,
	override var width: Double = 1.0,
	override var height: Double = 1.0,
	override var depth: Double = 1.0,
	private var topFace: Boolean = true,
	private var bottomFace: Boolean = true,
	private var northFace: Boolean = true,
	private var southFace: Boolean = true,
	private var westFace: Boolean = true,
	private var eastFace: Boolean = true,
	private var topFaceUV: RenderHelperUV? = null,
	private var bottomFaceUV: RenderHelperUV? = null,
	private var northFaceUV: RenderHelperUV? = null,
	private var southFaceUV: RenderHelperUV? = null,
	private var westFaceUV: RenderHelperUV? = null,
	private var eastFaceUV: RenderHelperUV? = null,
	private var topFaceDoubleSided: Boolean = false,
	private var bottomFaceDoubleSided: Boolean = false,
	private var northFaceDoubleSided: Boolean = false,
	private var southFaceDoubleSided: Boolean = false,
	private var westFaceDoubleSided: Boolean = false,
	private var eastFaceDoubleSided: Boolean = false
) : IRenderHelperShape {
	override val vertices: NonNullList<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>> =
		NonNullList.create<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>>()

	/**
	 * Sets this cube's faces' visibility. This is using global positioning,
	 * i.e. north is negative Z, east is positive X, and so on. This will only
	 * take effect once [vertices are regenerated][regenerateVertices].
	 *
	 * @return This shape, for chaining.
	 * @see regenerateVertices
	 */
	fun setFacesEnabled(topFace: Boolean? = null, bottomFace: Boolean? = null,
	                    northFace: Boolean? = null, southFace: Boolean? = null,
	                    westFace: Boolean? = null, eastFace: Boolean? = null): RenderHelperCube {
		topFace?.let { this.topFace = it }
		bottomFace?.let { this.bottomFace = it }
		northFace?.let { this.northFace = it }
		southFace?.let { this.southFace = it }
		westFace?.let { this.westFace = it }
		eastFace?.let { this.eastFace = it }

		return this
	}

	/**
	 * Sets the UVs for faces. These faces must be enabled for this to do
	 * anything. See [RenderHelperFace.setUV] and [RenderHelperUV] for more
	 * info. Needs a call to [regenerateVertices] to take effect.
	 *
	 * @return This shape, for chaining.
	 * @see RenderHelperFace.setUV
	 * @see RenderHelperUV
	 * @see regenerateVertices
	 */
	fun setFacesUV(topFaceUV: RenderHelperUV? = null, bottomFaceUV: RenderHelperUV? = null,
	               northFaceUV: RenderHelperUV? = null, southFaceUV: RenderHelperUV? = null,
	               westFaceUV: RenderHelperUV? = null, eastFaceUV: RenderHelperUV? = null): RenderHelperCube {
		topFaceUV?.let { this.topFaceUV = it }
		bottomFaceUV?.let { this.bottomFaceUV = it }
		northFaceUV?.let { this.northFaceUV = it }
		southFaceUV?.let { this.southFaceUV = it }
		westFaceUV?.let { this.westFaceUV = it }
		eastFaceUV?.let { this.eastFaceUV = it }

		return this
	}

	/**
	 * Sets whether certain faces are double-sided, i.e. you can view them from
	 * the inside of the cube. These faces must be enabled for them to do
	 * anything. Needs a call to [regenerateVertices] to take effect.
	 *
	 * @return This shape, for chaining.
	 * @see RenderHelperFace.setDoubleSided
	 * @see regenerateVertices
	 */
	fun setFacesDoubleSided(topFaceDoubleSided: Boolean? = null,
	                        bottomFaceDoubleSided: Boolean? = null,
	                        northFaceDoubleSided: Boolean? = null,
	                        southFaceDoubleSided: Boolean? = null,
	                        westFaceDoubleSided: Boolean? = null,
	                        eastFaceDoubleSided: Boolean? = null): RenderHelperCube {
		topFaceDoubleSided?.let { this.topFaceDoubleSided = it }
		bottomFaceDoubleSided?.let { this.bottomFaceDoubleSided = it }
		northFaceDoubleSided?.let { this.northFaceDoubleSided = it }
		southFaceDoubleSided?.let { this.southFaceDoubleSided = it }
		westFaceDoubleSided?.let { this.westFaceDoubleSided = it }
		eastFaceDoubleSided?.let { this.eastFaceDoubleSided = it }

		return this
	}

	override fun regenerateVertices(): RenderHelperCube {
		vertices.removeAll(vertices)

		if (topFace) {
			RenderHelperFace(x, height / 2 + y, z, width, depth)
				.setUV(topFaceUV ?: RenderHelperUV())
				.setDoubleSided(topFaceDoubleSided)
				.regenerateVertices()
				.rotateCenterX(-Math.PI / 2)
				.drawVerticesTo(vertices)
		}

		if (bottomFace) {
			RenderHelperFace(x, -height / 2 + y, z, width, depth)
				.setUV(bottomFaceUV ?: RenderHelperUV())
				.setDoubleSided(bottomFaceDoubleSided)
				.regenerateVertices()
				.rotateCenterX(Math.PI / 2)
				.drawVerticesTo(vertices)
		}

		if (northFace) {
			RenderHelperFace(x, y, -depth / 2 + z, width, height)
				.setUV(northFaceUV ?: RenderHelperUV())
				.setDoubleSided(northFaceDoubleSided)
				.regenerateVertices()
				.rotateCenterY(Math.PI)
				.drawVerticesTo(vertices)
		}

		if (southFace) {
			RenderHelperFace(x, y, depth / 2 + z, width, height)
				.setUV(southFaceUV ?: RenderHelperUV())
				.setDoubleSided(southFaceDoubleSided)
				.regenerateVertices()
				.drawVerticesTo(vertices)
		}

		if (westFace) {
			RenderHelperFace(-width / 2 + x, y, z, depth, height)
				.setUV(westFaceUV ?: RenderHelperUV())
				.setDoubleSided(westFaceDoubleSided)
				.regenerateVertices()
				.rotateCenterY(Math.PI / 2)
				.drawVerticesTo(vertices)
		}

		if (eastFace) {
			RenderHelperFace(width / 2 + x, y, z, depth, height)
				.setUV(eastFaceUV ?: RenderHelperUV())
				.setDoubleSided(eastFaceDoubleSided)
				.regenerateVertices()
				.rotateCenterY(-Math.PI / 2)
				.drawVerticesTo(vertices)
		}

		return this
	}
}