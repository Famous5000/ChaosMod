package com.famous5000.chaos.blocks

import com.famous5000.chaos.ChaosTab
import com.famous5000.chaos.enums.ChaosTier
import com.famous5000.chaos.interfaces.IBlockHasChaosTier
import com.famous5000.chaos.items.ChaosBlockItem
import com.famous5000.chaos.modid
import com.famous5000.chaos.properties.PropertyChaosTier
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World

object ChaosBlock : Block(Material.IRON), IBlockHasChaosTier {
	init {
		unlocalizedName = "$modid.chaos_block"
		registryName = ResourceLocation(modid, "chaos_block")
		defaultState = this.blockState.baseState.withProperty(PropertyChaosTier, ChaosTier.T0)
		setCreativeTab(ChaosTab)
	}

	override fun createBlockState() = BlockStateContainer(this, PropertyChaosTier)
	override fun getChaosTier(blockState: IBlockState): ChaosTier = blockState.getValue(PropertyChaosTier)
	override fun getMetaFromState(state: IBlockState) = state.getValue(PropertyChaosTier).ordinal

	override fun getStateFromMeta(meta: Int) =
		defaultState.withProperty(PropertyChaosTier, ChaosTier.values().find { it.ordinal == meta }!!)

	override fun getStateForPlacement(world: World, pos: BlockPos, facing: EnumFacing,
	                                  hitX: Float, hitY: Float, hitZ: Float,
	                                  meta: Int, placer: EntityLivingBase,
	                                  hand: EnumHand): IBlockState = getStateFromMeta(meta)

	override fun getPickBlock(state: IBlockState, target: RayTraceResult,
	                          world: World, pos: BlockPos, player: EntityPlayer) =
		ItemStack(ChaosBlockItem, 1, getMetaFromState(state))
}