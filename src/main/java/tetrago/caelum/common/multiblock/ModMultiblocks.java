package tetrago.caelum.common.multiblock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModMultiblocks
{
    public static final DeferredRegister<Multiblock> MULTIBLOCKS = DeferredRegister.create(new ResourceLocation("multiblocks"), Caelum.MODID);

    public static final RegistryObject<Multiblock> BLAST_FURNACE = MULTIBLOCKS.register("blast_furnace", BlastFurnaceMultiblock::new);
}
