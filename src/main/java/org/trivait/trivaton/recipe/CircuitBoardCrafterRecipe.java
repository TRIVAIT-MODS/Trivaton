package org.trivait.trivaton.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.ArrayList;

public record CircuitBoardCrafterRecipe(
        DefaultedList<Ingredient> ingredients,
        ItemStack output,
        boolean isShaped
) implements Recipe<CraftingRecipeInput> {

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (world.isClient()) return false;

        if (!isShaped) {
            RecipeMatcher matcher = new RecipeMatcher();
            int inputCount = 0;

            for (int i = 0; i < input.getSize(); i++) {
                ItemStack stack = input.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    inputCount++;
                    matcher.addInput(stack, 1);
                }
            }

            return matcher.match(this, null);
        }

        for (int i = 0; i < 9; i++) {
            Ingredient ingredient = i < this.ingredients.size() ? this.ingredients.get(i) : Ingredient.EMPTY;
            ItemStack stack = i < input.getSize() ? input.getStackInSlot(i) : ItemStack.EMPTY;
            if (!ingredient.test(stack)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CIRCUIT_BOARD_CRAFTER_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CIRCUIT_BOARD_CRAFTER_TYPE;
    }

    public static class Serializer implements RecipeSerializer<CircuitBoardCrafterRecipe> {
        public static final MapCodec<CircuitBoardCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").xmap(
                        list -> {
                            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(9, Ingredient.EMPTY);
                            for (int i = 0; i < Math.min(list.size(), 9); i++) {
                                defaultedList.set(i, list.get(i));
                            }
                            return defaultedList;
                        },
                        ArrayList::new
                ).forGetter(CircuitBoardCrafterRecipe::ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(CircuitBoardCrafterRecipe::output),
                Codec.BOOL.optionalFieldOf("is_shaped", true).forGetter(CircuitBoardCrafterRecipe::isShaped)
        ).apply(inst, CircuitBoardCrafterRecipe::new));

        public static final PacketCodec<RegistryByteBuf, CircuitBoardCrafterRecipe> STREAM_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toCollection(DefaultedList::ofSize)), CircuitBoardCrafterRecipe::ingredients,
                ItemStack.PACKET_CODEC, CircuitBoardCrafterRecipe::output,
                PacketCodecs.BOOL, CircuitBoardCrafterRecipe::isShaped,
                CircuitBoardCrafterRecipe::new
        );

        @Override
        public MapCodec<CircuitBoardCrafterRecipe> codec() { return CODEC; }

        @Override
        public PacketCodec<RegistryByteBuf, CircuitBoardCrafterRecipe> packetCodec() { return STREAM_CODEC; }
    }
}
