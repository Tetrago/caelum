package tetrago.caelum.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

public class ModItems
{
    private static final Item.Properties PROPERTIES = new Item.Properties().tab(Caelum.ITEM_GROUP);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Caelum.MODID);

    public static final RegistryObject<Item> ALUMINUM_ORE = fromBlock(ModBlocks.ALUMINUM_ORE);
    public static final RegistryObject<Item> DEEPSLATE_ALUMINUM_ORE = fromBlock(ModBlocks.DEEPSLATE_ALUMINUM_ORE);
    public static final RegistryObject<Item> ALUMINUM_BLOCK = fromBlock(ModBlocks.ALUMINUM_BLOCK);

    public static final RegistryObject<Item> BASIC_SOLAR_PANEL = fromBlock(ModBlocks.BASIC_SOLAR_PANEL);
    public static final RegistryObject<Item> ADVANCED_SOLAR_PANEL = fromBlock(ModBlocks.ADVANCED_SOLAR_PANEL);

    public static final RegistryObject<Item> RAW_ALUMINUM = simple("raw_aluminum");
    public static final RegistryObject<Item> ALUMINUM_INGOT = simple("aluminum_ingot");
    public static final RegistryObject<Item> ALUMINUM_NUGGET = simple("aluminum_nugget");

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
