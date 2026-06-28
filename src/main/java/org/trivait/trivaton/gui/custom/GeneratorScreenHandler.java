package org.trivait.trivaton.gui.custom;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.trivait.trivaton.block.entity.custom.CircuitBoardCrafterBlockEntity;
import org.trivait.trivaton.block.entity.custom.GeneratorBlockEntity;
import org.trivait.trivaton.gui.ModScreenHandlers;
import org.trivait.trivaton.item.custom.CircuitBoardItem;

public class GeneratorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final GeneratorBlockEntity blockEntity;

    public GeneratorScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }

    public GeneratorScreenHandler(int syncId, PlayerInventory playerInventory,
                                  BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.GENERATOR_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        this.blockEntity = ((GeneratorBlockEntity) blockEntity);
        this.propertyDelegate = arrayPropertyDelegate;

        this.addSlot(new Slot(inventory, 0, 63, 34));
        this.addSlot(new Slot(inventory, 1, 44, 15));
        this.addSlot(new Slot(inventory, 2, 63, 12));
        this.addSlot(new Slot(inventory, 3, 82, 15));
        this.addSlot(new Slot(inventory, 4, 85, 34));
        this.addSlot(new Slot(inventory, 5, 82, 53));
        this.addSlot(new Slot(inventory, 6, 63, 56));
        this.addSlot(new Slot(inventory, 7, 44, 53));
        this.addSlot(new Slot(inventory, 8, 41, 34));
        this.addSlot(new Slot(inventory, 9, 137, 34));
        this.addSlot(new Slot(inventory, 10, 19, 34) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof CircuitBoardItem;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot == 9) {
                if (!this.insertItem(originalStack, 11, 47, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(originalStack, newStack);
            } else if (invSlot == 10) {
                if (!this.insertItem(originalStack, 11, 47, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (invSlot >= 11 && invSlot < 47) {
                if (originalStack.getItem() instanceof CircuitBoardItem) {
                    if (!this.insertItem(originalStack, 10, 11, false)) {
                        if (!this.insertItem(originalStack, 0, 9, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else {
                    if (!this.insertItem(originalStack, 0, 9, false)) {
                        if (invSlot < 38) {
                            if (!this.insertItem(originalStack, 38, 47, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else {
                            if (!this.insertItem(originalStack, 11, 38, false)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                }
            } else if (!this.insertItem(originalStack, 11, 47, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}