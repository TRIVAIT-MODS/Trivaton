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
                        entries.add(ModBlocks.ENRICHED_THIORITE_STONE);
                        entries.add(ModBlocks.SIEVE);
                        entries.add(ModBlocks.CIRCUIT_BOARD_CRAFTER);

                        entries.add(ModItems.THIORITE_SHARD);
                        entries.add(ModItems.THIORITE_INGOT);
                        entries.add(ModItems.ENRICHED_THIORITE_SHARD);
                        entries.add(ModItems.ENRICHED_THIORITE_INGOT);
                    }).build());

    public static void register() {

    }
}
