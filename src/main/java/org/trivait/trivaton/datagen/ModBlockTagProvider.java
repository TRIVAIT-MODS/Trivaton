package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.trivait.trivaton.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SIEVE)
                .add(ModBlocks.ENRICHED_THIORITE_STONE)
                .add(ModBlocks.CIRCUIT_BOARD_CRAFTER)
                .add(ModBlocks.THIORITE_CRYSTAL_GENERATOR)
                .add(ModBlocks.THIORITE_STONE)
                .add(ModBlocks.THIORITE_STONE_STAIRS)
                .add(ModBlocks.THIORITE_STONE_SLAB)
                .add(ModBlocks.THIORITE_STONE_WALL)
                .add(ModBlocks.POLISHED_THIORITE_STONE)
                .add(ModBlocks.POLISHED_THIORITE_STONE_STAIRS)
                .add(ModBlocks.POLISHED_THIORITE_STONE_SLAB)
                .add(ModBlocks.POLISHED_THIORITE_STONE_WALL)
                .add(ModBlocks.THIORITE_STONE_BRICKS)
                .add(ModBlocks.THIORITE_STONE_BRICKS_STAIRS)
                .add(ModBlocks.THIORITE_STONE_BRICKS_SLAB)
                .add(ModBlocks.THIORITE_BLOCK)
                .add(ModBlocks.THIORITE_STONE_BRICKS_WALL);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.SIEVE)
                .add(ModBlocks.ENRICHED_THIORITE_STONE)
                .add(ModBlocks.CIRCUIT_BOARD_CRAFTER)
                .add(ModBlocks.THIORITE_CRYSTAL_GENERATOR)
                .add(ModBlocks.THIORITE_STONE)
                .add(ModBlocks.THIORITE_STONE_STAIRS)
                .add(ModBlocks.THIORITE_STONE_SLAB)
                .add(ModBlocks.THIORITE_STONE_WALL)
                .add(ModBlocks.POLISHED_THIORITE_STONE)
                .add(ModBlocks.POLISHED_THIORITE_STONE_STAIRS)
                .add(ModBlocks.POLISHED_THIORITE_STONE_SLAB)
                .add(ModBlocks.POLISHED_THIORITE_STONE_WALL)
                .add(ModBlocks.THIORITE_STONE_BRICKS)
                .add(ModBlocks.THIORITE_STONE_BRICKS_STAIRS)
                .add(ModBlocks.THIORITE_STONE_BRICKS_SLAB)
                .add(ModBlocks.THIORITE_BLOCK)
                .add(ModBlocks.THIORITE_STONE_BRICKS_WALL);
    }
}