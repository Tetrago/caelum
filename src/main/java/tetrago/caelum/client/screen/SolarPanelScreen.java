package tetrago.caelum.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tetrago.caelum.client.screen.widget.EnergyWidget;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.SolarPanelContainer;

import java.util.Optional;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelContainer>
{
    public static final String UNLOCALIZED_NAME = "screen.caelum.solar_panel";

    private static final ResourceLocation TEXTURE = new ResourceLocation(Caelum.MODID, "textures/gui/solar_panel.png");

    private EnergyWidget energyWidget;

    public SolarPanelScreen(SolarPanelContainer container, Inventory inv, Component name)
    {
        super(container, inv, name);
    }

    @Override
    protected void init()
    {
        super.init();

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyWidget = new EnergyWidget(x + 8, y + 21, 16, 44)
        {
            @Override
            protected int getEnergyStored()
            {
                return menu.getEnergyStored();
            }

            @Override
            protected int getEnergyCapacity()
            {
                return menu.getEnergyCapacity();
            }
        };
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
        super.renderLabels(matrixStack, mouseX, mouseY);

        drawString(matrixStack, Minecraft.getInstance().font, (menu.isGenerating() ? menu.getGenerationRate() : 0) + " FE/t", 85, 40, 0xffffff);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int mouseX, int mouseY)
    {
        super.renderTooltip(matrixStack, mouseX, mouseY);

        if(energyWidget.contains(mouseX, mouseY))
        {
            renderTooltip(matrixStack, energyWidget.getTooltips(), Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);

        energyWidget.draw(matrixStack, partialTicks, mouseX, mouseY);

        blit(matrixStack, x + 42, y + 27, 176, menu.isGenerating() ? 0 : 32, 32, 32);
    }
}
