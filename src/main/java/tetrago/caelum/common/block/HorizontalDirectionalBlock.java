package tetrago.caelum.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

public class HorizontalDirectionalBlock extends Block
{
    public static final Property<Direction> FACING = DirectionProperty.create("facing");

    public HorizontalDirectionalBlock(Properties pProperties)
    {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return super.getStateForPlacement(pContext).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(FACING);
    }
}
