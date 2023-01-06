package tetrago.caelum.common.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class MultiblocksRecord implements IMultiblocksRecord, INBTSerializable<ListTag>
{
    private List<BlockPos> multiblocks = new ArrayList<>();

    @Override
    public ListTag serializeNBT()
    {
        ListTag list = new ListTag();
        list.addAll(0, multiblocks.stream().map(pos -> NbtUtils.writeBlockPos(pos)).toList());
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        multiblocks = nbt.getList(0).stream().map(tag -> NbtUtils.readBlockPos((CompoundTag)tag)).toList();
    }

    @Override
    public List<BlockPos> getMultiblocks()
    {
        return multiblocks;
    }

    @Override
    public void add(BlockPos pos)
    {
        multiblocks.add(pos);
    }

    @Override
    public void remove(BlockPos pos)
    {
        multiblocks.remove(pos);
    }
}
