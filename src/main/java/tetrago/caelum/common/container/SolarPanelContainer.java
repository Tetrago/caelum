package tetrago.caelum.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.capability.GeneratorStorage;

public class SolarPanelContainer extends InventoryContainer
{
    public SolarPanelContainer(int windowId, BlockPos pos, Inventory inv, Player player)
    {
        super(Caelum.SOLAR_PANEL_CONTAINER.get(), windowId, pos, inv);

        addInventory(8, 84, 18, 18);
        addHotbar(8, 142, 18);

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
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((GeneratorStorage)cap).setEnergyStored((cap.getEnergyStored() & 0xffff0000) | v));
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
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(cap -> ((GeneratorStorage)cap).setEnergyStored((cap.getEnergyStored() & 0x0000ffff) | (v << 16)));
            }
        });
    }

    public int getEnergy()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getGenerationRate()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(cap -> ((GeneratorStorage)cap).getGenerationRate()).orElse(0);
    }
}
