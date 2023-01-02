package tetrago.caelum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.blockentity.SolarPanelContainer;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelContainer>
{
    private final ResourceLocation GUI = new ResourceLocation(Caelum.MODID, "textures/gui/solar_panel.png");

    public SolarPanelScreen(SolarPanelContainer container, Inventory inv, Component name)
    {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY)
    {
        drawString(matrixStack, Minecraft.getInstance().font, "Energy: " + menu.getEnergy() + " RF", 10, 10, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, "        " + menu.getGenerationRate() + " RF/t", 10, 20, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (width - imageWidth) / 2;
        int relY = (height - imageHeight) / 2;
        blit(matrixStack, relX, relY, 0, 0, imageWidth, imageHeight);
    }
}
