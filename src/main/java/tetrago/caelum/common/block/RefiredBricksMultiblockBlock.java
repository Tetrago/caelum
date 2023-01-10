package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tetrago.caelum.common.multiblock.ModMultiblocks;
import tetrago.caelum.common.util.BlockEntityUtil;

public class RefiredBricksMultiblockBlock extends MultiblockBlock
{
    public RefiredBricksMultiblockBlock()
    {
        super(ModMultiblocks.BLAST_FURNACE, Properties.copy(Blocks.BRICKS));
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
}
