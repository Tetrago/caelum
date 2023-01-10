package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.ModBlockEntities;
import tetrago.caelum.common.blockentity.SolarPanelBlockEntity;

public class SolarPanelBlock extends ModEntityBlock
{
    private final int bufferCapacity;
    private final int generationRate;

    public SolarPanelBlock(int bufferCapacity, int generationRate)
    {
        super(Properties.of(Material.METAL).strength(2));

        this.bufferCapacity = bufferCapacity;
        this.generationRate = generationRate;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
    {
        return Shapes.box(0, 0, 0, 1, 3.0f / 16, 1);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return tickerOf(type, ModBlockEntities.SOLAR_PANEL_BLOCK_ENTITY.get(), SolarPanelBlockEntity::tick);
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
        if(level.isClientSide()) return InteractionResult.SUCCESS;

        openGui(level, pos, player);
        return InteractionResult.CONSUME;
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
