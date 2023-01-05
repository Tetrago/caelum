package tetrago.caelum.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class Widget extends GuiComponent
{
    protected final Rect2i rect;

    public Widget(Rect2i rect)
    {
        this.rect = rect;
    }

    public boolean contains(int x, int y)
    {
        return rect.contains(x, y);
    }

    public abstract List<Component> getTooltips();
    public abstract void draw(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY);
}
