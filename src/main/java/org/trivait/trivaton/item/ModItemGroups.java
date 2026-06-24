package org.trivait.trivaton.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup TRIVATON = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Trivaton.MOD_ID, "trivaton"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.THIORITE_STONE))
                    .displayName(Text.translatable("itemgroup.trivaton"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.THIORITE_STONE);
                        entries.add(ModBlocks.THIORITE_STONE_STAIRS);
                        entries.add(ModBlocks.THIORITE_STONE_SLAB);
                        entries.add(ModBlocks.THIORITE_STONE_WALL);

                        entries.add(ModBlocks.POLISHED_THIORITE_STONE);
                        entries.add(ModBlocks.POLISHED_THIORITE_STONE_STAIRS);
                        entries.add(ModBlocks.POLISHED_THIORITE_STONE_SLAB);
                        entries.add(ModBlocks.POLISHED_THIORITE_STONE_WALL);

                        entries.add(ModBlocks.THIORITE_STONE_BRICKS);
                        entries.add(ModBlocks.THIORITE_STONE_BRICKS_STAIRS);
                        entries.add(ModBlocks.THIORITE_STONE_BRICKS_SLAB);
                        entries.add(ModBlocks.THIORITE_STONE_BRICKS_WALL);

                        entries.add(ModBlocks.ENRICHED_THIORITE_STONE);
                        entries.add(ModBlocks.SIEVE);
                        entries.add(ModBlocks.CIRCUIT_BOARD_CRAFTER);
                        entries.add(ModBlocks.THIORITE_CRYSTAL_GENERATOR);

                        entries.add(ModBlocks.THIORITE_BLOCK);

                        entries.add(ModItems.THIORITE_SHARD);
                        entries.add(ModItems.THIORITE_INGOT);
                        entries.add(ModItems.ENRICHED_THIORITE_SHARD);
                        entries.add(ModItems.ENRICHED_THIORITE_INGOT);
                        entries.add(ModItems.THIORITE_CRYSTAL);

                        entries.add(ModItems.CIRCUIT_BOARD_1);
                        entries.add(ModItems.CIRCUIT_BOARD_2);
                        entries.add(ModItems.CIRCUIT_BOARD_3);

                        entries.add(ModItems.THIORITE_GARNET_PICKAXE);
                        entries.add(ModItems.THIORITE_GARNET_AXE);
                        entries.add(ModItems.THIORITE_GARNET_SHOVEL);
                        entries.add(ModItems.THIORITE_SWORD);

                        entries.add(ModItems.THIORITE_HELMET);
                        entries.add(ModItems.THIORITE_CHESTPLATE);
                        entries.add(ModItems.THIORITE_LEGGINGS);
                        entries.add(ModItems.THIORITE_BOOTS);

                        entries.add(ModItems.THIORITE_HAMMER);
                    }).build());

    public static void register() {

    }
}
