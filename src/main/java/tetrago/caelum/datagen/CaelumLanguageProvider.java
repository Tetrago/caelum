package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tetrago.caelum.common.Caelum;

public class CaelumLanguageProvider extends LanguageProvider
{
    public CaelumLanguageProvider(DataGenerator gen, String locale)
    {
        super(gen, Caelum.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        add("itemGroup.caelum", "Caelum");

        add(Caelum.BASIC_SOLAR_PANEL.get(), "Basic Solar Panel");
        add(Caelum.ADVANCED_SOLAR_PANEL.get(), "Advanced Solar Panel");
    }
}
