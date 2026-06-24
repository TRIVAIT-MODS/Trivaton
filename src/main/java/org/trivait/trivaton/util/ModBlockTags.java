package org.trivait.trivaton.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;

public class ModBlockTags {
    private static TagKey<Block> createTag(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Trivaton.id(name));
    }
}
