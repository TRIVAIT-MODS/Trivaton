package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import org.trivait.trivaton.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.THIORITE_STONE);
        addDrop(ModBlocks.SIEVE);
        addDrop(ModBlocks.ENRICHED_THIORITE_STONE);
        addDrop(ModBlocks.CIRCUIT_BOARD_CRAFTER);
    }
}