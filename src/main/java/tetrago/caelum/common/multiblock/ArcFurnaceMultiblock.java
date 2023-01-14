package tetrago.caelum.common.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import tetrago.caelum.common.block.CoilBlock;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.blockentity.ArcFurnaceControllerBlockEntity;
import tetrago.caelum.datagen.ModBlockTagsProvider;

import java.util.List;

public class ArcFurnaceMultiblock extends Multiblock
{
    public ArcFurnaceMultiblock()
    {
        super(new Builder()
                .define('B', ModBlocks.REFIRED_BRICKS.get())
                .define('C', ModBlocks.ARC_FURNACE_CONTROLLER.get())
                .define('X', state -> state.is(ModBlocks.REFIRED_BRICKS.get()) || state.is(Blocks.AIR) || state.is(ModBlockTagsProvider.COIL))
                .define('X', ModBlocks.REFIRED_BRICKS.get(), ModBlocks.COPPER_COIL.get(), Blocks.AIR)
                .define('A', Blocks.AIR)
                .define('P', ModBlocks.REFIRED_BRICKS.get(), ModBlocks.ENERGY_PORT.get(), ModBlocks.MATERIAL_HOPPER.get(), ModBlocks.MATERIAL_CHUTE.get())
                .layer(
                        "  BBB  ",
                        " BBBBB ",
                        "BBBBBBB",
                        "BBBBBBB",
                        "BBBBBBB",
                        " BBBBB ",
                        "  BBB  ")
                .layer(
                        "  BPB  ",
                        " BBBBB ",
                        "BBAAABB",
                        "PBAAABP",
                        "BBAAABB",
                        " BBBBB ",
                        "  BCB  ")
                .layer(
                        "   B   ",
                        "  BBB  ",
                        " BXAXB ",
                        "BBAAABB",
                        " BXAXB ",
                        "  BBB  ",
                        "   B   ")
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
    public void onDeconstruct(Level level, BlockPos pos, Rotation rotation)
    {
        super.onDeconstruct(level, pos, rotation);

        if(level.isClientSide()) return;
        ((ArcFurnaceControllerBlockEntity)level.getBlockEntity(pos)).drop();
    }

    @Override
    protected boolean isValid(Level level, BlockPos anchor, Rotation rotation)
    {
        return getBlockPositions(anchor, rotation).stream().map(level::getBlockState).filter(state -> state.getBlock() instanceof CoilBlock).count() >= 4;
    }

    @Override
    public List<BoundingBox> getBoundingBoxes()
    {
        return List.of(new BoundingBox(1, 0, 1, 5, 2, 5),
                new BoundingBox(2, 3, 2, 5, 4, 5),
                new BoundingBox(2, 0, 0, 4, 1, 6),
                new BoundingBox(0, 0, 2, 6, 1, 4));
    }
}
