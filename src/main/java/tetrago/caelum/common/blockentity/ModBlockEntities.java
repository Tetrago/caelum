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

    public static final RegistryObject<BlockEntityType<ArcFurnaceControllerBlockEntity>> ARC_FURNACE_CONTROLLER = BLOCK_ENTITIES.register("arc_furnace_controller", () -> BlockEntityType.Builder.of(ArcFurnaceControllerBlockEntity::new, ModBlocks.ARC_FURNACE_CONTROLLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyPortBlockEntity>> ENERGY_PORT = BLOCK_ENTITIES.register("energy_port", () -> BlockEntityType.Builder.of(EnergyPortBlockEntity::new, ModBlocks.ENERGY_PORT.get()).build(null));
    public static final RegistryObject<BlockEntityType<MaterialHopperBlockEntity>> MATERIAL_HOPPER = BLOCK_ENTITIES.register("material_hopper", () -> BlockEntityType.Builder.of(MaterialHopperBlockEntity::new, ModBlocks.MATERIAL_HOPPER.get()).build(null));
    public static final RegistryObject<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANEL = BLOCK_ENTITIES.register("solar_panel", () -> BlockEntityType.Builder.of(SolarPanelBlockEntity::new, ModBlocks.BASIC_SOLAR_PANEL.get(), ModBlocks.ADVANCED_SOLAR_PANEL.get()).build(null));
}
