package org.trivait.trivaton.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.block.custom.ThioriteCrystalGeneratorBlock;
import org.trivait.trivaton.recipe.ThioriteCrystalGeneratorRecipe;

public class ThioriteCrystalGeneratorCategory
        implements IRecipeCategory<ThioriteCrystalGeneratorRecipe> {

    public static final RecipeType<ThioriteCrystalGeneratorRecipe> TYPE =
            RecipeType.create(
                    Trivaton.MOD_ID,
                    "thiorite_crystal_generator",
                    ThioriteCrystalGeneratorRecipe.class
            );

    private static final Identifier SLOT_TEXTURE =
            Trivaton.id("textures/gui/thiorite_crystal_generator/jei_slot.png");

    private static final Identifier ARROW_TEXTURE =
            Trivaton.id("textures/gui/thiorite_crystal_generator/jei_arrow.png");

    private static final Identifier FULL_ARROW_TEXTURE =
            Trivaton.id("textures/gui/arrow_progress.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ThioriteCrystalGeneratorCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 70);
        this.icon = guiHelper.createDrawableItemStack(
                new ItemStack(ModBlocks.THIORITE_CRYSTAL_GENERATOR)
        );
    }

    @Override
    public RecipeType<ThioriteCrystalGeneratorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.trivaton.thiorite_crystal_generator");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ThioriteCrystalGeneratorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(
                RecipeIngredientRole.OUTPUT,
                120,
                26
        ).addItemStack(recipe.output());
    }

    private int getProgress() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return 0;
        return (int)(client.world.getTime() % 25);
    }

    private int getStage() {
        return (getProgress() * 5) / 25;
    }

    @Override
    public void draw(ThioriteCrystalGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext context, double mouseX, double mouseY) {
        renderGenerator(context);
        renderArrow(context);
        renderOutputSlot(context);
    }

    private void renderGenerator(DrawContext context) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        int stage = getStage();

        BlockState state = ModBlocks.THIORITE_CRYSTAL_GENERATOR
                .getDefaultState()
                .with(
                        ThioriteCrystalGeneratorBlock.CRYSTAL_STAGE,
                        stage
                );

        MatrixStack matrices = context.getMatrices();

        matrices.push();

        matrices.translate(50, 47, 50);
        matrices.scale(30f, -30f, 30f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(225));

        client.getBlockRenderManager().renderBlockAsEntity(state, matrices, context.getVertexConsumers(), 0xF000F0, OverlayTexture.DEFAULT_UV);

        context.draw();
        matrices.pop();
    }

    private void renderOutputSlot(DrawContext context) {
        context.drawTexture(SLOT_TEXTURE, 119, 25, 0, 0, 18, 18, 18, 18);
    }

    private void renderArrow(DrawContext context) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        int progress = getProgress();
        int arrowProgress = (progress * 24) / 25;

        int arrowX = 80 - 12;
        int arrowY = 27;

        context.drawTexture(ARROW_TEXTURE, arrowX, arrowY, 0, 0, 24, 16, 24, 16);

        context.drawTexture(FULL_ARROW_TEXTURE, arrowX, arrowY, 0, 0, arrowProgress, 16, 24, 16);
    }
}