package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.common.container.ArcFurnaceControllerContainer;

public class ArcFurnaceControllerBlockEntity extends BlockEntity implements MenuProvider
{
    public ArcFurnaceControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.ARC_FURNACE_CONTROLLER.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent(ArcFurnaceControllerScreen.UNLOCALIZED_NAME);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new ArcFurnaceControllerContainer(pContainerId, pPlayerInventory, this);
    }
}
