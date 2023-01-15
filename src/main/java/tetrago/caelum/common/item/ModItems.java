package tetrago.caelum.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.CoilBlock;
import tetrago.caelum.common.block.ModBlocks;

public class ModItems
{
    public static final Item.Properties PROPERTIES = new Item.Properties().tab(Caelum.ITEM_GROUP);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Caelum.MODID);

    public static final RegistryObject<Item> BAUXITE_ORE = fromBlock(ModBlocks.BAUXITE_ORE);
    public static final RegistryObject<Item> DEEPSLATE_BAUXITE_ORE = fromBlock(ModBlocks.DEEPSLATE_BAUXITE_ORE);
    public static final RegistryObject<Item> ILMENITE_ORE = fromBlock(ModBlocks.ILMENITE_ORE);
    public static final RegistryObject<Item> DEEPSLATE_ILMENITE_ORE = fromBlock(ModBlocks.DEEPSLATE_ILMENITE_ORE);

    public static final RegistryObject<Item> ALUMINUM_BLOCK = fromBlock(ModBlocks.ALUMINUM_BLOCK);
    public static final RegistryObject<Item> STEEL_BLOCK = fromBlock(ModBlocks.STEEL_BLOCK);
    public static final RegistryObject<Item> TITANIUM_BLOCK = fromBlock(ModBlocks.TITANIUM_BLOCK);

    public static final RegistryObject<Item> REFIRED_BRICKS = fromBlock(ModBlocks.REFIRED_BRICKS);
    public static final RegistryObject<Item> MACHINE_FRAME = fromBlock(ModBlocks.MACHINE_FRAME);
    public static final RegistryObject<Item> ROLLER = fromBlock(ModBlocks.ROLLER);

    public static final RegistryObject<Item> COPPER_COIL = ITEMS.register(ModBlocks.COPPER_COIL.getId().getPath(), () -> new CoilBlockItem((CoilBlock)ModBlocks.COPPER_COIL.get(), PROPERTIES));

    public static final RegistryObject<Item> ARC_FURNACE_CONTROLLER = fromBlock(ModBlocks.ARC_FURNACE_CONTROLLER);
    public static final RegistryObject<Item> MATERIAL_HOPPER = fromBlock(ModBlocks.MATERIAL_HOPPER);
    public static final RegistryObject<Item> ENERGY_PORT = fromBlock(ModBlocks.ENERGY_PORT);

    public static final RegistryObject<Item> BASIC_SOLAR_PANEL = fromBlock(ModBlocks.BASIC_SOLAR_PANEL);
    public static final RegistryObject<Item> ADVANCED_SOLAR_PANEL = fromBlock(ModBlocks.ADVANCED_SOLAR_PANEL);

    public static final RegistryObject<Item> RAW_BAUXITE = simple("raw_bauxite");
    public static final RegistryObject<Item> RAW_ILMENITE = simple("raw_ilmenite");

    public static final RegistryObject<Item> ALUMINUM_INGOT = simple("aluminum_ingot");
    public static final RegistryObject<Item> ALUMINUM_NUGGET = simple("aluminum_nugget");
    public static final RegistryObject<Item> STEEL_INGOT = simple("steel_ingot");
    public static final RegistryObject<Item> STEEL_NUGGET = simple("steel_nugget");
    public static final RegistryObject<Item> TITANIUM_INGOT = simple("titanium_ingot");
    public static final RegistryObject<Item> TITANIUM_NUGGET = simple("titanium_nugget");

    public static final RegistryObject<Item> BASIC_CIRCUIT_BOARD = simple("basic_circuit_board");
    public static final RegistryObject<Item> BASIC_PHOTOVOLTAIC_CELL = simple("basic_photovoltaic_cell");

    private static RegistryObject<Item> fromBlock(final RegistryObject<Block> block)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), PROPERTIES));
    }

    private static RegistryObject<Item> simple(String name)
    {
        return ITEMS.register(name, () -> new Item(PROPERTIES));
    }
}
