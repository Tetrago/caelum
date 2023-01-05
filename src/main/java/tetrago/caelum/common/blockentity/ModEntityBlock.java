package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public abstract class ModEntityBlock extends Block implements EntityBlock
{
    public ModEntityBlock(Properties props)
    {
        super(props);
    }

    protected <T extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> tickerOf(BlockEntityType<A> type, BlockEntityType<T> target, BlockEntityTicker<? super T> ticker)
    {
        return type == target ? (BlockEntityTicker<A>)ticker : null;
    }

    protected boolean openGui(Level level, BlockPos pos, Player player)
    {
        final BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof MenuProvider tile)
        {
            NetworkHooks.openGui((ServerPlayer)player, tile, pos);
            return true;
        }

        return false;
    }
}
