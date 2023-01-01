package tetrago.caelum;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import tetrago.caelum.common.block.BasicSolarPanelBlock;
import tetrago.caelum.datagen.CaelumBlockStateProvider;
import tetrago.caelum.datagen.CaelumItemModelProvider;
import tetrago.caelum.datagen.CaelumLanguageProvider;

@Mod(Caelum.MODID)
public class Caelum
{
    public static final String MODID = "caelum";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Caelum()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
        {
            BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                final BlockItem item = new BlockItem(block, new Item.Properties());
                item.setRegistryName(block.getRegistryName());
                itemRegistryEvent.getRegistry().register(item);
            });
        }

        @SubscribeEvent
        public static void onGatherData(final GatherDataEvent gatherDataEvent)
        {
            final DataGenerator gen = gatherDataEvent.getGenerator();

            if(gatherDataEvent.includeClient())
            {
                gen.addProvider(new CaelumBlockStateProvider(gen, gatherDataEvent.getExistingFileHelper()));
                gen.addProvider(new CaelumItemModelProvider(gen, gatherDataEvent.getExistingFileHelper()));
                gen.addProvider(new CaelumLanguageProvider(gen, "en_US"));
            }
        }
    }

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<BasicSolarPanelBlock> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", BasicSolarPanelBlock::new);
}
