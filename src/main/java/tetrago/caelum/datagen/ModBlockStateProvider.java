package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
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
        simpleBlock(ModBlocks.ALUMINUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        simpleBlock(ModBlocks.ALUMINUM_BLOCK.get());

        simpleBlock(ModBlocks.BASIC_SOLAR_PANEL.get(), models().withExistingParent("block/basic_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/basic_solar_panel")));
        simpleBlock(ModBlocks.ADVANCED_SOLAR_PANEL.get(), models().withExistingParent("block/advanced_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/advanced_solar_panel")));
    }
}
