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

    private static final int TEXTURE_WIDTH = 190;
    private static final int TEXTURE_HEIGHT = 200;

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Actinium.MOD_ID, "textures/gui/pumpjack_gui.png");

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
        pGuiGraphics.blit(TEXTURE, x, y, imageWidth, imageHeight,
                0.0f, 0.0f, imageWidth, imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);

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

            pGuiGraphics.blit(TEXTURE, targetX, targetY, 10, filledHeight,
                    sourceX, sourceY, 10, filledHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 107;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float delta) {
        renderBackground(pGuiGraphics);

        super.render(pGuiGraphics, pMouseX, pMouseY, delta);

        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int fluidAmount = this.menu.getFluidAmount();

        String text = fluidAmount + " mB";

        int textX = this.leftPos + 123 - this.font.width(text);
        int textY = this.topPos + 60;

        pGuiGraphics.drawString(
                this.font,
                text,
                textX,
                textY,
                0x404040,
                false
        );
    }
}
