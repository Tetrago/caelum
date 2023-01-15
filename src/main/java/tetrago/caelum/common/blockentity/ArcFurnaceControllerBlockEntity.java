package tetrago.caelum.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.common.block.HorizontalDirectionalBlock;
import tetrago.caelum.common.block.MultiblockBlock;
import tetrago.caelum.common.capability.ModEnergyStorage;
import tetrago.caelum.common.capability.ProxyItemHandler;
import tetrago.caelum.common.container.ArcFurnaceControllerContainer;
import tetrago.caelum.common.recipe.ArcFurnaceRecipe;

import java.util.Optional;

public class ArcFurnaceControllerBlockEntity extends BlockEntity implements MenuProvider
{
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private final ModEnergyStorage energyStorage = new ModEnergyStorage(250000, 1000)
    {
        @Override
        protected void onEnergyChanged()
        {
            setChanged();
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerCapability = LazyOptional.of(() -> itemStackHandler);
    private final LazyOptional<IItemHandler> inputItemHandlerCapability = LazyOptional.of(() -> new ProxyItemHandler(itemStackHandler, 0, false));
    private final LazyOptional<IItemHandler> outputItemHandlerCapability = LazyOptional.of(() -> new ProxyItemHandler(itemStackHandler, 1, true));
    private final LazyOptional<IItemHandler> extraItemHandlerCapability = LazyOptional.of(() -> new ProxyItemHandler(itemStackHandler, 2, true));
    private final LazyOptional<IEnergyStorage> energyStorageCapability = LazyOptional.of(() -> energyStorage);

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 150;

    public ArcFurnaceControllerBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.ARC_FURNACE_CONTROLLER.get(), pPos, pBlockState);

        data = new ContainerData()
        {
            @Override
            public int get(int pIndex)
            {
                return switch(pIndex) {
                    default -> progress;
                    case 1 -> maxProgress;
                };
            }

            @Override
            public void set(int pIndex, int pValue)
            {
                switch(pIndex)
                {
                default -> progress = pValue;
                case 1 -> maxProgress = pValue;
                }
            }

            @Override
            public int getCount()
            {
                return 2;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArcFurnaceControllerBlockEntity blockEntity)
    {
        if(level.isClientSide()) return;

        if(hasRecipe(blockEntity))
        {
            ++blockEntity.progress;
            setChanged(level, pos, state);

            blockEntity.energyStorage.extractEnergy(700 / blockEntity.maxProgress, false);

            if(blockEntity.progress > blockEntity.maxProgress)
            {
                craft(blockEntity);
            }
        }
        else if(blockEntity.progress > 0)
        {
            blockEntity.progress = 0;
            setChanged(level, pos, state);
        }
    }

    private static boolean hasRecipe(ArcFurnaceControllerBlockEntity blockEntity)
    {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getStackInSlot(0));
        Optional<ArcFurnaceRecipe> recipe = blockEntity.level.getRecipeManager().getRecipeFor(ArcFurnaceRecipe.Type.INSTANCE, inventory, blockEntity.level);

        return recipe.isPresent() && canInsertIntoOutput(inventory, recipe.get().getResultItem()) && hasMinimumEnergy(blockEntity);
    }

    private static boolean canInsertIntoOutput(SimpleContainer inventory, ItemStack result)
    {
        final ItemStack stack = inventory.getItem(1);
        return stack.isEmpty()
                || (stack.getItem() == result.getItem() && stack.getCount() + result.getCount() < stack.getMaxStackSize());
    }

    private static boolean hasMinimumEnergy(ArcFurnaceControllerBlockEntity blockEntity)
    {
        return blockEntity.energyStorage.getEnergyStored() > 700;
    }

    private static void craft(ArcFurnaceControllerBlockEntity blockEntity)
    {
        final Level level = blockEntity.level;

        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getStackInSlot(0));
        Optional<ArcFurnaceRecipe> recipe = level.getRecipeManager().getRecipeFor(ArcFurnaceRecipe.Type.INSTANCE, inventory, level);

        if(recipe.isPresent())
        {
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.setStackInSlot(1, new ItemStack(recipe.get().getResultItem().getItem(), blockEntity.itemStackHandler.getStackInSlot(1).getCount() + recipe.get().getResultItem().getCount()));

            blockEntity.progress = 0;
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        final Direction facing = getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        if(getBlockState().getValue(MultiblockBlock.CONSTRUCTED) && side != facing)
        {
            if(cap == CapabilityEnergy.ENERGY)
            {
                return energyStorageCapability.cast();
            }
            else if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            {
                if(side == Rotation.CLOCKWISE_90.rotate(facing)) return inputItemHandlerCapability.cast();
                else if(side == Rotation.COUNTERCLOCKWISE_90.rotate(facing)) return outputItemHandlerCapability.cast();
                else if(side == Rotation.CLOCKWISE_180.rotate(facing)) return extraItemHandlerCapability.cast();
                else if(side == null) return itemHandlerCapability.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag pTag)
    {
        super.load(pTag);

        energyStorage.deserializeNBT(pTag.get("energy"));
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        pTag.putInt("progress", progress);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag)
    {
        super.saveAdditional(pTag);

        pTag.put("energy", energyStorage.serializeNBT());
        pTag.put("inventory", itemStackHandler.serializeNBT());
        progress = pTag.getInt("progress");
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
        return new ArcFurnaceControllerContainer(pContainerId, pPlayerInventory, this, data);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();

        itemHandlerCapability.invalidate();
        inputItemHandlerCapability.invalidate();
        outputItemHandlerCapability.invalidate();
        extraItemHandlerCapability.invalidate();
        energyStorageCapability.invalidate();
    }

    public void drop()
    {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); ++i)
        {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);
    }
}
