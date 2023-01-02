package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.GeneratorStorage;
import tetrago.caelum.common.block.SolarPanelBlock;

public class SolarPanelBlockEntity extends BlockEntity
{
    private final GeneratorStorage generatorStorage;
    private final LazyOptional<IEnergyStorage> energyStorage;

    public SolarPanelBlockEntity(BlockPos pos, BlockState state)
    {
        super(Caelum.SOLAR_PANEL_BLOCK_ENTITY.get(), pos, state);

        final SolarPanelBlock block = (SolarPanelBlock)getBlockState().getBlock();
        generatorStorage = new GeneratorStorage(block.getEnergyBufferCapacity(), block.getEnergyGenerationRate());
        energyStorage = LazyOptional.of(() -> generatorStorage);
    }

    public void tick()
    {
        if(level.isClientSide()) return;

        generatorStorage.generateEnergy();
        setChanged();

        for(Direction direction : Direction.values())
        {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
            if(be == null) continue;

            boolean extracted = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(cap -> {
                if(!cap.canReceive()) return false;

                int max = generatorStorage.extractEnergy(generatorStorage.getEnergyStored(), true); // Determine how much can be withdrawn
                int out = cap.receiveEnergy(max, false); // Send as much as possible
                generatorStorage.extractEnergy(out, false); // Remove what was extracted

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
            generatorStorage.deserializeNBT(tag.get("energy"));
        }

        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        tag.put("energy", generatorStorage.serializeNBT());

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
        energyStorage.cast();
    }
}
