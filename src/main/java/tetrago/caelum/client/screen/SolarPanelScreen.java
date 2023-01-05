package tetrago.caelum.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.SolarPanelContainer;

public class SolarPanelScreen extends AbstractContainerScreen<SolarPanelContainer>
{
    public static final String UNLOCALIZED_NAME = "screen.caelum.solar_panel";

    private static final ResourceLocation TEXTURE = new ResourceLocation(Caelum.MODID, "textures/gui/solar_panel.png");

    private final Player player;

    public SolarPanelScreen(SolarPanelContainer container, Inventory inv, Component name)
    {
        super(container, inv, name);

        player = inv.player;
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
        drawString(matrixStack, Minecraft.getInstance().font, (menu.isGenerating() ? menu.getGenerationRate() : 0) + " FE/t", 80, 35, 0xffffff);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int mouseX, int mouseY)
    {
        super.renderTooltip(matrixStack, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if(mouseX >= x + 8 && mouseX < x + 8 + 15 && mouseY >= y + 8 && mouseY < y + 8 + 70)
        {
            renderTooltip(matrixStack, new TextComponent(menu.getEnergyStored() + "/" + menu.getEnergyCapacity() + " FE"), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);

        float progress = (float)menu.getEnergyStored() / menu.getEnergyCapacity();
        blit(matrixStack, x + 8, y + 8 + (int)((1 - progress) * 70), 176, 0, 15, (int)(progress * 70));

        blit(matrixStack, x + 43, y + 25, 191, menu.isGenerating() ? 0 : 32, 32, 32);
    }
}
