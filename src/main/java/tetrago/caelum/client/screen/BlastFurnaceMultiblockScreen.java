package tetrago.caelum.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.BlastFurnaceMultiblockContainer;

public class BlastFurnaceMultiblockScreen extends AbstractContainerScreen<BlastFurnaceMultiblockContainer>
{
    public static final String UNLOCALIZED_NAME = Caelum.modid("screen.{}.blast_furnace_multiblock");

    private static final ResourceLocation TEXTURE = Caelum.loc("textures/gui/blast_furnace_multiblock.png");

    public BlastFurnaceMultiblockScreen(BlastFurnaceMultiblockContainer pMenu, Inventory pPlayerInventory, Component pTitle)
    {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY)
    {
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
    }
}
