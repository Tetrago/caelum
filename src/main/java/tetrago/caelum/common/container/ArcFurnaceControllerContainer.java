package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import tetrago.caelum.common.capability.ModEnergyStorage;
import tetrago.caelum.common.container.data.EnergyData;

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
    }

    public int getEnergyStored()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.getEnergyStored()).orElse(0);
    }

    public int getEnergyCapacity()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(cap -> cap.getMaxEnergyStored()).orElse(0);
    }
}
