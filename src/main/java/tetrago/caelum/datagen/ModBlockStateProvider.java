package tetrago.caelum.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.FacingBlock;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.block.MultiblockBlock;

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

        Caelum.LOGGER.debug(ModBlocks.REFIRED_BRICKS.getId().getPath());
        multiblock(ModBlocks.REFIRED_BRICKS.get(), modLoc("block/refired_bricks"), modLoc("block/refired_bricks_constructed"));

        facingBlock(ModBlocks.MATERIAL_HOPPER.get(), modLoc("block/material_hopper_side"), modLoc("block/material_hopper_bottom"), modLoc("block/material_hopper_top"));
        facingBlock(ModBlocks.MATERIAL_CHUTE.get(), modLoc("block/material_hopper_side"), modLoc("block/material_hopper_top"), modLoc("block/material_hopper_top"));

        simpleBlock(ModBlocks.BASIC_SOLAR_PANEL.get(), models().withExistingParent("block/basic_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/basic_solar_panel")));
        simpleBlock(ModBlocks.ADVANCED_SOLAR_PANEL.get(), models().withExistingParent("block/advanced_solar_panel", modLoc("block/solar_panel")).texture("texture", modLoc("block/advanced_solar_panel")));
    }

    private void multiblock(Block block, ResourceLocation texture, ResourceLocation constructed)
    {
        getVariantBuilder(block)
                .partialState().with(MultiblockBlock.CONSTRUCTED, true).modelForState().modelFile(models().cubeAll(block.getRegistryName().getPath() + "_constructed", constructed)).addModel()
                .partialState().with(MultiblockBlock.CONSTRUCTED, false).modelForState().modelFile(models().cubeAll(block.getRegistryName().getPath(), texture)).addModel();
    }

    private void facingBlock(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top)
    {
        getVariantBuilder(block).forAllStates(state -> {
            final Direction dir = state.getValue(FacingBlock.DIRECTION);
            return ConfiguredModel.builder().modelFile(models().cubeBottomTop(block.getRegistryName().getPath(), side, bottom, top))
                    .rotationX((int)(dir.getRotation().toXYZDegrees().x() + 360) % 360)
                    .rotationY((int)(dir.toYRot() + 180) % 360)
                    .build();
        });
    }
}
