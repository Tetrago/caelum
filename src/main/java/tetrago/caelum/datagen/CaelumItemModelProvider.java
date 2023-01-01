package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.Caelum;

public class CaelumItemModelProvider extends ItemModelProvider
{
    public CaelumItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Caelum.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        withExistingParent(Caelum.BASIC_SOLAR_PANEL.getId().getPath(), modLoc("block/basic_solar_panel"));
    }
}
