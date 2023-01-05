package tetrago.caelum.common;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.blockentity.ModBlockEntities;
import tetrago.caelum.common.container.ModContainers;
import tetrago.caelum.common.item.ModItems;

@Mod(Caelum.MODID)
public class Caelum
{
    public static final String MODID = "caelum";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Caelum()
    {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModContainers.CONTAINERS.register(bus);
        ModItems.ITEMS.register(bus);
    }

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.BASIC_SOLAR_PANEL.get().asItem());
        }
    };
}
