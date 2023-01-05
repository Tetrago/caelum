package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Caelum.MODID);

    public static final RegistryObject<Block> ALUMINUM_ORE = BLOCKS.register("aluminum_ore", OreBlock::new);
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = BLOCKS.register("deepslate_aluminum_ore", OreBlock::new);

    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", () -> new SolarPanelBlock(10000, 200));
    public static final RegistryObject<Block> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(25000, 400));
}
