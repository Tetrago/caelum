package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import tetrago.caelum.common.block.SolarPanelBlock;
import tetrago.caelum.common.capability.GeneratorEnergyStorage;

public class SolarPanelContainer extends ModBaseContainer
{
    public SolarPanelContainer(int windowId, Inventory inv, FriendlyByteBuf data)
    {
        this(windowId, inv, inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    public SolarPanelContainer(int windowId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModContainers.SOLAR_PANEL_CONTAINER.get(), windowId, blockEntity.getBlockPos(), inv);

        addPlayerInventory();
        addPlayerHotbar();

        track();
    }

    private void track()
    {
        addDataSlot(new DataSlot()
        {
            @Override
            public int get()
            {
                return getEnergy() & 0x0000ffff;
            }

            @Override
            public void set(int v)
            {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((GeneratorEnergyStorage)cap).setEnergyStored((cap.getEnergyStored() & 0xffff0000) | v));
            }
        });

        addDataSlot(new DataSlot()
        {
            @Override
            public int get()
            {
                return (getEnergy() >> 16) & 0x0000ffff;
            }

            @Override
            public void set(int v)
            {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((GeneratorEnergyStorage)cap).setEnergyStored((cap.getEnergyStored() & 0x0000ffff) | (v << 16)));
            }
        });
    }

    public int getEnergy()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getGenerationRate()
    {
        return ((SolarPanelBlock)blockEntity.getBlockState().getBlock()).getEnergyGenerationRate();
    }
}
