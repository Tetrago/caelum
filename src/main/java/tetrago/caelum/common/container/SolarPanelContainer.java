package tetrago.caelum.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import tetrago.caelum.common.block.SolarPanelBlock;
import tetrago.caelum.common.blockentity.SolarPanelBlockEntity;
import tetrago.caelum.common.capability.GeneratorEnergyStorage;
import tetrago.caelum.common.capability.ModEnergyStorage;
import tetrago.caelum.common.container.data.EnergyData;

public class SolarPanelContainer extends ModBaseContainer
{
    private boolean isGenerating = false;

    public SolarPanelContainer(int windowId, Inventory inv, FriendlyByteBuf data)
    {
        this(windowId, inv, inv.player.level.getBlockEntity(data.readBlockPos()));
    }

    public SolarPanelContainer(int windowId, Inventory inv, BlockEntity blockEntity)
    {
        super(ModContainers.SOLAR_PANEL.get(), windowId, blockEntity.getBlockPos(), inv);

        addPlayerInventory();
        addPlayerHotbar();

        addEnergyData();
    }

    private void addEnergyData()
    {
        addDataSlots(new EnergyData()
        {
            @Override
            protected int getEnergyStored()
            {
                return SolarPanelContainer.this.getEnergyStored();
            }

            @Override
            protected void setEnergyStored(int energy)
            {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((ModEnergyStorage)cap).setEnergyStored(energy));
            }
        });

        addDataSlot(new DataSlot()
        {
            @Override
            public int get()
            {
                return ((SolarPanelBlockEntity)blockEntity).isGenerating() ? 1 : 0;
            }

            @Override
            public void set(int v)
            {
                isGenerating = v == 1;
            }
        });
    }

    public int getEnergyStored()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getEnergyCapacity()
    {
        return ((SolarPanelBlock)blockEntity.getBlockState().getBlock()).getEnergyBufferCapacity();
    }

    public int getGenerationRate()
    {
        return ((SolarPanelBlock)blockEntity.getBlockState().getBlock()).getEnergyGenerationRate();
    }

    public boolean isGenerating()
    {
        return isGenerating;
    }
}
