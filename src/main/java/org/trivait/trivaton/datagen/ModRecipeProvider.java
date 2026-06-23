package org.trivait.trivaton.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
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
    }
}