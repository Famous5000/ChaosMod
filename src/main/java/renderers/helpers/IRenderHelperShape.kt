package com.famous5000.chaos.renderers.helpers

import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.NonNullList
import org.lwjgl.opengl.GL11.GL_QUADS

/**
 * An interface that render helper shapes can implement. It has some built-in
 * functions that make creating a new shape easy. See [RenderHelperCube] for an
 * example of how to make one of these. See
 * [`ChaosInfuserT0Renderer`][com.famous5000.chaos.renderers.ChaosInfuserT0Renderer]
 * for usages.
 *
 * @property x The X coordinate of the center of this shape, in blocks.
 * @property y The Y coordinate of the center of this shape, in blocks.
 * @property z The Z coordinate of the center of this shape, in blocks.
 * @property width The width of this shape (X dimension), in blocks.
 * @property height The height of this shape (Y dimension), in blocks.
 * @property depth The depth of this shape (Z dimension), in blocks.
 */
interface IRenderHelperShape {
	var x: Double
	var y: Double
	var z: Double
	var width: Double
	var height: Double
	var depth: Double

	/**
	 * Sets the [x][IRenderHelperShape.x], [y][IRenderHelperShape.y] and
	 * [z][IRenderHelperShape.z] position of this shape. You will need to call
	 * [regenerateVertices] if you want this to take effect, and you'll lose all
	 * transformations.
	 *
	 * Try [translateXYZ] or any of the [translateX], [translateY] or
	 * [translateZ] methods for a way to translate the shape if [vertices] were
	 * already built.
	 *
	 * @param x The new X coordinate of the shape. See [IRenderHelperShape.x]
	 * @param y The new Y coordinate of the shape. See [IRenderHelperShape.y]
	 * @param z The new Z coordinate of the shape. See [IRenderHelperShape.z]
	 * @return This shape, for chaining.
	 */
	fun setXYZ(x: Double, y: Double, z: Double): IRenderHelperShape {
		this.x = x
		this.y = y
		this.z = z

		return this
	}

	/**
	 * Translates this shape by the given [x], [y], and [z] values. This works
	 * directly on [vertices], so you should call [regenerateVertices] once
	 * before using it.
	 *
	 * @param x X to translate by, in blocks of course. This can be negative.
	 * @param y Y to translate by, in blocks of course. This can be negative.
	 * @param z Z to translate by, in blocks of course. This can be negative.
	 * @return This shape, for chaining.
	 * @see translateX
	 * @see translateY
	 * @see translateZ
	 */
	fun translateXYZ(x: Double, y: Double, z: Double): IRenderHelperShape {
		this.x += x
		this.y += y
		this.z += z

		for (index in vertices.indices) {
			val vertex = vertices[index]
			val pos = vertex.first
			val uv = vertex.second

			vertices[index] = Pair(
				Triple(
					pos.first + x,
					pos.second + y,
					pos.third + z
				),
				uv
			)
		}

		return this
	}

	/**
	 * Translates this shape across the X axis by the given [x] value. You don't
	 * necessarily have to call [regenerateVertices] before this method, as it
	 * also modifies the [x][IRenderHelperShape.x], [y][IRenderHelperShape.y]
	 * and [z][IRenderHelperShape.z] properties, which [regenerateVertices]
	 * uses.
	 *
	 * @param x X to translate by, in blocks.
	 * @return This shape, for chaining.
	 * @see translateXYZ
	 * @see regenerateVertices
	 */
	fun translateX(x: Double) = translateXYZ(x, .0, .0)

	/**
	 * Translates this shape across the Y axis by the given [y] value. You don't
	 * necessarily have to call [regenerateVertices] before this method, as it
	 * also modifies the [x][IRenderHelperShape.x], [y][IRenderHelperShape.y]
	 * and [z][IRenderHelperShape.z] properties, which [regenerateVertices]
	 * uses.
	 *
	 * @param y Y to translate by, in blocks.
	 * @return This shape, for chaining.
	 * @see translateXYZ
	 * @see regenerateVertices
	 */
	fun translateY(y: Double) = translateXYZ(.0, y, .0)

	/**
	 * Translates this shape across the Z axis by the given [z] value. You don't
	 * necessarily have to call [regenerateVertices] before this method, as it
	 * also modifies the [x][IRenderHelperShape.x], [y][IRenderHelperShape.y]
	 * and [z][IRenderHelperShape.z] properties, which [regenerateVertices]
	 * uses.
	 *
	 * @param z Z to translate by, in blocks.
	 * @return This shape, for chaining.
	 * @see translateXYZ
	 * @see regenerateVertices
	 */
	fun translateZ(z: Double) = translateXYZ(.0, .0, z)

	/**
	 * The stored vertices that construct this shape.
	 *
	 * [regenerateVertices] adds vertices to this [NonNullList] like so:
	 *
	 * [Pair]<[Triple]<x, y, z>, [Double]<u, v>>
	 *
	 * See [RenderHelperUV] for more info on what `u` and `v` mean.
	 *
	 * @see regenerateVertices
	 * @see drawVerticesTo
	 */
	val vertices: NonNullList<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>>

	/**
	 * Regenerates the vertices that construct this shape.
	 *
	 * Do this before any transforms (i.e. rotation, etc.) since methods like
	 * [rotateY] or [translateX] operate directly on [vertices].
	 *
	 * @return This shape, for chaining.
	 * @see drawVerticesTo
	 * @see translateXYZ
	 * @see rotateX
	 * @see rotateY
	 * @see rotateZ
	 */
	fun regenerateVertices(): IRenderHelperShape

	/**
	 * Copies [vertices] to a [BufferBuilder].
	 *
	 * The [BufferBuilder] must be in [GL_QUADS] mode and
	 * [DefaultVertexFormats.POSITION_TEX] format, or else an
	 * [IllegalArgumentException] will be thrown.
	 *
	 * @param bufferBuilder The buffer builder to draw to.
	 * @return This shape, for chaining.
	 * @see vertices
	 * @see BufferBuilder
	 */
	fun drawVerticesTo(bufferBuilder: BufferBuilder): IRenderHelperShape {
		val glMode = bufferBuilder.drawMode

		if (glMode != GL_QUADS) {
			throw IllegalArgumentException("BufferBuilder must be in GL_QUADS ($GL_QUADS) mode")
		}

		val vertexFormat = bufferBuilder.vertexFormat

		if (vertexFormat != DefaultVertexFormats.POSITION_TEX) {
			throw IllegalArgumentException("BufferBuilder is not in POSITION_TEX format")
		}

		for ((pos, tex) in vertices) {
			bufferBuilder.pos(pos.first, pos.second, pos.third).tex(tex.first, tex.second).endVertex()
		}

		return this
	}

	/**
	 * Copies [this shape's vertices][IRenderHelperShape.vertices] to
	 * [the `vertices` argument][vertices].
	 *
	 * Can be used by [IRenderHelperShape]s that want to use other
	 * [IRenderHelperShape]s in their construction. See [RenderHelperCube] for
	 * an example of this.
	 *
	 * @param vertices The array to copy
	 *                 [this shape's vertices][IRenderHelperShape.vertices] to.
	 * @return This shape, for chaining.
	 * @see regenerateVertices
	 */
	fun drawVerticesTo(vertices: NonNullList<Pair<Triple<Double, Double, Double>, Pair<Double, Double>>>): IRenderHelperShape {
		for (vertex in this.vertices) {
			vertices.add(vertex)
		}

		return this
	}

	/**
	 * Rotates this shape clockwise by [angle] radians on the X axis at [y],
	 * [z].
	 *
	 * As this operates directly on [vertices], don't forget to call
	 * [regenerateVertices] beforehand.
	 *
	 * @param z The Z coordinate of the axis to rotate upon.
	 * @param y The Y coordinate of the axis to rotate upon.
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *        negative.
	 * @return This shape, for chaining.
	 * @see rotateCenterX
	 */
	fun rotateX(y: Double, z: Double, angle: Double): IRenderHelperShape {
		for (index in vertices.indices) {
			val vertex = vertices[index]
			val pos = vertex.first
			val uv = vertex.second

			/**
			 * https://stackoverflow.com/a/12161405
			 *
			 * newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
			 * newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
			 */

			vertices[index] = Pair(
				Triple(
					pos.first,
					y + (pos.second - y) * Math.cos(angle) - (pos.third - z) * Math.sin(angle),
					z + (pos.second - y) * Math.sin(angle) + (pos.third - z) * Math.cos(angle)
				),
				uv
			)
		}

		return this
	}

	/**
	 * Rotates this shape clockwise by [angle] radians on the Y axis at [x],
	 * [z].
	 *
	 * As this operates directly on [vertices], don't forget to call
	 * [regenerateVertices] beforehand.
	 *
	 * @param x The X coordinate of the axis to rotate upon.
	 * @param z The Z coordinate of the axis to rotate upon.
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *        negative.
	 * @return This shape, for chaining.
	 * @see rotateCenterY
	 */
	fun rotateY(x: Double, z: Double, angle: Double): IRenderHelperShape {
		for (index in vertices.indices) {
			val vertex = vertices[index]
			val pos = vertex.first
			val uv = vertex.second

			/**
			 * https://stackoverflow.com/a/12161405
			 *
			 * newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
			 * newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
			 */

			vertices[index] = Pair(
				Triple(
					x + (pos.first - x) * Math.cos(angle) - (pos.third - z) * Math.sin(angle),
					pos.second,
					z + (pos.first - x) * Math.sin(angle) + (pos.third - z) * Math.cos(angle)
				),
				uv
			)
		}

		return this
	}

	/**
	 * Rotates this shape clockwise by [angle] radians on the Z axis at [x],
	 * [y].
	 *
	 * As this operates directly on [vertices], don't forget to call
	 * [regenerateVertices] beforehand.
	 *
	 * @param x The X coordinate of the axis to rotate upon.
	 * @param y The Y coordinate of the axis to rotate upon.
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *        negative.
	 * @return This shape, for chaining.
	 * @see rotateCenterZ
	 */
	fun rotateZ(x: Double, y: Double, angle: Double): IRenderHelperShape {
		for (index in vertices.indices) {
			val vertex = vertices[index]
			val pos = vertex.first
			val uv = vertex.second

			/**
			 * https://stackoverflow.com/a/12161405
			 *
			 * newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
			 * newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
			 */

			vertices[index] = Pair(
				Triple(
					x + (pos.first - x) * Math.cos(angle) - (pos.second - y) * Math.sin(angle),
					y + (pos.first - x) * Math.sin(angle) + (pos.second - y) * Math.cos(angle),
					pos.third
				),
				uv
			)
		}

		return this
	}

	/**
	 * Rotate this shape clockwise [angle] radians upon the X axis that
	 * intersects with the center of this shape, defined by [y] and [z].
	 *
	 * See [rotateX] for more information. You still should not forget to call
	 * [regenerateVertices] before calling this.
	 *
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *              negative.
	 * @return This shape, for chaining.
	 * @see rotateX
	 */
	fun rotateCenterX(angle: Double) = rotateX(y, z, angle)

	/**
	 * Rotate this shape clockwise [angle] radians upon the Y axis that
	 * intersects with the center of this shape, defined by [x] and [z].
	 *
	 * See [rotateX] for more information. You still should not forget to call
	 * [regenerateVertices] before calling this.
	 *
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *              negative.
	 * @return This shape, for chaining.
	 * @see rotateY
	 */
	fun rotateCenterY(angle: Double) = rotateY(x, z, angle)

	/**
	 * Rotate this shape clockwise [angle] radians upon the Z axis that
	 * intersects with the center of this shape, defined by [x] and [y].
	 *
	 * See [rotateX] for more information. You still should not forget to call
	 * [regenerateVertices] before calling this.
	 *
	 * @param angle Amount of clockwise rotation in radians. This can be
	 *              negative.
	 * @return This shape, for chaining.
	 * @see rotateZ
	 */
	fun rotateCenterZ(angle: Double) = rotateZ(x, y, angle)
}