package org.trivait.trivaton.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.recipe.ExtraOutput;
import org.trivait.trivaton.recipe.SieveRecipe;

public class SieveRecipeCategory implements IRecipeCategory<SieveRecipe> {

    private static final Identifier SLOT_TEXTURE =
            Trivaton.id("textures/gui/sieve/jei_slot.png");
    private static final Identifier ARROW_TEXTURE =
            Trivaton.id("textures/gui/sieve/jei_arrow.png");
    private static final Identifier ARROW_FULL_TEXTURE =
            Trivaton.id("textures/gui/arrow_progress.png");

    private final IDrawable background;

    public static final RecipeType<SieveRecipe> TYPE =
            RecipeType.create(Trivaton.MOD_ID, "sieve", SieveRecipe.class);

    private final IDrawable icon;

    public SieveRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 70);

        this.icon = guiHelper.createDrawableItemStack(
                new ItemStack(ModBlocks.SIEVE)
        );
    }

    private int getOutputCount(SieveRecipe recipe) {
        int count = recipe.extraOutputs().size();

        if (!recipe.output().isEmpty()) {
            count++;
        }

        return count;
    }

    @Override
    public RecipeType<SieveRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.trivaton.sieve");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SieveRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(
                RecipeIngredientRole.INPUT,
                30-20,
                20
        ).addIngredients(recipe.inputItem());

        int outputCount = getOutputCount(recipe);

        int slotSize = 18;
        int spacing = 2;

        int totalWidth =
                outputCount * slotSize +
                        (outputCount - 1) * spacing;

        int startX = 120 - totalWidth / 2;

        int index = 0;

        if (!recipe.output().isEmpty()) {

            builder.addSlot(
                    RecipeIngredientRole.OUTPUT,
                    startX,
                    35-9
            ).addItemStack(recipe.output());

            index++;
        }

        for (ExtraOutput extra : recipe.extraOutputs()) {

            builder.addSlot(
                    RecipeIngredientRole.OUTPUT,
                    startX + index * (slotSize + spacing),
                    35-9
            ).addItemStack(extra.stack());

            index++;
        }
    }

    @Override
    public void draw(SieveRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext context, double mouseX, double mouseY) {
        renderArrow(context, recipe);

        renderSieveBlock(context);

        renderOutputSlots(context, recipe);

        renderChances(context, recipe);
    }
    private void renderSieveBlock(DrawContext context) {
        MatrixStack matrices = context.getMatrices();

        matrices.push();

        matrices.translate(2.5, 23, -200);

        matrices.scale(2, 2, 2);

        context.drawItem(
                new ItemStack(ModBlocks.SIEVE),
                0,
                0
        );
        matrices.pop();
    }
    private void renderOutputSlots(DrawContext context, SieveRecipe recipe) {

        int outputCount = getOutputCount(recipe);

        int slotSize = 18;
        int spacing = 2;

        int totalWidth =
                outputCount * slotSize +
                        (outputCount - 1) * spacing;

        int startX = 120 - totalWidth / 2;

        for (int i = 0; i < outputCount; i++) {

            context.drawTexture(
                    SLOT_TEXTURE,
                    startX + i * (slotSize + spacing) - 1,
                    34-9,
                    0,
                    0,
                    18,
                    18,
                    18,
                    18
            );
        }
    }
    private void renderArrow(DrawContext context, SieveRecipe recipe) {
        int outputCount = getOutputCount(recipe);

        int slotSize = 18;
        int spacing = 2;

        int totalWidth =
                outputCount * slotSize +
                        (outputCount - 1) * spacing;

        int startX = 120 - totalWidth / 2;

        int inputX = 10;
        int inputRight = inputX + 18;

        int gap = startX - inputRight;

        int arrowX = inputRight + (gap - 24) / 2;
        int arrowY = 27;

        int progress = Math.toIntExact((MinecraftClient.getInstance().world.getTime() % 24));

        context.drawTexture(ARROW_TEXTURE, arrowX, arrowY, 0, 0, 24, 16, 24, 16);

        context.drawTexture(ARROW_FULL_TEXTURE, arrowX, arrowY, 0, 0, progress, 16, 24, 16);
    }
    private void renderChances(DrawContext context, SieveRecipe recipe) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int outputCount = getOutputCount(recipe);
        int slotSize = 18;
        int spacing = 2;
        int totalWidth = outputCount * slotSize + (outputCount - 1) * spacing;
        int startX = 120 - totalWidth / 2;
        int slotIndex = recipe.output().isEmpty() ? 0 : 1;

        for (ExtraOutput extra : recipe.extraOutputs()) {
            float percent = extra.chance() * 100f;

            if (percent >= 100f) {
                slotIndex++;
                continue;
            }

            String text = String.format(java.util.Locale.US, "%.1f%%", percent);
            if (text.endsWith(".0%")) {
                text = text.replace(".0%", "%");
            }

            int textWidth = textRenderer.getWidth(text);
            int x = startX + slotIndex * (slotSize + spacing) + 8 - textWidth / 2;

            context.drawText(textRenderer, text, x, 13, 0xFFFFFF, true);
            slotIndex++;
        }
    }
}