package tetrago.caelum.common.capability;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.List;

public interface IMultiblocksRecord
{
    List<Pair<BlockPos, BoundingBox>> getMultiblocks();
    void add(BlockPos pos, BoundingBox box);
    void remove(BlockPos pos);
}
