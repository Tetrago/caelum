package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.EnergyPortBlockEntity;

public class EnergyPortBlock extends Block implements EntityBlock
{
    public EnergyPortBlock()
    {
        super(Properties.copy(Blocks.IRON_BLOCK));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new EnergyPortBlockEntity(pPos, pState);
    }
}
