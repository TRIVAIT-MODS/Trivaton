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
        addDrop(ModBlocks.THIORITE_CRYSTAL_GENERATOR);
        addDrop(ModBlocks.THIORITE_BLOCK);
        addDrop(ModBlocks.THIORITE_STONE_STAIRS);
        addDrop(ModBlocks.THIORITE_STONE_SLAB);
        addDrop(ModBlocks.THIORITE_STONE_WALL);
        addDrop(ModBlocks.POLISHED_THIORITE_STONE);
        addDrop(ModBlocks.POLISHED_THIORITE_STONE_STAIRS);
        addDrop(ModBlocks.POLISHED_THIORITE_STONE_SLAB);
        addDrop(ModBlocks.POLISHED_THIORITE_STONE_WALL);
        addDrop(ModBlocks.THIORITE_STONE_BRICKS);
        addDrop(ModBlocks.THIORITE_STONE_BRICKS_STAIRS);
        addDrop(ModBlocks.THIORITE_STONE_BRICKS_SLAB);
        addDrop(ModBlocks.THIORITE_STONE_BRICKS_WALL);
    }
}