package org.trivait.trivaton.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public record GeneratorRecipe(Ingredient centerInput, List<Ingredient> sideInputs, ItemStack output) implements Recipe<GeneratorRecipeInput> {

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.centerInput);
        list.addAll(this.sideInputs);
        return list;
    }

    @Override
    public boolean matches(GeneratorRecipeInput input, World world) {
        if (world.isClient()) {
            return false;
        }

        if (!centerInput.test(input.getCenterStack())) {
            return false;
        }

        List<ItemStack> availableSides = input.getSideStacks();
        if (availableSides.size() != sideInputs.size()) {
            return false;
        }

        List<Ingredient> requiredSides = new ArrayList<>(sideInputs);

        for (ItemStack stack : availableSides) {
            boolean matched = false;
            for (int i = 0; i < requiredSides.size(); i++) {
                if (requiredSides.get(i).test(stack)) {
                    requiredSides.remove(i);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }

        return requiredSides.isEmpty();
    }

    @Override
    public ItemStack craft(GeneratorRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GENERATOR_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GENERATOR_TYPE;
    }

    public static class Serializer implements RecipeSerializer<GeneratorRecipe> {
        public static final MapCodec<GeneratorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("center").forGetter(GeneratorRecipe::centerInput),
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("sides").forGetter(GeneratorRecipe::sideInputs),
                ItemStack.CODEC.fieldOf("result").forGetter(GeneratorRecipe::output)
        ).apply(inst, GeneratorRecipe::new));

        public static final PacketCodec<RegistryByteBuf, GeneratorRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, GeneratorRecipe::centerInput,
                        Ingredient.PACKET_CODEC.collect(net.minecraft.network.codec.PacketCodecs.toList()), GeneratorRecipe::sideInputs,
                        ItemStack.PACKET_CODEC, GeneratorRecipe::output,
                        GeneratorRecipe::new);

        @Override
        public MapCodec<GeneratorRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, GeneratorRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
