package com.famous5000.chaos.blocks

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.modid
import com.famous5000.chaos.tileentities.ChaosInfuserT0TileEntity
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

object ChaosInfuserT0Block : Block(Material.ROCK) {
	init {
		unlocalizedName = "$modid.chaos_infuser_t0"
		registryName = ResourceLocation(modid, "chaos_infuser_t0")
		setCreativeTab(ChaosTab)
	}

	@Suppress("overridingDeprecatedMember")
	override fun isOpaqueCube(state: IBlockState) = false

	@Suppress("overridingDeprecatedMember")
	override fun isFullCube(state: IBlockState) = false

	@Suppress("overridingDeprecatedMember")
	override fun getBlockFaceShape(worldIn: IBlockAccess, state: IBlockState, pos: BlockPos, face: EnumFacing) =
		if (face == EnumFacing.DOWN) BlockFaceShape.SOLID
		else BlockFaceShape.UNDEFINED

	@Suppress("overridingDeprecatedMember")
	override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos): AxisAlignedBB {
		return AxisAlignedBB(.0, .0, .0, 1.0, .75, 1.0)
	}

	override fun hasTileEntity(state: IBlockState) = true

	override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
		return ChaosInfuserT0TileEntity()
	}
}