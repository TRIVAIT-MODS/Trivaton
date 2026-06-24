package org.trivait.trivaton.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.trivait.trivaton.Trivaton;

public class ModItemTags {
    private static TagKey<Item> createTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, Trivaton.id(name));
    }
}
