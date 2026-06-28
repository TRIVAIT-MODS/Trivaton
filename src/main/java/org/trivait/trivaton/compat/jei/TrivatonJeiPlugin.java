package org.trivait.trivaton.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.gui.ModScreenHandlers;
import org.trivait.trivaton.gui.custom.*;
import org.trivait.trivaton.item.ModItems;
import org.trivait.trivaton.recipe.*;

import java.util.List;

@JeiPlugin
public class TrivatonJeiPlugin implements IModPlugin {

    private static final Identifier UID =
            Identifier.of(Trivaton.MOD_ID, "jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(
            IRecipeCategoryRegistration registration
    ) {

        registration.addRecipeCategories(
                new SieveRecipeCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
        registration.addRecipeCategories(
                new CircuitBoardCrafterCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
        registration.addRecipeCategories(
                new ThioriteCrystalGeneratorCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
        registration.addRecipeCategories(
                new GeneratorCategory(
                        registration
                                .getJeiHelpers()
                                .getGuiHelper()
                )
        );
    }

    @Override
    public void registerRecipes(
            IRecipeRegistration registration
    ) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null) {
            return;
        }

        RecipeManager recipeManager =
                client.world.getRecipeManager();

        List<SieveRecipe> sieveRecipes =
                recipeManager.listAllOfType(ModRecipes.SIEVE_TYPE)
                        .stream()
                        .map(RecipeEntry::value)
                        .toList();

        registration.addRecipes(
                SieveRecipeCategory.TYPE,
                sieveRecipes
        );
        List<CircuitBoardCrafterRecipe> circuitBoardCrafterRecipes =
                recipeManager.listAllOfType(ModRecipes.CIRCUIT_BOARD_CRAFTER_TYPE)
                        .stream()
                        .map(RecipeEntry::value)
                        .toList();

        registration.addRecipes(
                CircuitBoardCrafterCategory.TYPE,
                circuitBoardCrafterRecipes
        );
        registration.addRecipes(
                ThioriteCrystalGeneratorCategory.TYPE,
                List.of(
                        new ThioriteCrystalGeneratorRecipe(
                                new ItemStack(ModItems.THIORITE_CRYSTAL)
                        )
                )
        );
        List<GeneratorRecipe> generatorRecipes =
                recipeManager.listAllOfType(ModRecipes.GENERATOR_TYPE)
                        .stream()
                        .map(RecipeEntry::value)
                        .toList();

        registration.addRecipes(
                GeneratorCategory.TYPE,
                generatorRecipes
        );
    }

    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.SIEVE),
                SieveRecipeCategory.TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.CIRCUIT_BOARD_CRAFTER),
                CircuitBoardCrafterCategory.TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.THIORITE_CRYSTAL_GENERATOR),
                ThioriteCrystalGeneratorCategory.TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.GENERATOR),
                GeneratorCategory.TYPE
        );
    }

    @Override
    public void registerGuiHandlers(
            IGuiHandlerRegistration registration
    ) {
        registration.addRecipeClickArea(
                SieveScreen.class,
                64,
                35,
                24,
                16,
                SieveRecipeCategory.TYPE
        );
        registration.addRecipeClickArea(
                CircuitBoardCrafterScreen.class,
                92,
                35,
                24,
                16,
                CircuitBoardCrafterCategory.TYPE
        );
        registration.addRecipeClickArea(
                ThioriteCrystalGenratorScreen.class,
                80,
                33,
                16,
                24,
                ThioriteCrystalGeneratorCategory.TYPE
        );
        registration.addRecipeClickArea(
                GeneratorScreen.class,
                105,
                35,
                24,
                16,
                GeneratorCategory.TYPE
        );
    }
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                CircuitBoardCrafterScreenHandler.class,
                ModScreenHandlers.CIRCUIT_BOARD_CRAFTER_SCREEN_HANDLER,
                CircuitBoardCrafterCategory.TYPE,
                0, 10,
                11, 36
        );
        registration.addRecipeTransferHandler(
                SieveScreenHandler.class,
                ModScreenHandlers.SIEVE_SCREEN_HANDLER,
                SieveRecipeCategory.TYPE,
                0, 1,
                7, 36
        );
        registration.addRecipeTransferHandler(
                GeneratorScreenHandler.class,
                ModScreenHandlers.GENERATOR_SCREEN_HANDLER,
                GeneratorCategory.TYPE,
                0, 10,
                11, 36
        );
    }
}