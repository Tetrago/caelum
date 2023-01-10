package tetrago.caelum.common.multiblock;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import tetrago.caelum.common.block.ModBlocks;

import java.util.List;

public class ArcFurnaceMultiblock extends Multiblock
{
    public ArcFurnaceMultiblock()
    {
        super(new Builder()
                .define('B', ModBlocks.REFIRED_BRICKS.get())
                .define('C', ModBlocks.ARC_FURNACE_CONTROLLER.get())
                .define('X', ModBlocks.REFIRED_BRICKS.get())
                .layer(
                        "  BBB  ",
                        " BBBBB ",
                        "BBBBBBB",
                        "BBBBBBB",
                        "BBBBBBB",
                        " BBBBB ",
                        "  BBB  ")
                .layer(
                        "  BBB  ",
                        " BBBBB ",
                        "BB   BB",
                        "BB   BB",
                        "BB   BB",
                        " BBBBB ",
                        "  BCB  ")
                .layer(
                        "       ",
                        "  BBB  ",
                        " BX XB ",
                        " B   B ",
                        " BX XB ",
                        "  BBB  ",
                        "       ")
                .layer(
                        "       ",
                        "       ",
                        "  XBX  ",
                        "  BBB  ",
                        "  XBX  ",
                        "       ",
                        "       ")
                .build(3, 1, 0));
    }

    @Override
    public List<BoundingBox> getBoundingBoxes()
    {
        return List.of(new BoundingBox(1, 0, 1, 6, 3, 6),
                new BoundingBox(2, 3, 2, 5, 4, 5),
                new BoundingBox(2, 0, 0, 5, 1, 7),
                new BoundingBox(0, 0, 2, 7, 1, 5));
    }
}
