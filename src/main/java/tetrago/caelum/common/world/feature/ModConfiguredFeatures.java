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
    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_BAUXITE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BAUXITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_BAUXITE_ORE.get().defaultBlockState()));

    public static final List<OreConfiguration.TargetBlockState> OVERWORLD_ILMENITE_ORES = List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.ILMENITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ILMENITE_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> BAUXITE_ORE = FeatureUtils.register("bauxite_ore", Feature.ORE, new OreConfiguration(OVERWORLD_BAUXITE_ORES, 9));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ILMENITE_ORE = FeatureUtils.register("ilmenite_ore", Feature.ORE, new OreConfiguration(OVERWORLD_ILMENITE_ORES, 6));
}
