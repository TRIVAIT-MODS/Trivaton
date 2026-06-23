package org.trivait.trivaton.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.trivait.trivaton.Trivaton;

public class ModRecipes {
    public static final RecipeSerializer<SieveRecipe> SIEVE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Trivaton.id("sieve"),
            new SieveRecipe.Serializer()
    );
    public static final RecipeType<SieveRecipe> SIEVE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Trivaton.id("sieve"), new RecipeType<SieveRecipe>() {
                @Override
                public String toString() {
                    return "sieve";
                }
            }
    );

    public static void register() {

    }
}
