package tetrago.caelum.common.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.INBTSerializable;
import tetrago.caelum.common.multiblock.Multiblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultiblockInstanceRecord implements IMultiblockInstanceRecord, INBTSerializable<ListTag>
{
    private List<Multiblock.Instance> list = new ArrayList<>();

    @Override
    public ListTag serializeNBT()
    {
        ListTag list = new ListTag();
        list.addAll(this.list.stream().map(Multiblock.Instance::serializeNBT).toList());
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        list = nbt.stream().map(tag -> {
            Multiblock.Instance inst = new Multiblock.Instance();
            inst.deserializeNBT((CompoundTag)tag);
            return inst;
        }).collect(Collectors.toList());
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

    @Override
    public Optional<Multiblock.Instance> isWithinMultiblock(BlockPos pos)
    {
        return list.stream().filter(inst -> inst.getBoundingBoxes().stream().anyMatch(box -> box.intersects(new BoundingBox(pos)))).findAny();
    }
}
