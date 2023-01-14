package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import tetrago.caelum.client.screen.slot.OutputSlot;
import tetrago.caelum.common.capability.ModEnergyStorage;
import tetrago.caelum.common.container.data.EnergyData;

public class ArcFurnaceControllerContainer extends ModBaseContainer
{
    private final ContainerData data;

    public ArcFurnaceControllerContainer(int windowId, Inventory inv, FriendlyByteBuf data)
    {
        this(windowId, inv, inv.player.level.getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public ArcFurnaceControllerContainer(int windowId, Inventory inv, BlockEntity blockEntity, ContainerData data)
    {
        super(ModContainers.ARC_FURNACE_CONTROLLER.get(), windowId, blockEntity.getBlockPos(), inv);

        this.data = data;

        checkContainerSize(inv, 3);

        addPlayerInventory();
        addPlayerHotbar();

        blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(cap -> {
            addSlot(new SlotItemHandler(cap, 0, 44, 35));
            addSlot(new OutputSlot(cap, 1, 104, 35));
            addSlot(new OutputSlot(cap, 2, 131, 35));
        });

        addDataSlots(new EnergyData()
        {
            @Override
            protected int getEnergyStored()
            {
                return ArcFurnaceControllerContainer.this.getEnergyStored();
            }

            @Override
            protected void setEnergyStored(int energy)
            {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((ModEnergyStorage)cap).setEnergyStored(energy));
            }
        });

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex)
    {
        return quickMoveStack(pPlayer, pIndex, 3);
    }

    public boolean isCrafting()
    {
        return data.get(0) > 0;
    }

    public int getScaledProgress()
    {
        final int ARROW_SIZE = 22;

        int progress = data.get(0);
        int maxProgress = data.get(1);

        return maxProgress != 0 ? progress * ARROW_SIZE / maxProgress : 0;
    }

    public int getEnergyStored()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getEnergyCapacity()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getMaxEnergyStored).orElse(0);
    }
}
