package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.item.ModItems;

public class ModItemTagsProvider extends ItemTagsProvider
{
    public static final TagKey<Item> ORES_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "ores/aluminum"));
    public static final TagKey<Item> RAW_MATERIALS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "raw_materials/aluminum"));
    public static final TagKey<Item> STORAGE_BLOCKS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "storage_blocks/steel"));
    public static final TagKey<Item> INGOTS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "ingots/aluminum"));
    public static final TagKey<Item> NUGGETS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "nuggets/aluminum"));
    public static final TagKey<Item> STORAGE_BLOCKS_STEEL = ItemTags.create(new ResourceLocation("forge", "storage_blocks/aluminum"));
    public static final TagKey<Item> INGOTS_STEEL = ItemTags.create(new ResourceLocation("forge", "ingots/steel"));
    public static final TagKey<Item> NUGGETS_STEEL = ItemTags.create(new ResourceLocation("forge", "nuggets/steel"));

    public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(pGenerator, pBlockTagsProvider, Caelum.MODID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(ORES_ALUMINUM)
                .add(ModItems.BAUXITE_ORE.get())
                .add(ModItems.DEEPSLATE_BAUXITE_ORE.get());

        tag(Tags.Items.ORES)
                .add(ModItems.BAUXITE_ORE.get())
                .add(ModItems.DEEPSLATE_BAUXITE_ORE.get());

        tag(Tags.Items.RAW_MATERIALS)
                .add(ModItems.RAW_BAUXITE.get());

        tag(RAW_MATERIALS_ALUMINUM).add(ModItems.RAW_BAUXITE.get());

        tag(Tags.Items.STORAGE_BLOCKS)
                .add(ModItems.ALUMINUM_BLOCK.get())
                .add(ModItems.STEEL_BLOCK.get());

        tag(STORAGE_BLOCKS_ALUMINUM).add(ModItems.ALUMINUM_BLOCK.get());
        tag(STORAGE_BLOCKS_STEEL).add(ModItems.STEEL_BLOCK.get());

        tag(Tags.Items.INGOTS)
                .add(ModItems.ALUMINUM_INGOT.get())
                .add(ModItems.STEEL_INGOT.get());

        tag(INGOTS_ALUMINUM).add(ModItems.ALUMINUM_INGOT.get());
        tag(INGOTS_STEEL).add(ModItems.STEEL_INGOT.get());

        tag(Tags.Items.NUGGETS)
                .add(ModItems.ALUMINUM_NUGGET.get())
                .add(ModItems.STEEL_NUGGET.get());

        tag(NUGGETS_ALUMINUM).add(ModItems.ALUMINUM_NUGGET.get());
        tag(NUGGETS_STEEL).add(ModItems.STEEL_NUGGET.get());
    }
}
