package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Caelum.MODID);

    public static final RegistryObject<Block> BAUXITE_ORE = simple("bauxite_ore", Blocks.IRON_ORE);
    public static final RegistryObject<Block> DEEPSLATE_BAUXITE_ORE = simple("deepslate_bauxite_ore", Blocks.DEEPSLATE_IRON_ORE);
    public static final RegistryObject<Block> ALUMINUM_BLOCK = simple("aluminum_block", Blocks.IRON_BLOCK);
    public static final RegistryObject<Block> STEEL_BLOCK = simple("steel_block", Blocks.IRON_BLOCK);

    public static final RegistryObject<Block> REFIRED_BRICKS = simple("refired_bricks", Blocks.BRICKS);
    public static final RegistryObject<Block> COPPER_COIL = BLOCKS.register("copper_coil", () -> new CoilBlock(200));

    public static final RegistryObject<Block> ARC_FURNACE_CONTROLLER = BLOCKS.register("arc_furnace_controller", ArcFurnaceControllerBlock::new);
    public static final RegistryObject<Block> MATERIAL_HOPPER = BLOCKS.register("material_hopper", () -> new OmnidirectionalBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER)));
    public static final RegistryObject<Block> MATERIAL_CHUTE = BLOCKS.register("material_chute", () -> new OmnidirectionalBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER)));
    public static final RegistryObject<Block> ENERGY_PORT = BLOCKS.register("energy_port", EnergyPortBlock::new);

    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", () -> new SolarPanelBlock(10000, 200));
    public static final RegistryObject<Block> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(25000, 400));

    private static RegistryObject<Block> simple(String name, Block parent)
    {
        return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.copy(parent)));
    }
}
