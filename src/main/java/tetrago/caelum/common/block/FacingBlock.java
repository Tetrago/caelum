package tetrago.caelum.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class FacingBlock extends Block
{
    public static final Property<Direction> DIRECTION = DirectionProperty.create("direction");

    public FacingBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return defaultBlockState().setValue(DIRECTION, pContext.getClickedFace().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation)
    {
        return pState.setValue(DIRECTION, pRotation.rotate(pState.getValue(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror)
    {
        return pState.setValue(DIRECTION, pMirror.mirror(pState.getValue(DIRECTION)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(DIRECTION);
    }
}
