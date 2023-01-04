package tetrago.caelum.common;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.slf4j.Logger;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.blockentity.ModBlockEntities;
import tetrago.caelum.common.container.ModContainers;
import tetrago.caelum.datagen.ModItemModelProvider;
import tetrago.caelum.datagen.ModLanguageProvider;
import tetrago.caelum.datagen.ModBlockStateProvider;

@Mod(Caelum.MODID)
public class Caelum
{
    public static final String MODID = "caelum";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Caelum()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);

        bus.register(this);
    }

    @SubscribeEvent
    public void onGatherData(final GatherDataEvent gatherDataEvent)
    {
        final DataGenerator gen = gatherDataEvent.getGenerator();

        if(gatherDataEvent.includeClient())
        {
            gen.addProvider(new ModBlockStateProvider(gen, gatherDataEvent.getExistingFileHelper()));
            gen.addProvider(new ModItemModelProvider(gen, gatherDataEvent.getExistingFileHelper()));
            gen.addProvider(new ModLanguageProvider(gen, "en_US"));
        }
    }

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.BASIC_SOLAR_PANEL.get().asItem());
        }
    };
}
