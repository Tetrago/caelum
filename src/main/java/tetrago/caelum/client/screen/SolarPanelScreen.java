package tetrago.caelum.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.SolarPanelContainer;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelContainer>
{
    public static final String UNLOCALIZED_NAME = "screen.caelum.solar_panel";

    private final ResourceLocation TEXTURE = new ResourceLocation(Caelum.MODID, "textures/gui/solar_panel.png");

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
        drawString(matrixStack, Minecraft.getInstance().font, "Energy: " + menu.getEnergy() + " RF", 10, 30, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, menu.getGenerationRate() + " RF/t", 20, 40, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
    }
}
