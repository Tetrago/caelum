package tetrago.caelum.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiblockInstanceRecordProvider implements ICapabilityProvider, ICapabilitySerializable<ListTag>
{
    private MultiblockInstanceRecord instanceRecord = new MultiblockInstanceRecord();
    private final LazyOptional<IMultiblocksRecord> record = LazyOptional.of(() -> instanceRecord);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return record.cast();
    }

    @Override
    public ListTag serializeNBT()
    {
        return instanceRecord.serializeNBT();
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        instanceRecord.deserializeNBT(nbt);
    }
}
