package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Caelum.MODID);

    public static final RegistryObject<SolarPanelBlock> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", () -> new SolarPanelBlock(1000, 200));
    public static final RegistryObject<SolarPanelBlock> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(5000, 400));
}
