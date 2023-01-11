package tetrago.caelum.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.CoilBlock;

import java.util.List;

public class CoilBlockItem extends BlockItem
{
    public CoilBlockItem(CoilBlock pBlock, Properties pProperties)
    {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag)
    {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        pTooltip.add(new TranslatableComponent(Caelum.modid("tooltip.{}.block.coil")).append(": " + ((CoilBlock)getBlock()).getCoilStrength()).withStyle(ChatFormatting.GRAY));
    }
}
