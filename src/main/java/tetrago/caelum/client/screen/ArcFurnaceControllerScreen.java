package tetrago.caelum.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.ArcFurnaceControllerContainer;

public class ArcFurnaceControllerScreen extends AbstractContainerScreen<ArcFurnaceControllerContainer>
{
    public static final String UNLOCALIZED_NAME = Caelum.modid("screen.{}.arc_furnace_controller");

    private static final ResourceLocation TEXTURE = Caelum.loc("textures/gui/arc_furnace_controller.png");

    public ArcFurnaceControllerScreen(ArcFurnaceControllerContainer container, Inventory inv, Component name)
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
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(matrixStack, x, y, 0, 0, imageWidth, imageHeight);
    }
}
