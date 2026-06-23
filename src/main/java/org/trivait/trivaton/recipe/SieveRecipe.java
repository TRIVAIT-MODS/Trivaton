package org.trivait.trivaton.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public record SieveRecipe(Ingredient inputItem, ItemStack output, List<ExtraOutput> extraOutputs) implements Recipe<SieveRecipeInput> {

    public SieveRecipe {
        if (extraOutputs.size() > 5) {
            extraOutputs = extraOutputs.subList(0, 5);
        }
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean matches(SieveRecipeInput input, World world) {
        if (world.isClient) return false;
        return this.inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SieveRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack finalResult = this.output.copy();

        for (ExtraOutput extra : extraOutputs) {
            if (Math.random() < extra.chance()) {
                if (extra.replace()) {
                    finalResult = extra.stack().copy();
                } else {
                    if (ItemStack.areItemsAndComponentsEqual(finalResult, extra.stack())) {
                        finalResult.increment(extra.stack().getCount());
                    } else {
                        return extra.stack().copy();
                    }
                }
            }
        }
        return finalResult;
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
        return ModRecipes.SIEVE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SIEVE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<SieveRecipe> {
        public static final MapCodec<SieveRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(SieveRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(SieveRecipe::output),
                ExtraOutput.CODEC.listOf().optionalFieldOf("extra_results", new ArrayList<>()).forGetter(SieveRecipe::extraOutputs)
        ).apply(inst, SieveRecipe::new));

        public static final PacketCodec<RegistryByteBuf, SieveRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        PacketCodecs.codec(CODEC.codec()).cast(),
                        recipe -> recipe,
                        recipe -> recipe
                );

        @Override
        public MapCodec<SieveRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SieveRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
