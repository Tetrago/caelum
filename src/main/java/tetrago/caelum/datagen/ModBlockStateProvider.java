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
import tetrago.caelum.common.block.OmnidirectionalBlock;
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
        simpleBlock(ModBlocks.ALUMINUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        simpleBlock(ModBlocks.ALUMINUM_BLOCK.get());

        simpleBlock(ModBlocks.REFIRED_BRICKS.get());

        horizontalDirectionalBlock(ModBlocks.ARC_FURNACE_CONTROLLER.get(), state ->
                models().orientable(state.getBlock().getRegistryName().getPath() + (state.getValue(MultiblockBlock.CONSTRUCTED) ? "_constructed" : ""),
                        modLoc("block/arc_furnace_controller_side"),
                        modLoc("block/arc_furnace_controller_front" + (state.getValue(MultiblockBlock.CONSTRUCTED) ? "_constructed" : "")),
                        modLoc("block/arc_furnace_controller_top")));

        omnidirectionalBlock(ModBlocks.MATERIAL_HOPPER.get(), modLoc("block/material_hopper_side"), modLoc("block/material_hopper_bottom"), modLoc("block/material_hopper_top"));
        omnidirectionalBlock(ModBlocks.MATERIAL_CHUTE.get(), modLoc("block/material_hopper_side"), modLoc("block/material_hopper_top"), modLoc("block/material_hopper_top"));

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

    private void omnidirectionalBlock(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top)
    {
        getVariantBuilder(block).forAllStates(state -> {
            final Direction dir = state.getValue(OmnidirectionalBlock.FACING);
            return ConfiguredModel.builder().modelFile(models().cubeBottomTop(block.getRegistryName().getPath(), side, bottom, top))
                    .rotationX((int)(dir.getRotation().toXYZDegrees().x() + 360) % 360)
                    .rotationY((int)(dir.toYRot() + 180) % 360)
                    .build();
        });
    }
}
