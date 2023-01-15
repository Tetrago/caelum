package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
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
        simpleBlockItem(ModItems.BAUXITE_ORE);
        simpleBlockItem(ModItems.DEEPSLATE_BAUXITE_ORE);
        simpleBlockItem(ModItems.ILMENITE_ORE);
        simpleBlockItem(ModItems.DEEPSLATE_ILMENITE_ORE);

        simpleBlockItem(ModItems.ALUMINUM_BLOCK);
        simpleBlockItem(ModItems.STEEL_BLOCK);
        simpleBlockItem(ModItems.TITANIUM_BLOCK);

        simpleBlockItem(ModItems.REFIRED_BRICKS);
        simpleBlockItem(ModItems.MACHINE_FRAME);

        simpleBlockItem(ModItems.COPPER_COIL);

        simpleBlockItem(ModItems.ARC_FURNACE_CONTROLLER);
        simpleBlockItem(ModItems.MATERIAL_HOPPER);
        simpleBlockItem(ModItems.ENERGY_PORT);

        simpleBlockItem(ModItems.BASIC_SOLAR_PANEL);
        simpleBlockItem(ModItems.ADVANCED_SOLAR_PANEL);

        simpleItem(ModItems.RAW_BAUXITE);
        simpleItem(ModItems.RAW_ILMENITE);

        simpleItem(ModItems.ALUMINUM_INGOT);
        simpleItem(ModItems.ALUMINUM_NUGGET);
        simpleItem(ModItems.STEEL_INGOT);
        simpleItem(ModItems.STEEL_NUGGET);
        simpleItem(ModItems.TITANIUM_INGOT);
        simpleItem(ModItems.TITANIUM_NUGGET);

        simpleItem(ModItems.BASIC_CIRCUIT_BOARD);
        simpleItem(ModItems.BASIC_PHOTOVOLTAIC_CELL);
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
