package tetrago.caelum.datagen.loot;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

public class ModBlockLoot extends BlockLoot
{
    @Override
    protected void addTables()
    {
        add(ModBlocks.ALUMINUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_ALUMINUM.get()));
        add(ModBlocks.DEEPSLATE_ALUMINUM_ORE.get(), block -> createOreDrop(block, ModItems.RAW_ALUMINUM.get()));
        dropSelf(ModBlocks.ALUMINUM_BLOCK.get());

        dropSelf(ModBlocks.REFIRED_BRICKS.get());
        dropSelf(ModBlocks.COPPER_COIL.get());

        dropSelf(ModBlocks.ARC_FURNACE_CONTROLLER.get());
        dropSelf(ModBlocks.MATERIAL_HOPPER.get());
        dropSelf(ModBlocks.MATERIAL_CHUTE.get());
        dropSelf(ModBlocks.ENERGY_PORT.get());

        dropSelf(ModBlocks.BASIC_SOLAR_PANEL.get());
        dropSelf(ModBlocks.ADVANCED_SOLAR_PANEL.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
