package org.trivait.trivaton.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;

public class ModItems {

    public static final Item THIORITE_SHARD = registerItem("thiorite_shard", new Item(new Item.Settings()));
    public static final Item THIORITE_INGOT = registerItem("thiorite_ingot", new Item(new Item.Settings()));
    public static final Item ENRICHED_THIORITE_SHARD = registerItem("enriched_thiorite_shard", new Item(new Item.Settings()));
    public static final Item ENRICHED_THIORITE_INGOT = registerItem("enriched_thiorite_ingot", new Item(new Item.Settings()));

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, Trivaton.id(id), item);
    }

    public static void register() {

    }
}
