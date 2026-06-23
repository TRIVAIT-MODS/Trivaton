package org.trivait.trivaton.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.custom.SieveBlock;

public class ModBlocks {

    public static final Block THIORITE_STONE = registerBlock("thiorite_stone", new Block(AbstractBlock.Settings.copy(Blocks.TUFF)));
    public static final Block ENRICHED_THIORITE_STONE = registerBlock("enriched_thiorite_stone", new Block(AbstractBlock.Settings.copy(Blocks.TUFF)));
    public static final Block SIEVE = registerBlock("sieve", new SieveBlock(AbstractBlock.Settings.copy(Blocks.COPPER_BLOCK)));
    public static final Block CIRCUIT_BOARD_CRAFTER = registerBlock("circuit_board_crafter", new Block(AbstractBlock.Settings.copy(Blocks.TUFF)));

    private static Block registerBlock(String name, Block block){
        Identifier id = Trivaton.id(name);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void register() {

    }
}
