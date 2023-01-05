package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

public class ModBlockTagsProvider extends BlockTagsProvider
{
    public static final TagKey<Block> ALUMINUM_OPE = BlockTags.create(new ResourceLocation("forge", "ores/aluminum"));

    public ModBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper helper)
    {
        super(pGenerator, Caelum.MODID, helper);
    }

    @Override
    protected void addTags()
    {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALUMINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ALUMINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.ALUMINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        tag(ALUMINUM_OPE)
                .add(ModBlocks.ALUMINUM_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_STONE)
                .add(ModBlocks.ALUMINUM_ORE.get());
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
                .add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get());
    }
}
