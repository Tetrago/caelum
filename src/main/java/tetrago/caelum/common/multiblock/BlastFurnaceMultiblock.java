package tetrago.caelum.common.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.blockentity.BlastFurnaceMultiblockBlockEntity;

public class BlastFurnaceMultiblock extends Multiblock
{
    public BlastFurnaceMultiblock()
    {
        super(new Builder()
                .define('R', ModBlocks.REFIRED_BRICKS.get())
                .layer(
                        "RRR",
                        "RRR",
                        "RRR")
                .layer(
                        "RRR",
                        "R R",
                        "RRR")
                .layer(
                        "RRR",
                        "R R",
                        "RRR")
                .layer(
                        "RRR",
                        "RRR",
                        "RRR")
                .build(1, 1, 0));
    }

    @Override
    public void onConstruct(Level level, BlockPos pos)
    {
        if(level.isClientSide()) return;
        level.setBlockEntity(new BlastFurnaceMultiblockBlockEntity(pos, level.getBlockState(pos)));
    }

    @Override
    public void onDeconstruct(Level level, BlockPos pos)
    {
        if(level.isClientSide()) return;
        level.removeBlockEntity(pos);
    }
}
