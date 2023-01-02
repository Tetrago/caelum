package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.common.Caelum;

public class CaelumItemModelProvider extends ItemModelProvider
{
    public CaelumItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Caelum.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        Caelum.BLOCKS.getEntries().stream().map(obj -> obj.getId().getPath()).forEach(path -> withExistingParent(path, modLoc("block/" + path)));
    }
}
