package tetrago.caelum.common.capability;

import net.minecraft.core.BlockPos;

import java.util.List;

public interface IMultiblocksRecord
{
    List<BlockPos> getMultiblocks();
    void add(BlockPos pos);
    void remove(BlockPos pos);
}
