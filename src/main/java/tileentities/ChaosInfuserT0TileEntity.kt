package com.famous5000.chaos.tileentities

import com.famous5000.chaos.enums.ChaosTier
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString

class ChaosInfuserT0TileEntity : ChaosEnergyUserTileEntity(ChaosTier.T0, chaosEnergyCapacity = 1000000) {
	override fun getDisplayName(): ITextComponent? {
		return TextComponentString("Chaos Infuser (Tier 0)").setStyle(Style().setColor(getChaosTier().rarity.rarityColor))
	}
}