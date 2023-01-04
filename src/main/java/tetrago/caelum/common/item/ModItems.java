package tetrago.caelum.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Caelum.MODID)
public class ModItems
{
    @SubscribeEvent
    public static final void onRegisterItem(final RegistryEvent.Register<Item> itemRegistryEvent)
    {
        final Item.Properties properties = new Item.Properties().tab(Caelum.ITEM_GROUP);

        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            final BlockItem item = new BlockItem(block, properties);
            item.setRegistryName(block.getRegistryName());

            itemRegistryEvent.getRegistry().register(item);
        });
    }
}
