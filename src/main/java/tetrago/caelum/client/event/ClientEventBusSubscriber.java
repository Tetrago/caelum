package tetrago.caelum.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.client.screen.SolarPanelScreen;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.container.ModContainers;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Caelum.MODID)
public class ClientEventBusSubscriber
{
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.ARC_FURNACE_CONTROLLER.get(), ArcFurnaceControllerScreen::new);
            MenuScreens.register(ModContainers.SOLAR_PANEL.get(), SolarPanelScreen::new);
        });
    }
}
