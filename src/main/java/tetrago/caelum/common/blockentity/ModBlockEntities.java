package tetrago.caelum.common.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Caelum.MODID);

    public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL_BLOCK_ENTITY = BLOCK_ENTITIES.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new, ModBlocks.BASIC_SOLAR_PANEL.get(), ModBlocks.ADVANCED_SOLAR_PANEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlastFurnaceMultiblockBlockEntity>> BLAST_FURNACE_MULTIBLOCK_BLOCK_ENTITY = BLOCK_ENTITIES.register("blast_furance_multiblock", () -> BlockEntityType.Builder.of(BlastFurnaceMultiblockBlockEntity::new, ModBlocks.REFIRED_BRICKS.get()).build(null));
}
