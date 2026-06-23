package org.trivait.trivaton.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.trivait.trivaton.block.entity.ImplementedInventory;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.gui.custom.SieveScreenHandler;
import org.trivait.trivaton.recipe.ExtraOutput;
import org.trivait.trivaton.recipe.ModRecipes;
import org.trivait.trivaton.recipe.SieveRecipe;
import org.trivait.trivaton.recipe.SieveRecipeInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SieveBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int FIRST_OUTPUT_SLOT = 1;
    private static final int LAST_OUTPUT_SLOT = 6;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 120;

    public SieveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIEVE_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SieveBlockEntity.this.progress;
                    case 1 -> SieveBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SieveBlockEntity.this.progress = value;
                    case 1 -> SieveBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        this.markDirty();
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN || side != Direction.UP) {
            return new int[]{1, 2, 3, 4, 5, 6};
        }
        return new int[]{0};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction direction) {
        return slot == INPUT_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return slot >= FIRST_OUTPUT_SLOT && slot <= LAST_OUTPUT_SLOT;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.trivaton.sieve");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInv, PlayerEntity player) {
        return new SieveScreenHandler(syncId, playerInv, this, this.propertyDelegate);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putInt("sieve.progress", progress);
        nbt.putInt("sieve.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory.clear();
        Inventories.readNbt(nbt, inventory, registries);
        progress = nbt.getInt("sieve.progress");
        maxProgress = nbt.getInt("sieve.max_progress");
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public void updateListeners() {
        if (this.world != null && !this.world.isClient()) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
            if (this.world instanceof ServerWorld serverWorld) {
                serverWorld.getChunkManager().markForUpdate(this.getPos());
            }
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        updateListeners();
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            markDirty();

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else if (progress > 0) {
            --progress;
            markDirty();
        }
        if (isCrafting()) {
            if (progress % 6 == 0){
                world.playSound(null, pos, SoundEvents.BLOCK_SUSPICIOUS_SAND_STEP, SoundCategory.BLOCKS, 1f, 2f);
            }
        }
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 120;
        markDirty();
    }

    private void craftItem() {
        Optional<RecipeEntry<SieveRecipe>> recipeOptional = getCurrentRecipe();
        if (recipeOptional.isEmpty()) return;

        SieveRecipe recipe = recipeOptional.get().value();
        this.removeStack(INPUT_SLOT, 1);
        world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1f, 2f);

        List<ItemStack> outputs = new ArrayList<>();
        ItemStack mainOutput = recipe.output().copy();

        for (ExtraOutput extra : recipe.extraOutputs()) {
            if (this.world.random.nextFloat() < extra.chance()) {
                if (extra.replace()) {
                    mainOutput = extra.stack().copy();
                } else {
                    outputs.add(extra.stack().copy());
                }
            }
        }

        outputs.add(0, mainOutput);

        for (ItemStack stack : outputs) {
            if (stack.isEmpty()) continue;

            for (int slot = FIRST_OUTPUT_SLOT; slot <= LAST_OUTPUT_SLOT; slot++) {
                ItemStack slotStack = this.getStack(slot);
                if (!slotStack.isEmpty() && ItemStack.areItemsAndComponentsEqual(slotStack, stack)) {
                    int transfer = Math.min(stack.getCount(), slotStack.getMaxCount() - slotStack.getCount());
                    if (transfer > 0) {
                        slotStack.increment(transfer);
                        stack.decrement(transfer);
                    }
                }
                if (stack.isEmpty()) break;
            }

            if (!stack.isEmpty()) {
                for (int slot = FIRST_OUTPUT_SLOT; slot <= LAST_OUTPUT_SLOT; slot++) {
                    if (this.getStack(slot).isEmpty()) {
                        this.setStack(slot, stack.copy());
                        stack.setCount(0);
                        break;
                    }
                }
            }

            if (!stack.isEmpty() && !this.world.isClient()) {
                net.minecraft.entity.ItemEntity entity = new net.minecraft.entity.ItemEntity(
                        this.world, this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5, stack
                );
                this.world.spawnEntity(entity);
            }
        }

        this.markDirty();
    }

    private void increaseCraftingProgress() {
                this.progress++;
            }

            private boolean hasRecipe() {
                Optional<RecipeEntry<SieveRecipe>> recipe = getCurrentRecipe();
                if (recipe.isEmpty()) return false;

                for (int slot = FIRST_OUTPUT_SLOT; slot <= LAST_OUTPUT_SLOT; slot++) {
                    if (this.getStack(slot).isEmpty()) return true;
                    if (ItemStack.areItemsAndComponentsEqual(this.getStack(slot), recipe.get().value().output())
                            && this.getStack(slot).getCount() < this.getStack(slot).getMaxCount()) {
                        return true;
                    }
                }
                return false;
            }

            private Optional<RecipeEntry<SieveRecipe>> getCurrentRecipe() {
                if (this.world == null) return Optional.empty();
                return this.world.getRecipeManager().getFirstMatch(ModRecipes.SIEVE_TYPE,
                        new SieveRecipeInput(inventory.get(INPUT_SLOT)), this.world);
            }

            @Nullable
            @Override
            public Packet<ClientPlayPacketListener> toUpdatePacket() {
                return BlockEntityUpdateS2CPacket.create(this);
            }

            @Override
            public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
                NbtCompound nbt = new NbtCompound();
                this.writeNbt(nbt, registries);
                return nbt;
            }
        }
