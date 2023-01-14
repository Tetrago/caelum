package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.client.screen.SolarPanelScreen;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

public class ModLanguageProvider extends LanguageProvider
{
    public ModLanguageProvider(DataGenerator gen, String locale)
    {
        super(gen, Caelum.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        add(Caelum.modid("itemGroup.{}"), "Caelum");

        add(Caelum.modid("tooltip.{}.block.coil"), "Coil Strength");

        add(ArcFurnaceControllerScreen.UNLOCALIZED_NAME, "Arc Furnace");
        add(SolarPanelScreen.UNLOCALIZED_NAME, "Solar Panel");

        add(ModBlocks.BAUXITE_ORE.get(), "Bauxite Ore");
        add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), "Deepslate Bauxite Ore");
        add(ModBlocks.ALUMINUM_BLOCK.get(), "Block of Aluminum");
        add(ModBlocks.STEEL_BLOCK.get(), "Block of Steel");

        add(ModBlocks.REFIRED_BRICKS.get(), "Refired Bricks");
        add(ModBlocks.COPPER_COIL.get(), "Copper Coil");

        add(ModBlocks.ARC_FURNACE_CONTROLLER.get(), "Arc Furnace Controller");
        add(ModBlocks.MATERIAL_HOPPER.get(), "Material Hopper");
        add(ModBlocks.MATERIAL_CHUTE.get(), "Material Chute");
        add(ModBlocks.ENERGY_PORT.get(), "Energy Port");

        add(ModBlocks.BASIC_SOLAR_PANEL.get(), "Basic Solar Panel");
        add(ModBlocks.ADVANCED_SOLAR_PANEL.get(), "Advanced Solar Panel");

        add(ModItems.RAW_BAUXITE.get(), "Raw Bauxite");
        add(ModItems.ALUMINUM_INGOT.get(), "Aluminum Ingot");
        add(ModItems.ALUMINUM_NUGGET.get(), "Aluminum Nugget");
        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");
        add(ModItems.STEEL_NUGGET.get(), "Steel Nugget");

        add(ModItems.BASIC_CIRCUIT_BOARD.get(), "Basic Circuit Board");
        add(ModItems.BASIC_PHOTOVOLTAIC_CELL.get(), "Basic Photovoltaic Cell");
    }
}
