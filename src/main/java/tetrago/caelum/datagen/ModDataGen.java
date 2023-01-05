package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import tetrago.caelum.common.Caelum;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Caelum.MODID)
public class ModDataGen
{
    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent gatherDataEvent)
    {
        final DataGenerator gen = gatherDataEvent.getGenerator();

        if(gatherDataEvent.includeClient())
        {
            gen.addProvider(new ModBlockStateProvider(gen, gatherDataEvent.getExistingFileHelper()));
            gen.addProvider(new ModItemModelProvider(gen, gatherDataEvent.getExistingFileHelper()));
            gen.addProvider(new ModLanguageProvider(gen, "en_US"));
        }

        if(gatherDataEvent.includeServer())
        {
            ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(gen, gatherDataEvent.getExistingFileHelper());
            gen.addProvider(blockTagsProvider);
            gen.addProvider(new ModItemTagsProvider(gen, blockTagsProvider, gatherDataEvent.getExistingFileHelper()));
            gen.addProvider(new ModLootTableProvider(gen));
            gen.addProvider(new ModRecipeProvider(gen));
        }
    }
}
