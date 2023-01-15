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
        add(ModBlocks.BAUXITE_ORE.get(), block -> createOreDrop(block, ModItems.RAW_BAUXITE.get()));
        add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), block -> createOreDrop(block, ModItems.RAW_BAUXITE.get()));
        add(ModBlocks.ILMENITE_ORE.get(), block -> createOreDrop(block, ModItems.RAW_ILMENITE.get()));
        add(ModBlocks.DEEPSLATE_ILMENITE_ORE.get(), block -> createOreDrop(block, ModItems.RAW_ILMENITE.get()));

        dropSelf(ModBlocks.ALUMINUM_BLOCK.get());
        dropSelf(ModBlocks.STEEL_BLOCK.get());
        dropSelf(ModBlocks.TITANIUM_BLOCK.get());

        dropSelf(ModBlocks.REFIRED_BRICKS.get());
        dropSelf(ModBlocks.MACHINE_FRAME.get());
        dropSelf(ModBlocks.ROLLER.get());

        dropSelf(ModBlocks.COPPER_COIL.get());
        dropSelf(ModBlocks.BASIC_MOTOR.get());

        dropSelf(ModBlocks.ARC_FURNACE_CONTROLLER.get());
        dropSelf(ModBlocks.MATERIAL_HOPPER.get());
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
