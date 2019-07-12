package com.famous5000.chaos.subscribers

import com.famous5000.chaos.items.ChaosStarItem
import com.famous5000.chaos.modid
import net.minecraft.entity.boss.EntityWither
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object ReplaceWitherDrops {
	@JvmStatic
	@SubscribeEvent
	fun onLivingDrops(event: LivingDropsEvent) {
		if (event.entity is EntityWither) {
			for (drop in event.drops) {
				if (drop.item.item == Items.NETHER_STAR) {
					drop.item = ItemStack(ChaosStarItem, drop.item.count)
				}
			}
		}
	}
}