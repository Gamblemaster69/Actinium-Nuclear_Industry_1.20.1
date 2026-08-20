package net.lebaguette.actinium.block.entity;

import net.lebaguette.actinium.fluid.ModFluids;
import net.lebaguette.actinium.item.ModItems;
import net.lebaguette.actinium.screen.PumpjackMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PumpjackBlockEntity extends BlockEntity implements MenuProvider {
    private static final int SLOTS = 2;
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int TANK_CAPACITY = 6000; // max Capacity in mB
    private static final int PUMP_RATE = 5; // mB/tick

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private final FluidTank fluidTank = new FluidTank(TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final ContainerData data;

    public PumpjackBlockEntity(BlockPos pPos, BlockState pState) {
        super(ModBlockEntities.PUMPJACK_BE.get(), pPos, pState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> fluidTank.getFluidAmount();
                    case 1 -> fluidTank.getCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
        lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.put("tank", fluidTank.writeToNBT(new CompoundTag()));
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        fluidTank.readFromNBT(pTag.getCompound("tank"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("displayname.actinium.pumpjack");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PumpjackMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        pumpOil();
        fillBucket();
    }

    private void pumpOil() {
        if (fluidTank.getFluidAmount() < fluidTank.getCapacity()) {
            fluidTank.fill(new FluidStack(
                    ModFluids.OIL.get(),
                    PUMP_RATE),
                    IFluidHandler.FluidAction.EXECUTE
            );
        }
    }

    private void fillBucket() {
        ItemStack inputStack = itemStackHandler.getStackInSlot(INPUT_SLOT);

        boolean hasEmptyBucket = inputStack.is(Items.BUCKET);
        boolean tankHasEnoughOil = fluidTank.getFluidAmount() >= 1000;
        boolean ouputHasSpace = canInsertOilBucketIntoOutput();

        if (hasEmptyBucket && tankHasEnoughOil && ouputHasSpace) {
            itemStackHandler.extractItem(INPUT_SLOT, 1,false);
            fluidTank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

            ItemStack currentOutput = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
            itemStackHandler.setStackInSlot(OUTPUT_SLOT,
                    new ItemStack(ModItems.OIL_BUCKET.get(), currentOutput.getCount()));
        }
    }

    private boolean canInsertOilBucketIntoOutput() {
        ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
        boolean isEmpty = outputStack.isEmpty();
        return isEmpty;
    }
}
