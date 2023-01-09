package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.multiblock.Multiblock;

public abstract class MultiblockBlock extends Block
{
    public static final Property<Boolean> CONSTRUCTED = BooleanProperty.create("constructed");

    private final RegistryObject<Multiblock> multiblock;

    public MultiblockBlock(RegistryObject<Multiblock> multiblock, Properties pProperties)
    {
        super(pProperties);

        this.multiblock = multiblock;

        registerDefaultState(getStateDefinition().any().setValue(CONSTRUCTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(CONSTRUCTED);
    }
}
