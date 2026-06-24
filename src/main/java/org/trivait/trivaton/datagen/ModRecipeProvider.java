package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.item.ModItemGroups;
import org.trivait.trivaton.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.THIORITE_SHARD, RecipeCategory.MISC, ModItems.THIORITE_INGOT);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.ENRICHED_THIORITE_SHARD, RecipeCategory.MISC, ModItems.ENRICHED_THIORITE_INGOT);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.THIORITE_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_BLOCK,
                "thiorite_block_from_thiorite_ingots", null,
                "thiorite_ingot_from_thiorite_block", null);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SIEVE)
                .pattern("TST")
                .pattern("C C")
                .pattern("CCC")
                .input('T', ModBlocks.THIORITE_STONE)
                .input('S', Items.STRING)
                .input('C', Items.COPPER_INGOT)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ENRICHED_THIORITE_STONE)
                .pattern("TTT")
                .pattern("TET")
                .pattern("TTT")
                .input('T', ModBlocks.THIORITE_STONE)
                .input('E', ModItems.ENRICHED_THIORITE_SHARD)
                .criterion(hasItem(ModItems.ENRICHED_THIORITE_SHARD), conditionsFromItem(ModItems.ENRICHED_THIORITE_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CIRCUIT_BOARD_CRAFTER)
                .pattern("PPP")
                .pattern("TET")
                .input('T', ModBlocks.THIORITE_STONE)
                .input('E', ModItems.ENRICHED_THIORITE_INGOT)
                .input('P', ItemTags.PLANKS)
                .criterion(hasItem(ModItems.ENRICHED_THIORITE_INGOT), conditionsFromItem(ModItems.ENRICHED_THIORITE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_HAMMER)
                .pattern("TCT")
                .pattern("TCT")
                .pattern(" S ")
                .input('T', ModItems.THIORITE_INGOT)
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModBlocks.ENRICHED_THIORITE_STONE), conditionsFromItem(ModBlocks.ENRICHED_THIORITE_STONE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.THIORITE_CRYSTAL_GENERATOR)
                .pattern("GGG")
                .pattern("GEG")
                .pattern("TTT")
                .input('T', ModBlocks.POLISHED_THIORITE_STONE)
                .input('E', ModBlocks.ENRICHED_THIORITE_STONE)
                .input('G', Blocks.GLASS)
                .criterion(hasItem(ModBlocks.ENRICHED_THIORITE_STONE), conditionsFromItem(ModBlocks.ENRICHED_THIORITE_STONE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_GARNET_AXE)
                .pattern("TC ")
                .pattern("TS ")
                .pattern(" S ")
                .input('T', ModItems.THIORITE_INGOT)
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter, Trivaton.id("thiorite_axe_1"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_GARNET_AXE)
                .pattern(" CT")
                .pattern(" ST")
                .pattern(" S ")
                .input('T', ModItems.THIORITE_INGOT)
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter, Trivaton.id("thiorite_axe_2"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_GARNET_SHOVEL)
                .pattern(" C ")
                .pattern(" S ")
                .pattern(" S ")
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_GARNET_PICKAXE)
                .pattern("TCT")
                .pattern(" S ")
                .pattern(" S ")
                .input('T', ModItems.THIORITE_INGOT)
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_SWORD)
                .pattern("C")
                .pattern("T")
                .pattern("S")
                .input('T', ModItems.THIORITE_INGOT)
                .input('S', Items.STICK)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_HELMET)
                .pattern("TCT")
                .pattern("T T")
                .input('T', ModItems.THIORITE_INGOT)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_CHESTPLATE)
                .pattern("T T")
                .pattern("TCT")
                .pattern("TTT")
                .input('T', ModItems.THIORITE_INGOT)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_LEGGINGS)
                .pattern("TCT")
                .pattern("T T")
                .pattern("T T")
                .input('T', ModItems.THIORITE_INGOT)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_BOOTS)
                .pattern("C T")
                .pattern("T T")
                .input('T', ModItems.THIORITE_INGOT)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter, Trivaton.id("thiorite_boots_1"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THIORITE_BOOTS)
                .pattern("T C")
                .pattern("T T")
                .input('T', ModItems.THIORITE_INGOT)
                .input('C', ModItems.THIORITE_CRYSTAL)
                .criterion(hasItem(ModItems.THIORITE_CRYSTAL), conditionsFromItem(ModItems.THIORITE_CRYSTAL))
                .offerTo(exporter, Trivaton.id("thiorite_boots_2"));

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_SLAB, 2)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_slab"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_STAIRS, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_stairs"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_WALL, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_wall"));

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_polished_thiorite_stone"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_SLAB, 2)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_polished_thiorite_stone_slab"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_STAIRS, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_polished_thiorite_stone_stairs"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_WALL, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_polished_thiorite_stone_wall"));

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_bricks"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_SLAB, 2)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_bricks_slab"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_STAIRS, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_bricks_stairs"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_WALL, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE), conditionsFromItem(ModBlocks.THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("thiorite_stone_to_thiorite_stone_bricks_wall"));

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.POLISHED_THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_SLAB, 2)
                .criterion(hasItem(ModBlocks.POLISHED_THIORITE_STONE), conditionsFromItem(ModBlocks.POLISHED_THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("polished_thiorite_stone_to_polished_thiorite_stone_slab"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.POLISHED_THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_STAIRS, 1)
                .criterion(hasItem(ModBlocks.POLISHED_THIORITE_STONE), conditionsFromItem(ModBlocks.POLISHED_THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("polished_thiorite_stone_to_polished_thiorite_stone_stairs"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.POLISHED_THIORITE_STONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_THIORITE_STONE_WALL, 1)
                .criterion(hasItem(ModBlocks.POLISHED_THIORITE_STONE), conditionsFromItem(ModBlocks.POLISHED_THIORITE_STONE))
                .offerTo(exporter, Trivaton.id("polished_thiorite_stone_to_polished_thiorite_stone_wall"));

        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_SLAB, 2)
                .criterion(hasItem(ModBlocks.THIORITE_STONE_BRICKS), conditionsFromItem(ModBlocks.THIORITE_STONE_BRICKS))
                .offerTo(exporter, Trivaton.id("thiorite_stone_bricks_to_thiorite_stone_bricks_slab"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_STAIRS, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE_BRICKS), conditionsFromItem(ModBlocks.THIORITE_STONE_BRICKS))
                .offerTo(exporter, Trivaton.id("thiorite_stone_bricks_to_thiorite_stone_bricks_stairs"));
        StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ModBlocks.THIORITE_STONE_BRICKS), RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIORITE_STONE_BRICKS_WALL, 1)
                .criterion(hasItem(ModBlocks.THIORITE_STONE_BRICKS), conditionsFromItem(ModBlocks.THIORITE_STONE_BRICKS))
                .offerTo(exporter, Trivaton.id("thiorite_stone_bricks_to_thiorite_stone_bricks_wall"));
    }
}