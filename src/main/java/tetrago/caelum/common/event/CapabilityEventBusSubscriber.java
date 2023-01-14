package tetrago.caelum.common.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.capability.IMultiblockInstanceRecord;
import tetrago.caelum.common.capability.MultiblockInstanceRecordProvider;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Caelum.MODID)
public class CapabilityEventBusSubscriber
{
    @SubscribeEvent
    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent event)
    {
        event.register(IMultiblockInstanceRecord.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(final AttachCapabilitiesEvent<Level> event)
    {
        event.addCapability(Caelum.loc("multiblocks_record"), new MultiblockInstanceRecordProvider());
    }
}
