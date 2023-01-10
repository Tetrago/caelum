package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ArcFurnaceControllerContainer extends ModBaseContainer
{
    public ArcFurnaceControllerContainer(int windowId, Inventory inv, FriendlyByteBuf data)
    {
        this(windowId, inv, inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    public ArcFurnaceControllerContainer(int windowId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModContainers.ARC_FURNACE_CONTROLLER.get(), windowId, blockEntity.getBlockPos(), inv);

        addPlayerInventory();
        addPlayerHotbar();
    }
}
