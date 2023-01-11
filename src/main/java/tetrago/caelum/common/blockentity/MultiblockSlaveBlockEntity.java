package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class MultiblockSlaveBlockEntity extends BlockEntity
{
    private Optional<BlockPos> masterBlockPos = Optional.empty();

    public MultiblockSlaveBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState)
    {
        super(pType, pPos, pBlockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(masterBlockPos.isPresent() && canForwardCapability(cap))
        {
            BlockEntity be = level.getBlockEntity(masterBlockPos.get());
            if(be != null)
            {
                return be.getCapability(cap, side);
            }
        }

        return super.getCapability(cap, side);
    }

    protected abstract boolean canForwardCapability(Capability<?> cap);

    public void slave(BlockPos pos)
    {
        masterBlockPos = Optional.of(pos);
        setChanged();
    }

    public void free()
    {
        masterBlockPos = Optional.empty();
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        super.saveAdditional(pTag);

        masterBlockPos.ifPresent(pos -> pTag.put("master", NbtUtils.writeBlockPos(pos)));
    }

    @Override
    public void load(CompoundTag pTag)
    {
        super.load(pTag);

        masterBlockPos = Optional.ofNullable(pTag.contains("master") ? NbtUtils.readBlockPos(pTag.getCompound("master")) : null);
    }
}
