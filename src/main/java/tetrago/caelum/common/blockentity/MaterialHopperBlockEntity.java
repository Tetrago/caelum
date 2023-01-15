package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class MaterialHopperBlockEntity extends ProxyBlockEntity
{
    public MaterialHopperBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.MATERIAL_HOPPER.get(), pPos, pBlockState);
    }

    @Override
    protected boolean isProxyable(Capability<?> cap)
    {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }
}
