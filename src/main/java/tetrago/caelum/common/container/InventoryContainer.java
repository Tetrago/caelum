package tetrago.caelum.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

public class InventoryContainer extends AbstractContainerMenu
{
    protected final BlockEntity blockEntity;
    protected final IItemHandler inventory;

    protected InventoryContainer(@Nullable MenuType<?> menu, int windowId, BlockPos pos, Inventory inv)
    {
        super(menu, windowId);

        blockEntity = inv.player.getCommandSenderWorld().getBlockEntity(pos);
        inventory = new InvWrapper(inv);
    }

    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int count, int dx)
    {
        for(int i = 0; i < count; ++i)
        {
            addSlot(new SlotItemHandler(handler, index, x, y));

            ++index;
            x += dx;
        }

        return index;
    }

    protected void addInventory(int x, int y, int dx, int dy)
    {
        int index = 9;

        for(int i = 0; i < 3; ++i)
        {
            index = addSlotRange(inventory, index, x, y, 9, dx);
            y += dy;
        }
    }

    protected void addHotbar(int x, int y, int dx)
    {
        addSlotRange(inventory, 0, x, y, 9, dx);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }
}
