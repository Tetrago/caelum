package tetrago.caelum.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiblocksRecordProvider implements ICapabilityProvider, ICapabilitySerializable<ListTag>
{
    private MultiblocksRecord multiblocksRecord = new MultiblocksRecord();
    private final LazyOptional<IMultiblocksRecord> record = LazyOptional.of(() -> multiblocksRecord);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        return record.cast();
    }

    @Override
    public ListTag serializeNBT()
    {
        return multiblocksRecord.serializeNBT();
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        multiblocksRecord.deserializeNBT(nbt);
    }
}
