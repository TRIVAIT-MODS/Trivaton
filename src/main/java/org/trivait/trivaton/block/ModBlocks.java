package org.trivait.trivaton.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.custom.CircuitBoardCrafterBlock;
import org.trivait.trivaton.block.custom.SieveBlock;
import org.trivait.trivaton.block.custom.ThioriteCrystalGeneratorBlock;

public class ModBlocks {

    public static final Block THIORITE_STONE = registerBlock("thiorite_stone", new Block(AbstractBlock.Settings.copy(Blocks.TUFF)));
    public static final Block THIORITE_STONE_WALL = registerBlock("thiorite_stone_wall",
            new WallBlock(AbstractBlock.Settings.copy(Blocks.TUFF_WALL)));
    public static final Block THIORITE_STONE_STAIRS = registerBlock("thiorite_stone_stairs",
            new StairsBlock(ModBlocks.THIORITE_STONE.getDefaultState(),
                    AbstractBlock.Settings.copy(Blocks.TUFF_STAIRS)));
    public static final Block THIORITE_STONE_SLAB = registerBlock("thiorite_stone_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.TUFF_SLAB)));

    public static final Block ENRICHED_THIORITE_STONE = registerBlock("enriched_thiorite_stone", new Block(AbstractBlock.Settings.copy(Blocks.TUFF)));
    public static final Block SIEVE = registerBlock("sieve", new SieveBlock(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK)));
    public static final Block CIRCUIT_BOARD_CRAFTER = registerBlock("circuit_board_crafter", new CircuitBoardCrafterBlock(AbstractBlock.Settings.copy(Blocks.TUFF)));
    public static final Block THIORITE_CRYSTAL_GENERATOR = registerBlock("thiorite_crystal_generator", new ThioriteCrystalGeneratorBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));

    public static final Block POLISHED_THIORITE_STONE = registerBlock("polished_thiorite_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF)));
    public static final Block POLISHED_THIORITE_STONE_WALL = registerBlock("polished_thiorite_stone_wall",
            new WallBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_WALL)));
    public static final Block POLISHED_THIORITE_STONE_STAIRS = registerBlock("polished_thiorite_stone_stairs",
            new StairsBlock(ModBlocks.POLISHED_THIORITE_STONE.getDefaultState(),
                    AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_STAIRS)));
    public static final Block POLISHED_THIORITE_STONE_SLAB = registerBlock("polished_thiorite_stone_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_SLAB)));

    public static final Block THIORITE_STONE_BRICKS = registerBlock("thiorite_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.TUFF_BRICKS)));
    public static final Block THIORITE_STONE_BRICKS_WALL = registerBlock("thiorite_stone_bricks_wall",
            new WallBlock(AbstractBlock.Settings.copy(Blocks.TUFF_WALL)));
    public static final Block THIORITE_STONE_BRICKS_STAIRS = registerBlock("thiorite_stone_bricks_stairs",
            new StairsBlock(ModBlocks.THIORITE_STONE_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.copy(Blocks.TUFF_STAIRS)));
    public static final Block THIORITE_STONE_BRICKS_SLAB = registerBlock("thiorite_stone_bricks_slab",
            new SlabBlock(AbstractBlock.Settings.copy(Blocks.TUFF_SLAB)));

    public static final Block THIORITE_BLOCK = registerBlock("thiorite_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block){
        Identifier id = Trivaton.id(name);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void register() {

    }
}
