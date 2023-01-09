package tetrago.caelum.common.event;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.NotImplementedException;
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

        level.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).ifPresent(cap -> cap.getMultiblocks().forEach(instance -> {
            if(!instance.getShape().bounds().intersects(AABB.of(new BoundingBox(event.getPos())))) return;

            cap.remove(instance);

            final BlockState state = level.getBlockState(event.getPos());
            ((MultiblockBlock)state.getBlock()).onDeconstruct(state, level, event.getPos());

            throw new NotImplementedException("Deconstruct multiblock");
        }));
    }
}
