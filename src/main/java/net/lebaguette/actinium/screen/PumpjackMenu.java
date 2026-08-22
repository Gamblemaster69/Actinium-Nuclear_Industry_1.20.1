package net.lebaguette.actinium.screen;

import net.lebaguette.actinium.block.ModBlocks;
import net.lebaguette.actinium.block.entity.PumpjackBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class PumpjackMenu extends AbstractContainerMenu {

    public final PumpjackBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public PumpjackMenu(int pContainerId, Inventory pPlayerInv, FriendlyByteBuf pExraData) {
        this(pContainerId, pPlayerInv, (PumpjackBlockEntity) pPlayerInv.player.level().getBlockEntity(pExraData.readBlockPos()), new SimpleContainerData(2));
    }

    public PumpjackMenu( int pContainerId, Inventory pPlayerInv, PumpjackBlockEntity pBlockEntity, ContainerData pData) {
        super(ModMenuTypes.PUMPJACK_MENU.get(), pContainerId);
        this.blockEntity = pBlockEntity;
        this.level = pPlayerInv.player.level();
        this.data = pData;

        addDataSlots(pData);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler,0,58,91));
            this.addSlot(new SlotItemHandler(iItemHandler,1,104,91));
        });
       addPlayerInventory(pPlayerInv);
       addPlayerHotbar(pPlayerInv);
    }

    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;
    private static final int VANILLA_FIRST_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT; // = 2
    private static final int VANILLA_SLOT_COUNT = 36; // 27 Inventar + 9 Hotbar

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // Klick war auf einen Maschinen-Slot -> Item ins Spieler-Inventar verschieben
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, true)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // Klick war im Spieler-Inventar -> nur leere Eimer dürfen automatisch in den Input wandern
            if (sourceStack.is(Items.BUCKET)) {
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level,blockEntity.getBlockPos()),pPlayer,ModBlocks.PUMPJACK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 119 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playInvertory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playInvertory, i, 8 + i * 18, 177));
        }
    }

    public int getTankCapacity() {
        return data.get(1);
    }
    public int getFluidAmount() {
        return data.get(0);
    }
}
