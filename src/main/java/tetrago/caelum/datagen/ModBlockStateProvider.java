package tetrago.caelum.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.HorizontalDirectionalBlock;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.block.MultiblockBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, Caelum.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        simpleBlock(ModBlocks.BAUXITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_BAUXITE_ORE.get());
        simpleBlock(ModBlocks.ILMENITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_ILMENITE_ORE.get());

        simpleBlock(ModBlocks.ALUMINUM_BLOCK.get());
        simpleBlock(ModBlocks.STEEL_BLOCK.get());
        simpleBlock(ModBlocks.TITANIUM_BLOCK.get());

        simpleBlock(ModBlocks.REFIRED_BRICKS.get());
        simpleBlock(ModBlocks.COPPER_COIL.get(), models().cubeTop(ModBlocks.COPPER_COIL.getId().getPath(), modLoc("block/copper_coil_side"), modLoc("block/copper_coil_top")));

        horizontalDirectionalBlock(ModBlocks.ARC_FURNACE_CONTROLLER.get(), state ->
                models().orientable(state.getBlock().getRegistryName().getPath() + (state.getValue(MultiblockBlock.CONSTRUCTED) ? "_constructed" : ""),
                        modLoc("block/arc_furnace_controller_side"),
                        modLoc("block/arc_furnace_controller_front" + (state.getValue(MultiblockBlock.CONSTRUCTED) ? "_constructed" : "")),
                        modLoc("block/arc_furnace_controller_top")));

        simpleBlock(ModBlocks.MATERIAL_HOPPER.get());
        simpleBlock(ModBlocks.ENERGY_PORT.get());

        simpleBlock(ModBlocks.BASIC_SOLAR_PANEL.get(), models().withExistingParent("block/basic_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/basic_solar_panel")));
        simpleBlock(ModBlocks.ADVANCED_SOLAR_PANEL.get(), models().withExistingParent("block/advanced_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/advanced_solar_panel")));
    }

    private void horizontalDirectionalBlock(Block block, ResourceLocation side, ResourceLocation front, ResourceLocation top)
    {
        horizontalDirectionalBlock(block, state -> models().orientable(block.getRegistryName().getPath(), side, front, top));
    }

    private void horizontalDirectionalBlock(Block block, Function<BlockState, ModelFile> func)
    {
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(func.apply(state))
                .rotationY((int)(state.getValue(HorizontalDirectionalBlock.FACING).toYRot() + 180) % 360)
                .build());
    }
}
