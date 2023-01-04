package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.ModBlockEntities;
import tetrago.caelum.common.blockentity.ModEntityBlock;
import tetrago.caelum.common.blockentity.SolarPanelBlockEntity;

public class SolarPanelBlock extends ModEntityBlock
{
    private final int bufferCapacity;
    private final int generationRate;

    public SolarPanelBlock(int bufferCapacity, int generationRate)
    {
        super(Properties.of(Material.METAL)
                .sound(SoundType.METAL)
                .strength(2)
                .requiresCorrectToolForDrops());

        this.bufferCapacity = bufferCapacity;
        this.generationRate = generationRate;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.SOLAR_PANEL_BLOCK_ENTITY.get(), SolarPanelBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new SolarPanelBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if(!level.isClientSide())
        {
            tryOpenGui(level, pos, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public int getEnergyBufferCapacity()
    {
        return bufferCapacity;
    }

    public int getEnergyGenerationRate()
    {
        return generationRate;
    }
}
