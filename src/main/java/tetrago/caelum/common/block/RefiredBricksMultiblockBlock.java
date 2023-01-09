package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Blocks;
import tetrago.caelum.common.multiblock.ModMultiblocks;

public class RefiredBricksMultiblockBlock extends MultiblockBlock
{
    public RefiredBricksMultiblockBlock()
    {
        super(ModMultiblocks.BLAST_FURNACE, Properties.copy(Blocks.BRICKS));
    }
}
