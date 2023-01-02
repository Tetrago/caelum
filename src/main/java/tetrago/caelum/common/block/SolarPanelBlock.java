package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.blockentity.SolarPanelBlockEntity;
import tetrago.caelum.common.container.SolarPanelContainer;

public class SolarPanelBlock extends Block implements EntityBlock
{
    public static final String SCREEN_SOLAR_PANEL = "screen.caelum.solar_panel";

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
        return (l, p, s, t) -> {
            if(t instanceof SolarPanelBlockEntity tile)
            {
                tile.tick();
            }
        };
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
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof SolarPanelBlockEntity)
            {
                MenuProvider containerProvider = new MenuProvider()
                {
                    @Override
                    public Component getDisplayName()
                    {
                        return new TranslatableComponent(SCREEN_SOLAR_PANEL);
                    }

                    @Nullable
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
                    {
                        return new SolarPanelContainer(windowId, pos, inv, player);
                    }
                };

                NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
            }
        }

        return InteractionResult.SUCCESS;
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
