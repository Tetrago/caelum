package tetrago.caelum.common.capability;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class MultiblocksRecord implements IMultiblocksRecord, INBTSerializable<ListTag>
{
    private List<Pair<BlockPos, BoundingBox>> list = new ArrayList<>();

    @Override
    public ListTag serializeNBT()
    {
        ListTag list = new ListTag();
        list.addAll(0, this.list.stream().map(pair -> {
            CompoundTag tag = new CompoundTag();
            final BoundingBox box = pair.getSecond();

            tag.put("position", NbtUtils.writeBlockPos(pair.getFirst()));
            tag.put("begin", NbtUtils.writeBlockPos(new BlockPos(box.minX(), box.minY(), box.minZ())));
            tag.put("end", NbtUtils.writeBlockPos(new BlockPos(box.maxX(), box.maxY(), box.maxZ())));

            return tag;
        }).toList());
        return list;
    }

    @Override
    public void deserializeNBT(ListTag nbt)
    {
        list = nbt.getList(0).stream().map(tag -> {
            final CompoundTag compound = (CompoundTag)tag;

            final BlockPos pos = NbtUtils.readBlockPos(compound.getCompound("position"));
            final BlockPos begin = NbtUtils.readBlockPos(compound.getCompound("begin"));
            final BlockPos end = NbtUtils.readBlockPos(compound.getCompound("end"));

            return Pair.of(pos, new BoundingBox(begin.getX(), begin.getY(), begin.getZ(), end.getX(), end.getY(), end.getZ()));
        }).toList();
    }

    @Override
    public List<Pair<BlockPos, BoundingBox>> getMultiblocks()
    {
        return list;
    }

    @Override
    public void add(BlockPos pos, BoundingBox box)
    {
        list.add(Pair.of(pos, box));
    }

    @Override
    public void remove(BlockPos pos)
    {
        list.removeIf(pair -> pair.getFirst() == pos);
    }
}
