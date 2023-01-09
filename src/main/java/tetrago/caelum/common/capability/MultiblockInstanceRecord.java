package tetrago.caelum.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;
import tetrago.caelum.common.multiblock.Multiblock;

import java.util.ArrayList;
import java.util.List;

public class MultiblockInstanceRecord implements IMultiblocksRecord, INBTSerializable<ListTag>
{
    private List<Multiblock.Instance> list = new ArrayList<>();

    @Override
    public ListTag serializeNBT()
    {
        ListTag list = new ListTag();
        list.addAll(0, this.list.stream().map(Multiblock.Instance::serializeNBT).toList());
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        list = nbt.getList(0).stream().map(tag -> Multiblock.Instance.deserializeNBT((CompoundTag)tag)).toList();
    }

    @Override
    public List<Multiblock.Instance> getMultiblocks()
    {
        return list;
    }

    @Override
    public void add(Multiblock.Instance instance)
    {
        list.add(instance);
    }

    @Override
    public void remove(Multiblock.Instance instance)
    {
        list.removeIf(i -> i == instance);
    }
}
