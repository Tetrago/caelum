package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.GeneratorStorage;

public class SolarPanelContainer extends AbstractContainerMenu
{
    private final BlockEntity blockEntity;
    private IItemHandler inventory;

    public SolarPanelContainer(int windowId, BlockPos pos, Inventory inv, Player player)
    {
        super(Caelum.SOLAR_PANEL_CONTAINER.get(), windowId);

        blockEntity = inv.player.getCommandSenderWorld().getBlockEntity(pos);
        inventory = new InvWrapper(inv);

        layoutInventory();
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

    private void addSlotRange(IItemHandler handler, int index, int x, int y)
    {
        for(int i = 0; i < 9; ++i)
        {
            addSlot(new SlotItemHandler(handler, index, x, y));

            ++index;
            x += 18;
        }
    }

    private void layoutInventory()
    {
        addSlotRange(inventory, 0, 8, 142);

        addSlotRange(inventory, 27, 8, 120);
        addSlotRange(inventory, 18, 8, 102);
        addSlotRange(inventory, 9, 8, 84);
    }

    public int getEnergy()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getGenerationRate()
    {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(cap -> ((GeneratorStorage)cap).getGenerationRate()).orElse(0);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }
}
