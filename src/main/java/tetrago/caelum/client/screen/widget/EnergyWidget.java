package tetrago.caelum.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public abstract class EnergyWidget extends Widget
{
    public EnergyWidget(int x, int y, int w, int h)
    {
        super(new Rect2i(x, y, w, h));
    }

    @Override
    public List<Component> getTooltips()
    {
        return List.of(new TextComponent(String.format("%.2f kFE/ %.2f kFE", getEnergyStored() / 1000.0f, getEnergyCapacity() / 1000.0f)));
    }

    @Override
    public void draw(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        fillGradient(matrixStack, rect.getX(), rect.getY() + (int)((1 - (float)getEnergyStored() / getEnergyCapacity()) * rect.getHeight()), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), 0xff9cfd56, 0xff00dd20);
    }

    protected abstract int getEnergyStored();
    protected abstract int getEnergyCapacity();
}
