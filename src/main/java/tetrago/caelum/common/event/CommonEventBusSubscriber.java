package tetrago.caelum.common.event;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.capability.IMultiblocksRecord;
import tetrago.caelum.common.capability.ModCapabilities;
import tetrago.caelum.common.capability.MultiblocksRecordProvider;
import tetrago.caelum.common.world.feature.ModPlacedFeatures;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Caelum.MODID)
public class CommonEventBusSubscriber
{
    @SubscribeEvent
    public static void onBiomeLoading(final BiomeLoadingEvent event)
    {
        List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(ModPlacedFeatures.ALUMINUM_ORE_PLACED);
    }

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event)
    {
        if(event.getWorld().isClientSide()) return;
        final Level level = (Level)event.getWorld();

        level.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).ifPresent(cap -> cap.getMultiblocks().forEach(pair -> {
            if(!pair.getSecond().intersects(new BoundingBox(event.getPos()))) return;

            cap.remove(pair.getFirst());
            MultiblockBaseBlock.deconstruct(level.getBlockState(pair.getFirst()), level, pair.getFirst());
        }));
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent event)
    {
        event.register(IMultiblocksRecord.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(final AttachCapabilitiesEvent<Level> event)
    {
        event.addCapability(new ResourceLocation(Caelum.MODID, "multiblocks_record"), new MultiblocksRecordProvider());
    }
}
