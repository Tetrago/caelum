package tetrago.caelum.common.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import tetrago.caelum.common.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures
{
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_ALUMINUM_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ALUMINUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ALUMINUM_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ALUMINUM_ORE = FeatureUtils.register("aluminum_ore", Feature.ORE, new OreConfiguration(OVERWORLD_ALUMINUM_ORES, 9));
}
