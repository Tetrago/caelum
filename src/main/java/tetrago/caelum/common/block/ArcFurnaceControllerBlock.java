package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.ArcFurnaceControllerBlockEntity;
import tetrago.caelum.common.multiblock.ModMultiblocks;
import tetrago.caelum.common.util.BlockEntityUtil;

public class ArcFurnaceControllerBlock extends MultiblockBlock implements EntityBlock
{
    public ArcFurnaceControllerBlock()
    {
        super(ModMultiblocks.ARC_FURNACE, Properties.copy(Blocks.BRICKS));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return super.getStateForPlacement(pContext).setValue(HorizontalDirectionalBlock.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(HorizontalDirectionalBlock.FACING);
    }

    @Override
    protected InteractionResult useConstructed(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if(!level.isClientSide())
        {
            BlockEntityUtil.openGui(level, pos, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new ArcFurnaceControllerBlockEntity(pPos, pState);
    }
}
