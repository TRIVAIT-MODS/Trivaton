package org.trivait.trivaton.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.item.custom.CircuitBoardItem;
import org.trivait.trivaton.item.custom.HammerItem;

public class ModItems {

    public static final Item THIORITE_SHARD = registerItem("thiorite_shard", new Item(new Item.Settings()));
    public static final Item THIORITE_INGOT = registerItem("thiorite_ingot", new Item(new Item.Settings()));

    public static final Item ENRICHED_THIORITE_SHARD = registerItem("enriched_thiorite_shard", new Item(new Item.Settings()));
    public static final Item ENRICHED_THIORITE_INGOT = registerItem("enriched_thiorite_ingot", new Item(new Item.Settings()));

    public static final Item THIORITE_CRYSTAL = registerItem("thiorite_crystal", new Item(new Item.Settings()));

    public static final Item CIRCUIT_BOARD_1 = registerItem("circuit_board_1", new CircuitBoardItem(new Item.Settings().maxDamage(1000), 1));
    public static final Item CIRCUIT_BOARD_2 = registerItem("circuit_board_2", new CircuitBoardItem(new Item.Settings().maxDamage(1100), 2));
    public static final Item CIRCUIT_BOARD_3 = registerItem("circuit_board_3", new CircuitBoardItem(new Item.Settings().maxDamage(1250), 3));

    public static final Item THIORITE_SWORD = registerItem("thiorite_sword",
            new SwordItem(ModToolMaterials.THIORITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.THIORITE, 3, -2.4F))));
    public static final Item THIORITE_GARNET_PICKAXE = registerItem("thiorite_pickaxe",
            new PickaxeItem(ModToolMaterials.THIORITE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.THIORITE, 1, -2.8f))));
    public static final Item THIORITE_GARNET_SHOVEL = registerItem("thiorite_shovel",
            new ShovelItem(ModToolMaterials.THIORITE, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.THIORITE, 1.5f, -3.0f))));
    public static final Item THIORITE_GARNET_AXE = registerItem("thiorite_axe",
            new AxeItem(ModToolMaterials.THIORITE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.THIORITE, 6, -3.0f))));

    public static final Item THIORITE_HELMET = registerItem("thiorite_helmet",
            new ArmorItem(ModArmorMaterials.THIORITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37))));
    public static final Item THIORITE_CHESTPLATE = registerItem("thiorite_chestplate",
            new ArmorItem(ModArmorMaterials.THIORITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(37))));
    public static final Item THIORITE_LEGGINGS = registerItem("thiorite_leggings",
            new ArmorItem(ModArmorMaterials.THIORITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(37))));
    public static final Item THIORITE_BOOTS = registerItem("thiorite_boots",
            new ArmorItem(ModArmorMaterials.THIORITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(37))));

    public static final Item THIORITE_HAMMER = registerItem("thiorite_hammer",
            new HammerItem(ModToolMaterials.THIORITE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.THIORITE, 7, -3.4f))));

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, Trivaton.id(id), item);
    }

    public static void register() {

    }
}
