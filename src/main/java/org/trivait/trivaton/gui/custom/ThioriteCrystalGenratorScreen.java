package org.trivait.trivaton.gui.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ScrollableTextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.trivait.trivaton.Trivaton;

public class ThioriteCrystalGenratorScreen extends HandledScreen<ThioriteCrystalGenratorScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/thiorite_crystal_generator/thiorite_crystal_generator_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/thiorite_crystal_generator/arrow_progress.png");
    private static final Identifier CIRCUIT_BOARD_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/circuit_board_overlay.png");

    public ThioriteCrystalGenratorScreen(ThioriteCrystalGenratorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752, false);

        int maxTitleWidth = 60;
        int textWidth = this.textRenderer.getWidth(this.title);

        if (textWidth <= maxTitleWidth) {
            context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
        } else {
            int gap = 15;
            int totalWidth = textWidth + gap;
            long time = Util.getMeasuringTimeMs() / 30;
            int scroll = (int) (time % totalWidth);

            int globalX = (this.width - this.backgroundWidth) / 2;
            int globalY = (this.height - this.backgroundHeight) / 2;

            int startX = globalX + this.titleX;
            int startY = globalY + this.titleY;

            context.enableScissor(startX, startY, startX + maxTitleWidth, startY + this.textRenderer.fontHeight);
            context.drawText(this.textRenderer, this.title, this.titleX - scroll, this.titleY, 4210752, false);
            context.drawText(this.textRenderer, this.title, this.titleX - scroll + totalWidth, this.titleY, 4210752, false);
            context.disableScissor();
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        context.drawTexture(CIRCUIT_BOARD_TEXTURE, x+61, y+61, 0, 0, 16, 16, 16, 16);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            int progressHeight = handler.getScaledArrowProgress();
            int maxTextureHeight = 24;
            int textureWidth = 16;

            context.drawTexture(
                    ARROW_TEXTURE,
                    x + 80,
                    y + 33 + (maxTextureHeight - progressHeight),
                    0,
                    (float) (maxTextureHeight - progressHeight),
                    textureWidth,
                    progressHeight,
                    textureWidth,
                    maxTextureHeight
            );
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}