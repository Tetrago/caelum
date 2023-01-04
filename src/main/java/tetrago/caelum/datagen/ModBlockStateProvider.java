package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, Caelum.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        simpleBlock(ModBlocks.BASIC_SOLAR_PANEL.get(), models().cubeBottomTop(ModBlocks.BASIC_SOLAR_PANEL.getId().getPath(), modLoc("block/solar_panel_side"), modLoc("block/solar_panel_bottom"), modLoc("block/basic_solar_panel_top")));
        simpleBlock(ModBlocks.ADVANCED_SOLAR_PANEL.get(), models().cubeBottomTop(ModBlocks.ADVANCED_SOLAR_PANEL.getId().getPath(), modLoc("block/solar_panel_side"), modLoc("block/solar_panel_bottom"), modLoc("block/advanced_solar_panel_top")));
    }
}
