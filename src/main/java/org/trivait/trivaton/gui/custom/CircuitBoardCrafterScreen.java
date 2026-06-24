package org.trivait.trivaton.gui.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ScrollableTextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.trivait.trivaton.Trivaton;

public class CircuitBoardCrafterScreen extends HandledScreen<CircuitBoardCrafterScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/circuit_board_crafter/circuit_board_crafter_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/arrow_progress.png");
    private static final Identifier CIRCUIT_BOARD_TEXTURE =
            Identifier.of(Trivaton.MOD_ID, "textures/gui/circuit_board_overlay.png");

    public CircuitBoardCrafterScreen(CircuitBoardCrafterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        context.drawTexture(CIRCUIT_BOARD_TEXTURE, x+15, y+34, 0, 0, 16, 16, 16, 16);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(ARROW_TEXTURE, x + 92, y + 35, 0, 0,
                    handler.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}