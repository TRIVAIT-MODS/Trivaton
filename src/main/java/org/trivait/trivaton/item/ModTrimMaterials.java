package org.trivait.trivaton.item;

import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.trivait.trivaton.Trivaton;

import java.util.Map;

public class ModTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> THIORITE = RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Trivaton.id("thiorite"));
    public static final RegistryKey<ArmorTrimMaterial> MYTHIUM = RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Trivaton.id("mythium"));

    public static void bootstrap(Registerable<ArmorTrimMaterial> registerable) {
        register(registerable, THIORITE, Registries.ITEM.getEntry(ModItems.THIORITE_INGOT),
                Style.EMPTY.withColor(TextColor.parse("#757575").getOrThrow()), 0.3f);
        register(registerable, MYTHIUM, Registries.ITEM.getEntry(ModItems.MYTHIUM_INGOT),
                Style.EMPTY.withColor(TextColor.parse("#0094FF").getOrThrow()), 0.9f);
    }

    private static void register(Registerable<ArmorTrimMaterial> registerable, RegistryKey<ArmorTrimMaterial> armorTrimKey,
                                 RegistryEntry<Item> item, Style style, float itemModelIndex) {
        ArmorTrimMaterial trimMaterial = new ArmorTrimMaterial(armorTrimKey.getValue().getPath(), item, itemModelIndex, Map.of(),
                Text.translatable(Util.createTranslationKey("trim_material", armorTrimKey.getValue())).fillStyle(style));

        registerable.register(armorTrimKey, trimMaterial);
    }
}