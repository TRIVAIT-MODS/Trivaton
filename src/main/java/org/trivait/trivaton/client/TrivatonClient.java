package org.trivait.trivaton.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.text.TranslatableTextContent;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.block.entity.renderer.SieveBlockEntityRenderer;
import org.trivait.trivaton.gui.ModScreenHandlers;
import org.trivait.trivaton.gui.custom.CircuitBoardCrafterScreen;
import org.trivait.trivaton.gui.custom.SieveScreen;
import org.trivait.trivaton.gui.custom.ThioriteCrystalGenratorScreen;
import org.trivait.trivaton.item.custom.CircuitBoardItem;

public class TrivatonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.SIEVE_BE, SieveBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.SIEVE_SCREEN_HANDLER, SieveScreen::new);
        HandledScreens.register(ModScreenHandlers.CIRCUIT_BOARD_CRAFTER_SCREEN_HANDLER, CircuitBoardCrafterScreen::new);
        HandledScreens.register(ModScreenHandlers.THIORITE_CRYSTAL_GENERATOR_SCREEN_HANDLER, ThioriteCrystalGenratorScreen::new);

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (stack.getItem() instanceof CircuitBoardItem) {
                lines.removeIf(text -> text.getContent() instanceof TranslatableTextContent translatable
                        && "item.durability".equals(translatable.getKey()));
            }
        });
    }
}
