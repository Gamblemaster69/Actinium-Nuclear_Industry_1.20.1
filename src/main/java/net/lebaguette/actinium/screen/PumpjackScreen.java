package net.lebaguette.actinium.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lebaguette.actinium.Actinium;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PumpjackScreen extends AbstractContainerScreen<PumpjackMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Actinium.MOD_ID, "textures/gui/pumpjack.png");

    public PumpjackScreen(PumpjackMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 199;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        //BACKGROUND
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        //TANK
        int fluidAmound = this.menu.getFluidAmount();
        int tankCapacity = this.menu.getTankCapacity();

        int tankHeight = 54;

        int filledHeight = (int) ((float) fluidAmound / tankCapacity * tankHeight);

        if (filledHeight > 0) {
            int sourceX = 178;
            int sourceY = 22 + (tankHeight - filledHeight);

            int targetX = x + 136;
            int targetY = y + 22 + (tankHeight - filledHeight);

            pGuiGraphics.blit(
                    TEXTURE,
                    targetX, targetY,
                    sourceX, sourceY,
                    10,
                    filledHeight);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float delta) {
        renderBackground(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, delta);

        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int fluidAmount = this.menu.getFluidAmount();

        String text = fluidAmount + " mB";

        int textX = this.leftPos + 77 - this.font.width(text);
        int textY = this.topPos + 60;

        pGuiGraphics.drawString(
                this.font,
                text,
                textX,
                textY,
                0xFFFFFF,
                true
        );
    }
}
