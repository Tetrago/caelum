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

    public static final RegistryObject<Item> RAW_ALUMINUM = ITEMS.register("raw_aluminum", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(PROPERTIES));

    public static final RegistryObject<Item> BASIC_SOLAR_PANEL = fromBlock(ModBlocks.BASIC_SOLAR_PANEL);
    public static final RegistryObject<Item> ADVANCED_SOLAR_PANEL = fromBlock(ModBlocks.ADVANCED_SOLAR_PANEL);

    private static RegistryObject<Item> fromBlock(final RegistryObject<Block> block)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), PROPERTIES));
    }
}
