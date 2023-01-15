package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.MaterialHopperBlockEntity;

public class MaterialHopperBlock extends Block implements EntityBlock
{
    public MaterialHopperBlock()
    {
        super(Properties.copy(Blocks.HOPPER));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new MaterialHopperBlockEntity(pPos, pState);
    }
}
