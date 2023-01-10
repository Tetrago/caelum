package tetrago.caelum.common.capability;

import com.mojang.datafixers.util.Pair;
import io.netty.util.internal.ObjectPool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import tetrago.caelum.common.multiblock.Multiblock;

import java.util.List;
import java.util.Optional;

public interface IMultiblocksRecord
{
    List<Multiblock.Instance> getMultiblocks();
    void add(Multiblock.Instance instance);
    void remove(Multiblock.Instance instance);

    Optional<Multiblock.Instance> isWithinMultiblock(BlockPos pos);
}
