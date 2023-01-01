package tetrago.caelum.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tetrago.caelum.Caelum;

public class CaelumBlockStateProvider extends BlockStateProvider
{
    public CaelumBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, Caelum.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        simpleBlock(Caelum.BASIC_SOLAR_PANEL.get());
    }
}
