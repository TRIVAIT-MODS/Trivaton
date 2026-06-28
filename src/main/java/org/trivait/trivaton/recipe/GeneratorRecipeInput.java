package org.trivait.trivaton.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import java.util.List;

public record GeneratorRecipeInput(ItemStack centerStack, List<ItemStack> sideStacks) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == 0) return centerStack;
        int sideIndex = slot - 1;
        if (sideIndex >= 0 && sideIndex < sideStacks.size()) {
            return sideStacks.get(sideIndex);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSize() {
        return 1 + sideStacks.size();
    }

    public ItemStack getCenterStack() {
        return centerStack;
    }

    public List<ItemStack> getSideStacks() {
        return sideStacks;
    }
}
