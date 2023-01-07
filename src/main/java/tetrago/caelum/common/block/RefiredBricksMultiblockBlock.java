package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Blocks;

public class RefiredBricksMultiblockBlock extends MultiblockBaseBlock
{
    public RefiredBricksMultiblockBlock()
    {
        super(Properties.copy(Blocks.BRICKS), new Builder()
                .define('R', ModBlocks.REFIRED_BRICKS)
                .define('H', block -> block.is(ModBlocks.REFIRED_BRICKS.get()) || block.is(ModBlocks.MATERIAL_HOPPER.get()))
                .define('C', block -> block.is(ModBlocks.REFIRED_BRICKS.get()) || block.is(ModBlocks.MATERIAL_CHUTE.get()))
                .layer(
                        "RRR",
                        "CRC",
                        "RRR")
                .layer(
                        "RRR",
                        "R R",
                        "RRR")
                .layer(
                        " R ",
                        "R R",
                        " R ")
                .layer(
                        " R ",
                        "RHR",
                        " R ")
                .anchor(1, 1, 0)
                .build());
    }
}
