package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.client.screen.SolarPanelScreen;
import tetrago.caelum.common.block.SolarPanelBlock;
import tetrago.caelum.common.capability.GeneratorEnergyStorage;
import tetrago.caelum.common.container.SolarPanelContainer;

public class SolarPanelBlockEntity extends BlockEntity implements MenuProvider
{
    private final GeneratorEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyStorageCapability;

    public SolarPanelBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SOLAR_PANEL.get(), pos, state);

        final SolarPanelBlock block = (SolarPanelBlock)getBlockState().getBlock();
        energyStorage = new GeneratorEnergyStorage(block.getEnergyBufferCapacity(), block.getEnergyGenerationRate())
        {
            @Override
            protected void onEnergyChanged()
            {
                setChanged();
            }
        };

        energyStorageCapability = LazyOptional.of(() -> energyStorage);
    }

    public boolean isGenerating()
    {
        return level.canSeeSky(getBlockPos().above()) && level.isDay();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SolarPanelBlockEntity blockEntity)
    {
        if(level.isClientSide()) return;

        if(blockEntity.isGenerating())
        {
            blockEntity.energyStorage.generate();
        }

        BlockEntity be = level.getBlockEntity(blockEntity.worldPosition.below());
        if(be == null) return;

        be.getCapability(CapabilityEnergy.ENERGY, Direction.UP).ifPresent(cap -> {
            if(!cap.canReceive()) return;

            int max = blockEntity.energyStorage.extractEnergy(blockEntity.energyStorage.getEnergyStored(), true); // Determine how much can be withdrawn
            int out = cap.receiveEnergy(max, false); // Send as much as possible
            blockEntity.energyStorage.extractEnergy(out, false); // Remove what was extracted
        });
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        energyStorage.deserializeNBT(tag.get("energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        tag.put("energy", energyStorage.serializeNBT());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == CapabilityEnergy.ENERGY && (side == Direction.DOWN || side == null))
        {
            return energyStorageCapability.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        energyStorageCapability.invalidate();
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent(SolarPanelScreen.UNLOCALIZED_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
    {
        return new SolarPanelContainer(windowId, inv, this);
    }
}
