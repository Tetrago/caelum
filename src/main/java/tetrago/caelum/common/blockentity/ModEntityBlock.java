package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

public abstract class ModEntityBlock extends BaseEntityBlock
{
    public ModEntityBlock(Properties props)
    {
        super(props);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_)
    {
        return RenderShape.MODEL;
    }

    protected boolean tryOpenGui(Level level, BlockPos pos, Player player)
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
