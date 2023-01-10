package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.capability.ModCapabilities;
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

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit)
    {
        if(pState.getValue(CONSTRUCTED)) return useConstructed(pState, pLevel, pPos, pPlayer, pHand, pHit);

        return multiblock.get().match(pLevel, pPos).map(inst -> {
            onConstruct(pState, pLevel, pPos);

            if(!pLevel.isClientSide())
            {
                pLevel.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).ifPresent(cap -> cap.add(inst));
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }).orElse(super.use(pState, pLevel, pPos, pPlayer, pHand, pHit));
    }

    protected void onConstruct(BlockState state, Level level, BlockPos pos)
    {
        multiblock.get().onConstruct(level, pos);

        if(level.isClientSide()) return;
        level.setBlock(pos, state.setValue(CONSTRUCTED, true), 2);
    }

    public void onDeconstruct(BlockState state, Level level, BlockPos pos)
    {
        multiblock.get().onDeconstruct(level, pos);

        if(level.isClientSide()) return;
        level.setBlock(pos, state.setValue(CONSTRUCTED, false), 2);
    }

    protected InteractionResult useConstructed(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        return super.use(state, level, pos, player, hand, hit);
    }
}
