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
    private final GeneratorEnergyStorage generatorEnergyStorage;
    private final LazyOptional<IEnergyStorage> energyStorage;

    public SolarPanelBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SOLAR_PANEL_BLOCK_ENTITY.get(), pos, state);

        final SolarPanelBlock block = (SolarPanelBlock)getBlockState().getBlock();
        generatorEnergyStorage = new GeneratorEnergyStorage(block.getEnergyBufferCapacity(), block.getEnergyGenerationRate())
        {
            @Override
            protected void onEnergyChanged()
            {
                setChanged();
            }
        };

        energyStorage = LazyOptional.of(() -> generatorEnergyStorage);
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
            blockEntity.generatorEnergyStorage.generate();
        }

        for(Direction direction : Direction.values())
        {
            BlockEntity be = level.getBlockEntity(blockEntity.worldPosition.relative(direction));
            if(be == null) continue;

            boolean extracted = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(cap -> {
                if(!cap.canReceive()) return false;

                int max = blockEntity.generatorEnergyStorage.extractEnergy(blockEntity.generatorEnergyStorage.getEnergyStored(), true); // Determine how much can be withdrawn
                int out = cap.receiveEnergy(max, false); // Send as much as possible
                blockEntity.generatorEnergyStorage.extractEnergy(out, false); // Remove what was extracted

                return true;
            }).orElse(false);

            if(extracted) break;
        }
    }

    @Override
    public void load(CompoundTag tag)
    {
        if(tag.contains("energy"))
        {
            generatorEnergyStorage.deserializeNBT(tag.get("energy"));
        }

        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        tag.put("energy", generatorEnergyStorage.serializeNBT());

        super.saveAdditional(tag);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(side != Direction.UP && cap == CapabilityEnergy.ENERGY)
        {
            return energyStorage.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        energyStorage.invalidate();
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
