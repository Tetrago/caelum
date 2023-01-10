package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlastFurnaceMultiblockContainer extends ModBaseContainer
{
    public BlastFurnaceMultiblockContainer(int windowId, Inventory inv, FriendlyByteBuf data)
    {
        this(windowId, inv, inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    public BlastFurnaceMultiblockContainer(int windowId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModContainers.BLAST_FURNACE_MULTIBLOCK_CONTAINER.get(), windowId, blockEntity.getBlockPos(), inv);

        addPlayerInventory();
        addPlayerHotbar();
    }
}
