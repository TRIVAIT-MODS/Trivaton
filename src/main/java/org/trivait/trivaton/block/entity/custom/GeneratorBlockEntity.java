package org.trivait.trivaton.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
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
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.trivait.trivaton.block.entity.ImplementedInventory;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.gui.custom.GeneratorScreenHandler;
import org.trivait.trivaton.item.custom.CircuitBoardItem;
import org.trivait.trivaton.recipe.GeneratorRecipe;
import org.trivait.trivaton.recipe.GeneratorRecipeInput;
import org.trivait.trivaton.recipe.ModRecipes;
import org.trivait.trivaton.sound.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GeneratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(11, ItemStack.EMPTY);

    public static final int INPUT_SLOT = 0;
    public static final int SIDE_INPUT_SLOT_1 = 1;
    public static final int SIDE_INPUT_SLOT_2 = 2;
    public static final int SIDE_INPUT_SLOT_3 = 3;
    public static final int SIDE_INPUT_SLOT_4 = 4;
    public static final int SIDE_INPUT_SLOT_5 = 5;
    public static final int SIDE_INPUT_SLOT_6 = 6;
    public static final int SIDE_INPUT_SLOT_7 = 7;
    public static final int SIDE_INPUT_SLOT_8 = 8;
    public static final int OUTPUT_SLOT = 9;
    public static final int CIRCUIT_BOARD_SLOT = 10;

    public static final Integer[] SIDE_INPUTS = new Integer[]{
            SIDE_INPUT_SLOT_1,
            SIDE_INPUT_SLOT_2,
            SIDE_INPUT_SLOT_3,
            SIDE_INPUT_SLOT_4,
            SIDE_INPUT_SLOT_5,
            SIDE_INPUT_SLOT_6,
            SIDE_INPUT_SLOT_7,
            SIDE_INPUT_SLOT_8
    };

    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 100;
    private int durabilityCooldown;

    private double rotation;

    private boolean isCrafting = false;

    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GENERATOR_BE, pos, state);

        propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.trivaton.generator");
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GeneratorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);

        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }

        markDirty();
    }

    public void updateListeners() {
        if (world == null || world.isClient()) {
            return;
        }

        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        ((ServerWorld) world).getChunkManager().markForUpdate(pos);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        updateListeners();
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient) {
            if (hasRecipe()) {
                if (getCircuitBoardBoost()>0) {
                    world.playSound(null, pos, ModSounds.GENERATOR, SoundCategory.BLOCKS, 1, new Random().nextFloat(0.3f, 0.5f));
                    List<Integer> side_inputs = new ArrayList<>();

                    for (int i : SIDE_INPUTS) {
                        if (!inventory.get(i).isEmpty()) side_inputs.add(i);
                    }

                    int randomListIndex = new Random().nextInt(side_inputs.size());

                    int actualSlotIndex = side_inputs.get(randomListIndex);

                    ItemStack stackToParticle = inventory.get(actualSlotIndex).copy();

                    if (stackToParticle.isEmpty()) {
                        return;
                    }

                    spawnStackParticle(stackToParticle, world, pos);
                }

                isCrafting = true;
                increaseCraftingProgress();
                markDirty();

                if (hasCraftingFinished()) {
                    craftItem();
                    resetProgress();
                }

                ItemStack boardStack = inventory.get(CIRCUIT_BOARD_SLOT);

                if (!boardStack.isEmpty()) {
                    durabilityCooldown++;

                    if (durabilityCooldown >= 5) {
                        durabilityCooldown = 0;

                        if (boardStack.getDamage() < boardStack.getMaxDamage()) {
                            boardStack.damage(1, (ServerWorld) world, null, item -> {});
                            markDirty();
                        }
                    }
                }
            } else {
                isCrafting = false;
                if (progress > 0) {
                    progress = Math.max(0, progress - 8);
                    markDirty();
                }
            }
        }
    }

    private void spawnStackParticle(ItemStack stack, World world, BlockPos pos) {
        if (stack.isEmpty()) return;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.4;
        double z = pos.getZ() + 0.5;

        ((ServerWorld)world).spawnParticles(
                new ItemStackParticleEffect(ParticleTypes.ITEM, stack),
                x,
                y,
                z,
                1,
                0,
                0,
                0,
                0
        );
    }

    private int getCircuitBoardBoost() {
        ItemStack stack = inventory.get(CIRCUIT_BOARD_SLOT);

        if (stack.getItem() instanceof CircuitBoardItem board) {
            return board.getLevel(stack);
        }

        return 0;
    }

    private void increaseCraftingProgress() {
        progress += getCircuitBoardBoost();
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 100;
        markDirty();
    }

    private boolean isCircuitBoardBreak() {
        ItemStack stack = inventory.get(CIRCUIT_BOARD_SLOT);

        if (stack.isEmpty()) {
            return false;
        }

        return stack.getDamage() >= stack.getMaxDamage();
    }

    private void craftItem() {
        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5f, 1.5f);

        Optional<RecipeEntry<GeneratorRecipe>> recipeOptional = getCurrentRecipe();

        if (recipeOptional.isEmpty()) {
            return;
        }

        ItemStack output = recipeOptional.get().value().output();

        removeStack(INPUT_SLOT, 1);

        for (int i = 1; i <= 8; i++) {
            if (!getStack(i).isEmpty()) {
                removeStack(i, 1);
            }
        }

        setStack(OUTPUT_SLOT, new ItemStack(output.getItem(), getStack(OUTPUT_SLOT).getCount() + output.getCount()));

        markDirty();
    }

    public double getRotation() {
        if (isCrafting) rotation += 0.05;
        return rotation;
    }

    private boolean hasRecipe() {
        if (isCircuitBoardBreak()) {
            return false;
        }

        Optional<RecipeEntry<GeneratorRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();

        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output);
    }

    private GeneratorRecipeInput getGeneratorInput() {
        List<ItemStack> sideStacks = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            ItemStack stack = getStack(i);

            if (!stack.isEmpty()) {
                sideStacks.add(stack.copy());
            }
        }

        return new GeneratorRecipeInput(getStack(INPUT_SLOT).copy(), sideStacks);
    }

    private Optional<RecipeEntry<GeneratorRecipe>> getCurrentRecipe() {
        if (world == null) {
            return Optional.empty();
        }

        return world.getRecipeManager().getFirstMatch(ModRecipes.GENERATOR_TYPE, getGeneratorInput(), world);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return getStack(OUTPUT_SLOT).isEmpty() || getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = getStack(OUTPUT_SLOT).isEmpty() ? 64 : getStack(OUTPUT_SLOT).getMaxCount();

        return getStack(OUTPUT_SLOT).getCount() + count <= maxCount;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putInt("generator.progress", progress);
        nbt.putInt("generator.max_progress", maxProgress);
        nbt.putBoolean("generator.is_crafting", isCrafting);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        inventory.clear();
        Inventories.readNbt(nbt, inventory, registries);
        progress = nbt.getInt("generator.progress");
        maxProgress = nbt.getInt("generator.max_progress");
        isCrafting = nbt.getBoolean("generator.is_crafting");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt, registries);
        return nbt;
    }
}