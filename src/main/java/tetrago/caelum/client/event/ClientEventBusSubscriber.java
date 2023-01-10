package tetrago.caelum.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tetrago.caelum.client.screen.BlastFurnaceMultiblockScreen;
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
            MenuScreens.register(ModContainers.BLAST_FURNACE_MULTIBLOCK_CONTAINER.get(), BlastFurnaceMultiblockScreen::new);
            MenuScreens.register(ModContainers.SOLAR_PANEL_CONTAINER.get(), SolarPanelScreen::new);
        });
    }
}
