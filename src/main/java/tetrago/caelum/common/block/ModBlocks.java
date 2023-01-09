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

    public static final RegistryObject<Block> ALUMINUM_ORE = BLOCKS.register("aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = BLOCKS.register("deepslate_aluminum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final RegistryObject<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> REFIRED_BRICKS = BLOCKS.register("refired_bricks", RefiredBricksMultiblockBlock::new);

    public static final RegistryObject<Block> MATERIAL_HOPPER = BLOCKS.register("material_hopper", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER)));
    public static final RegistryObject<Block> MATERIAL_CHUTE = BLOCKS.register("material_chute", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER)));

    public static final RegistryObject<Block> BASIC_SOLAR_PANEL = BLOCKS.register("basic_solar_panel", () -> new SolarPanelBlock(10000, 200));
    public static final RegistryObject<Block> ADVANCED_SOLAR_PANEL = BLOCKS.register("advanced_solar_panel", () -> new SolarPanelBlock(25000, 400));
}
