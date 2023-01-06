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
    public static final TagKey<Item> INGOTS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "ingots/aluminum"));
    public static final TagKey<Item> NUGGETS_ALUMINUM = ItemTags.create(new ResourceLocation("forge", "nuggets/aluminum"));

    public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(pGenerator, pBlockTagsProvider, Caelum.MODID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        tag(ORES_ALUMINUM)
                .add(ModItems.ALUMINUM_ORE.get())
                .add(ModItems.DEEPSLATE_ALUMINUM_ORE.get());

        tag(Tags.Items.ORES)
                .add(ModItems.ALUMINUM_ORE.get())
                .add(ModItems.DEEPSLATE_ALUMINUM_ORE.get());

        tag(RAW_MATERIALS_ALUMINUM).add(ModItems.RAW_ALUMINUM.get());
        tag(Tags.Items.RAW_MATERIALS)
                .add(ModItems.RAW_ALUMINUM.get());

        tag(INGOTS_ALUMINUM).add(ModItems.ALUMINUM_INGOT.get());
        tag(Tags.Items.INGOTS)
                .add(ModItems.ALUMINUM_INGOT.get());

        tag(NUGGETS_ALUMINUM).add(ModItems.ALUMINUM_NUGGET.get());
        tag(Tags.Items.NUGGETS)
                .add(ModItems.ALUMINUM_NUGGET.get());
    }
}
