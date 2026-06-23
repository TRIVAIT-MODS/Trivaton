package org.trivait.trivaton.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.block.entity.renderer.SieveBlockEntityRenderer;
import org.trivait.trivaton.gui.ModScreenHandlers;
import org.trivait.trivaton.gui.custom.SieveScreen;

public class TrivatonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.SIEVE_BE, SieveBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.SIEVE_SCREEN_HANDLER, SieveScreen::new);
    }
}
