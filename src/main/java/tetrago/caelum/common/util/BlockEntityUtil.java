package tetrago.caelum.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.NetworkHooks;

public class BlockEntityUtil
{
    public static <T extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> tickerOf(BlockEntityType<A> type, BlockEntityType<T> target, BlockEntityTicker<? super T> ticker)
    {
        return type == target ? (BlockEntityTicker<A>)ticker : null;
    }

    public static boolean openGui(Level level, BlockPos pos, Player player)
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
