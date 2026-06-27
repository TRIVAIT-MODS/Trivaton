package org.trivait.trivaton.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.recipe.CircuitBoardCrafterRecipe;

public class CircuitBoardCrafterCategory implements IRecipeCategory<CircuitBoardCrafterRecipe> {

    public static final RecipeType<CircuitBoardCrafterRecipe> TYPE =
            RecipeType.create(Trivaton.MOD_ID, "circuit_board_crafter", CircuitBoardCrafterRecipe.class);

    private static final Identifier SLOT_TEXTURE =
            Trivaton.id("textures/gui/circuit_board_crafter/jei_slot.png");

    private static final Identifier ARROW_TEXTURE =
            Trivaton.id("textures/gui/circuit_board_crafter/jei_arrow.png");

    private static final Identifier FULL_ARROW_TEXTURE =
            Trivaton.id("textures/gui/arrow_progress.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CircuitBoardCrafterCategory(IGuiHelper guiHelper) {

        this.background = guiHelper.createBlankDrawable(150, 70);

        this.icon = guiHelper.createDrawableItemStack(
                new ItemStack(ModBlocks.CIRCUIT_BOARD_CRAFTER)
        );
    }

    @Override
    public RecipeType<CircuitBoardCrafterRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.trivaton.circuit_board_crafter");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CircuitBoardCrafterRecipe recipe, IFocusGroup focuses) {
        int startX = 10;
        int startY = 8;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                int index = row * 3 + col;

                if (index >= recipe.ingredients().size()) {
                    continue;
                }

                if (recipe.ingredients().get(index).isEmpty()) {
                    continue;
                }

                builder.addSlot(
                        RecipeIngredientRole.INPUT,
                        startX + col * 18,
                        startY + row * 18
                ).addIngredients(
                        recipe.ingredients().get(index)
                );
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 26).addItemStack(recipe.output());
    }

    @Override
    public void draw(CircuitBoardCrafterRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext context, double mouseX, double mouseY) {
        renderInputSlotBackgrounds(context);

        renderOutputSlotBackground(context);

        renderArrow(context);
    }

    private void renderInputSlotBackgrounds(DrawContext context) {
        int startX = 10;
        int startY = 8;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                context.drawTexture(SLOT_TEXTURE, startX + col * 18 - 1, startY + row * 18 - 1, 0, 0, 18, 18, 18, 18);
            }
        }
    }

    private void renderOutputSlotBackground(DrawContext context) {
        context.drawTexture(SLOT_TEXTURE, 119, 25, 0, 0, 18, 18, 18, 18);
    }

    private void renderArrow(DrawContext context) {
        if (MinecraftClient.getInstance().world == null) {
            return;
        }

        int progress = (int)(MinecraftClient.getInstance().world.getTime() % 25);

        int arrowX = 78;
        int arrowY = 27;

        context.drawTexture(ARROW_TEXTURE, arrowX, arrowY, 0, 0, 24, 16, 24, 16);

        context.drawTexture(FULL_ARROW_TEXTURE, arrowX, arrowY, 0, 0, progress, 16, 24, 16);
    }
}