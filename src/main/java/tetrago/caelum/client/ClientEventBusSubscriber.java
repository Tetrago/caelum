package tetrago.caelum.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tetrago.caelum.client.gui.SolarPanelScreen;
import tetrago.caelum.common.Caelum;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Caelum.MODID)
public class ClientEventBusSubscriber
{
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(Caelum.SOLAR_PANEL_CONTAINER.get(), SolarPanelScreen::new);
        });
    }
}
