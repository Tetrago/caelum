package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RollerBlock extends OmnidirectionalBlock
{
    public RollerBlock()
    {
        super(Properties.copy(Blocks.IRON_BLOCK));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        final Direction.Axis axis = pState.getValue(OmnidirectionalBlock.FACING).getAxis();
        return Shapes.box(
                axis.choose(0, 2.0f / 16, 2.0f / 16),
                axis.choose(2.0f / 16, 0, 2.0f/ 16),
                axis.choose(2.0f / 16, 2.0f / 16, 0),
                axis.choose(1, 14.0f / 16, 14.0f / 16),
                axis.choose(14.0f / 16, 1, 14.0f / 16),
                axis.choose(14.0f / 16, 14.0f / 16, 1));
    }
}
