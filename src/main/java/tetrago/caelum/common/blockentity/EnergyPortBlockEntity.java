package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyPortBlockEntity extends ProxyBlockEntity
{
    public EnergyPortBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.ENERGY_PORT.get(), pPos, pBlockState);
    }

    @Override
    protected boolean isProxyable(Capability<?> cap)
    {
        return cap == CapabilityEnergy.ENERGY;
    }
}
