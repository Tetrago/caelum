package tetrago.caelum.common.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class ProxyItemHandler implements IItemHandler
{
    private final IItemHandler handler;
    private final int index;
    private final boolean isOutput;

    public ProxyItemHandler(IItemHandler handler, int index, boolean isOutput)
    {
        this.handler = handler;
        this.index = index;
        this.isOutput = isOutput;
    }

    @Override
    public int getSlots()
    {
        return 1;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return handler.getStackInSlot(index);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        if(isOutput) return stack;
        return handler.insertItem(index, stack, simulate);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return handler.extractItem(index, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return handler.getSlotLimit(index);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack)
    {
        return handler.isItemValid(index, stack);
    }
}
