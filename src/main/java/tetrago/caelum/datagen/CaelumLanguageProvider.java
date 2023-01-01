package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import tetrago.caelum.Caelum;

public class CaelumLanguageProvider extends LanguageProvider
{
    public CaelumLanguageProvider(DataGenerator gen, String locale)
    {
        super(gen, Caelum.MODID, locale);
    }

    @Override
    protected void addTranslations()
    {
        add(Caelum.BASIC_SOLAR_PANEL.get(), "Basic Solar Panel");
    }
}
