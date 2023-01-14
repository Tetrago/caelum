package tetrago.caelum.common.event;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.MultiblockBlock;
import tetrago.caelum.common.capability.ModCapabilities;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Caelum.MODID)
public class MultiblockEventBusSubscriber
{
    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event)
    {
        if(event.getWorld().isClientSide()) return;
        final Level level = (Level)event.getWorld();

        level.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).ifPresent(cap -> {
            cap.isWithinMultiblock(event.getPos()).ifPresent(inst -> {
                event.setCanceled(true);

                final BlockState state = level.getBlockState(inst.getAnchorPosition());
                ((MultiblockBlock)state.getBlock()).onDeconstruct(state, level, inst.getAnchorPosition(), inst.getRotation());

                cap.remove(inst);
            });
        });
    }
}
