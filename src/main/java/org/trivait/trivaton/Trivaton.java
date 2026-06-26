package org.trivait.trivaton;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.gui.ModScreenHandlers;
import org.trivait.trivaton.item.ModItemGroups;
import org.trivait.trivaton.item.ModItems;
import org.trivait.trivaton.item.component.ModDataComponentTypes;
import org.trivait.trivaton.recipe.ModRecipes;
import org.trivait.trivaton.sound.ModSounds;
import org.trivait.trivaton.util.HammerUsageEvent;
import org.trivait.trivaton.world.gen.ModWorldGeneration;

public class Trivaton implements ModInitializer {
	public static final String MOD_ID = "trivaton";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModDataComponentTypes.register();
		ModItemGroups.register();
		ModItems.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModScreenHandlers.register();
		ModWorldGeneration.generateModWorldGen();
		ModSounds.register();

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
