package tetrago.caelum.common;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import tetrago.caelum.common.block.SolarPanelBlock;
import tetrago.caelum.common.blockentity.SolarPanelBlockEntity;
import tetrago.caelum.common.container.SolarPanelContainer;
import tetrago.caelum.datagen.CaelumItemModelProvider;
import tetrago.caelum.datagen.CaelumLanguageProvider;
import tetrago.caelum.datagen.CaelumBlockStateProvider;

@Mod(Caelum.MODID)
public class Caelum
{
    public static final String MODID = "caelum";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Caelum()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent)
        {
            final Item.Properties properties = new Item.Properties().tab(ITEM_GROUP);

            BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                final BlockItem item = new BlockItem(block, properties);
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

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(BASIC_SOLAR_PANEL.get().asItem());
        }
    };

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<SolarPanelBlock> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", () -> new SolarPanelBlock(1000, 200));
    public static final RegistryObject<SolarPanelBlock> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(5000, 400));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL_BLOCK_ENTITY = BLOCK_ENTITIES.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new, BASIC_SOLAR_PANEL.get(), ADVANCED_SOLAR_PANEL.get()).build(null));

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final RegistryObject<MenuType<SolarPanelContainer>> SOLAR_PANEL_CONTAINER = CONTAINERS.register("solar_panel", () -> IForgeMenuType.create((windowId, inv, data) -> new SolarPanelContainer(windowId, data.readBlockPos(), inv)));
}
