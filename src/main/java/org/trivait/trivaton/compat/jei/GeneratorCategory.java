package org.trivait.trivaton.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.recipe.GeneratorRecipe;

public class GeneratorCategory implements IRecipeCategory<GeneratorRecipe> {

    private static final Identifier SLOT_TEXTURE = Trivaton.id("textures/gui/generator/jei_slot.png");
    private static final Identifier ARROW_TEXTURE = Trivaton.id("textures/gui/generator/jei_arrow.png");
    private static final Identifier ARROW_FULL_TEXTURE = Trivaton.id("textures/gui/arrow_progress.png");

    private final IDrawable background;

    public static final RecipeType<GeneratorRecipe> TYPE =
            RecipeType.create(Trivaton.MOD_ID, "generator", GeneratorRecipe.class);

    private final IDrawable icon;

    public GeneratorCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(180, 90);
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.GENERATOR));
    }

    @Override
    public RecipeType<GeneratorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.trivaton.generator");
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
    public void setRecipe(IRecipeLayoutBuilder builder, GeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 34).addIngredients(recipe.centerInput());

        int[][] sidePositions = {
                {44, 15},
                {63, 12},
                {82, 15},
                {85, 34},
                {82, 53},
                {63, 56},
                {44, 53},
                {41, 34}
        };

        for (int i = 0; i < recipe.sideInputs().size() && i < 8; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, sidePositions[i][0], sidePositions[i][1]).addIngredients(recipe.sideInputs().get(i));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 137, 34).addItemStack(recipe.output());
    }

    @Override
    public void draw(GeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext context, double mouseX, double mouseY) {
        renderGenerator(context);
        renderSlots(context);
        renderArrow(context);
    }

    private void renderGenerator(DrawContext context) {
        MatrixStack matrices = context.getMatrices();

        matrices.push();
        matrices.translate(5, 25, -200);
        matrices.scale(2F, 2F, 2F);

        context.drawItem(new ItemStack(ModBlocks.GENERATOR), 0, 0);

        matrices.pop();
    }

    private void renderSlots(DrawContext context) {
        int[][] slots = {
                {63, 34},
                {44, 15},
                {63, 12},
                {82, 15},
                {85, 34},
                {82, 53},
                {63, 56},
                {44, 53},
                {41, 34},
                {137, 34}
        };

        for (int[] slot : slots) {
            context.drawTexture(SLOT_TEXTURE, slot[0] - 1, slot[1] - 1, 0, 0, 18, 18, 18, 18);
        }
    }

    private void renderArrow(DrawContext context) {
        int progress = (int)(MinecraftClient.getInstance().world.getTime() % 24);

        context.drawTexture(ARROW_TEXTURE, 106, 35, 0, 0, 24, 16, 24, 16);
        context.drawTexture(ARROW_FULL_TEXTURE, 106, 35, 0, 0, progress, 16, 24, 16);
    }
}