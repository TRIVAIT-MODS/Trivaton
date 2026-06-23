package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.THIORITE_STONE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENRICHED_THIORITE_STONE);

        TextureMap circuit_board_textures = new TextureMap()
                .put(TextureKey.TOP, Trivaton.id("block/circuit_board_crafter_top"))
                .put(TextureKey.BOTTOM, Trivaton.id("block/stone_thiorite"))
                .put(TextureKey.SIDE, Trivaton.id("block/circuit_board_crafter_side"));
        blockStateModelGenerator.registerSingleton(
                ModBlocks.CIRCUIT_BOARD_CRAFTER,
                circuit_board_textures,
                Models.CUBE_BOTTOM_TOP
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.THIORITE_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.THIORITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENRICHED_THIORITE_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.ENRICHED_THIORITE_INGOT, Models.GENERATED);
    }
}