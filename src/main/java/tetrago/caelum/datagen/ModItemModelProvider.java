package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Caelum.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        simpleBlockItem(ModItems.ALUMINUM_ORE);
        simpleBlockItem(ModItems.DEEPSLATE_ALUMINUM_ORE);
        simpleBlockItem(ModItems.ALUMINUM_BLOCK);

        withExistingParent(ModItems.BASIC_SOLAR_PANEL.get().getRegistryName().getPath(), modLoc("block/basic_solar_panel"));
        withExistingParent(ModItems.ADVANCED_SOLAR_PANEL.get().getRegistryName().getPath(), modLoc("block/advanced_solar_panel"));

        simpleItem(ModItems.RAW_ALUMINUM);
        simpleItem(ModItems.ALUMINUM_INGOT);
        simpleItem(ModItems.ALUMINUM_NUGGET);

        simpleItem(ModItems.BASIC_CIRCUIT_BOARD);
    }

    private void simpleBlockItem(final RegistryObject<Item> item)
    {
        withExistingParent(item.getId().getPath(), modLoc("block/" + item.getId().getPath()));
    }

    private void simpleItem(final RegistryObject<Item> item)
    {
        singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getId().getPath()));
    }
}
