package com.famous5000.chaos.properties

import com.famous5000.chaos.enums.ChaosTier
import com.google.common.base.Optional
import net.minecraft.block.properties.PropertyHelper

object PropertyChaosTier : PropertyHelper<ChaosTier>("chaos_tier", ChaosTier::class.java) {
	override fun getAllowedValues(): MutableCollection<ChaosTier> = ChaosTier.values().toMutableList()

	override fun parseValue(value: String): Optional<ChaosTier> {
		return value.let { name -> ChaosTier.values().find { it.name.toLowerCase() == name } }
			       ?.let { Optional.of(it) }
		       ?: Optional.absent<ChaosTier>()
	}

	override fun getName(value: ChaosTier) = value.name.toLowerCase()
}