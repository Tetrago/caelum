package tetrago.caelum.common.capability;

import net.minecraft.core.BlockPos;
import tetrago.caelum.common.multiblock.Multiblock;

import java.util.List;
import java.util.Optional;

public interface IMultiblockInstanceRecord
{
    List<Multiblock.Instance> getMultiblocks();
    void add(Multiblock.Instance instance);
    void remove(Multiblock.Instance instance);

    Optional<Multiblock.Instance> isWithinMultiblock(BlockPos pos);
}
