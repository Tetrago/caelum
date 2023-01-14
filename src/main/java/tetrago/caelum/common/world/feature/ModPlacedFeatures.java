package tetrago.caelum.common.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures
{
    public static final Holder<PlacedFeature> BAUXITE_ORE_PLACED = PlacementUtils.register("bauxite_ore_placed", ModConfiguredFeatures.BAUXITE_ORE, ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
    public static final Holder<PlacedFeature> ILMENITE_ORE_PLACED = PlacementUtils.register("ilmenite_ore_placed", ModConfiguredFeatures.ILMENITE_ORE, ModOrePlacement.commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(30))));
}
