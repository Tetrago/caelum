package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tetrago.caelum.client.screen.SolarPanelScreen;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

public class ModLanguageProvider extends LanguageProvider
{
    public ModLanguageProvider(DataGenerator gen, String locale)
    {
        super(gen, Caelum.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        add("itemGroup.caelum", "Caelum");

        add(SolarPanelScreen.UNLOCALIZED_NAME, "Solar Panel");

        add(ModBlocks.BASIC_SOLAR_PANEL.get(), "Basic Solar Panel");
        add(ModBlocks.ADVANCED_SOLAR_PANEL.get(), "Advanced Solar Panel");
    }
}
