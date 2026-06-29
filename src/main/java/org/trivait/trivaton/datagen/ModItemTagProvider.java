package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.trivait.trivaton.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.THIORITE_PICKAXE)
                .add(ModItems.MYTHIUM_PICKAXE)
                .add(ModItems.MYTHIUM_HAMMER)
                .add(ModItems.THIORITE_HAMMER);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.THIORITE_AXE)
                .add(ModItems.MYTHIUM_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.THIORITE_SHOVEL)
                .add(ModItems.MYTHIUM_SHOVEL);
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.THIORITE_SWORD)
                .add(ModItems.MYTHIUM_SWORD);
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.THIORITE_HELMET)
                .add(ModItems.MYTHIUM_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.THIORITE_CHESTPLATE)
                .add(ModItems.MYTHIUM_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.THIORITE_LEGGINGS)
                .add(ModItems.MYTHIUM_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.THIORITE_BOOTS)
                .add(ModItems.MYTHIUM_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.THIORITE_HELMET)
                .add(ModItems.THIORITE_CHESTPLATE)
                .add(ModItems.THIORITE_LEGGINGS)
                .add(ModItems.THIORITE_BOOTS)
                .add(ModItems.MYTHIUM_HELMET)
                .add(ModItems.MYTHIUM_CHESTPLATE)
                .add(ModItems.MYTHIUM_LEGGINGS)
                .add(ModItems.MYTHIUM_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(ModItems.THIORITE_INGOT)
                .add(ModItems.MYTHIUM_INGOT);
    }
}