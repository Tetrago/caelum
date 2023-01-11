package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.common.block.HorizontalDirectionalBlock;
import tetrago.caelum.common.block.MultiblockBlock;
import tetrago.caelum.common.capability.ModEnergyStorage;
import tetrago.caelum.common.container.ArcFurnaceControllerContainer;

public class ArcFurnaceControllerBlockEntity extends BlockEntity implements MenuProvider
{
    private final ModEnergyStorage energyStorage = new ModEnergyStorage(250000, 1000)
    {
        @Override
        protected void onEnergyChanged()
        {
            setChanged();
        }
    };

    private final LazyOptional<IEnergyStorage> energyStorageCapability = LazyOptional.of(() -> energyStorage);

    public ArcFurnaceControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.ARC_FURNACE_CONTROLLER.get(), pPos, pBlockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == CapabilityEnergy.ENERGY && getBlockState().getValue(MultiblockBlock.CONSTRUCTED) && side != getBlockState().getValue(HorizontalDirectionalBlock.FACING))
        {
            return energyStorageCapability.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        super.load(pTag);

        if(pTag.contains("energy"))
        {
            energyStorage.setEnergyStored(pTag.getInt("energy"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        super.saveAdditional(pTag);

        pTag.putInt("energy", energyStorage.getEnergyStored());
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
