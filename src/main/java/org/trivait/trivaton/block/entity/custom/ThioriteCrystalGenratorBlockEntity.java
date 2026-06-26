package org.trivait.trivaton.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.block.custom.ThioriteCrystalGeneratorBlock;
import org.trivait.trivaton.block.entity.ImplementedInventory;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.gui.custom.ThioriteCrystalGenratorScreenHandler;
import org.trivait.trivaton.item.ModItems;
import org.trivait.trivaton.item.custom.CircuitBoardItem;

public class ThioriteCrystalGenratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 1;
    private static final int CIRCUIT_BOARD_SLOT = 0;
    private static final int BONE_MEAL_SLOT = 2;
    private static final int OUTPUT_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 1250;
    private boolean isCrafting = false;
    private int lastStage = 0;

    public ThioriteCrystalGenratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.THIORITE_CRYSTAL_GENERATOR_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ThioriteCrystalGenratorBlockEntity.this.progress;
                    case 1 -> ThioriteCrystalGenratorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: ThioriteCrystalGenratorBlockEntity.this.progress = value;
                    case 1: ThioriteCrystalGenratorBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.trivaton.thiorite_crystal_generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ThioriteCrystalGenratorScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("thiorite_crystal_generator.progress", progress);
        nbt.putInt("thiorite_crystal_generator.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("thiorite_crystal_generator.progress");
        maxProgress = nbt.getInt("thiorite_crystal_generator.max_progress");
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0, 1, 2, 3};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == 3) {
            return false;
        }
        if (slot == 0) {
            return stack.getItem() instanceof CircuitBoardItem;
        }
        if (slot == 1) {
            return stack.isOf(ModItems.THIORITE_INGOT);
        }
        if (slot == 2) {
            return stack.isOf(Items.BONE_MEAL);
        }

        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 3;
    }

    public int getStage() {
        if (maxProgress <= 0 || progress <= 0) {
            return 0;
        }
        if (progress >= maxProgress) {
            return 4;
        }
        return Math.clamp((progress * 5L) / maxProgress, 0, 4);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (canCraft()) {
            increaseCraftingProgress();
            isCrafting = true;
            markDirty(world, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                ((ServerWorld) world).spawnParticles(
                        new BlockStateParticleEffect(ParticleTypes.BLOCK, ModBlocks.THIORITE_STONE.getDefaultState()),
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        10,
                        0,
                        0,
                        0,
                        0.1
                );
                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                resetProgress();
            }
        } else {
            isCrafting = false;
            resetProgress();
        }

        if (isCrafting && progress > 0) {
            int speed = Math.max(1, getCircuitBoardPower());
            int interval = Math.max(1, maxProgress / 10);

            int previousMilestone = (progress - speed) / interval;
            int currentMilestone = progress / interval;

            if (currentMilestone > previousMilestone && currentMilestone <= 10) {
                ((ServerWorld) world).spawnParticles(
                        ParticleTypes.COMPOSTER,
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        10,
                        0.2,
                        0.2,
                        0.2,
                        0
                );
                world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                inventory.get(BONE_MEAL_SLOT).decrement(1);
            }
        }

        if (isCrafting && progress % 5 == 0) {
            ItemStack boardStack = inventory.get(CIRCUIT_BOARD_SLOT);
            int durability = boardStack.getMaxDamage() - boardStack.getDamage();
            if (durability>0) {
                boardStack.damage(1, ((ServerWorld) world), null, item -> {
                });
            }
        }

        if (lastStage != getStage()) {
            lastStage = getStage();
            world.setBlockState(pos, state.with(ThioriteCrystalGeneratorBlock.CRYSTAL_STAGE, lastStage));
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 1250;
    }

    private void craftItem() {
        ItemStack output = new ItemStack(ModItems.THIORITE_CRYSTAL);

        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress+=getCircuitBoardPower();
    }

    private int getCircuitBoardPower() {
        ItemStack stack = inventory.get(CIRCUIT_BOARD_SLOT);
        if (stack.isEmpty() || !(stack.getItem() instanceof CircuitBoardItem)) return 0;
        return ((CircuitBoardItem) stack.getItem()).getLevel(inventory.get(CIRCUIT_BOARD_SLOT));
    }

    private boolean canCraft() {
        if (inventory.get(BONE_MEAL_SLOT).isEmpty()) return false;
        if (inventory.get(INPUT_SLOT).isEmpty()) return false;
        if (isCircuitBoardBreak()) return false;
        ItemStack output = new ItemStack(ModItems.THIORITE_CRYSTAL);

        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private boolean isCircuitBoardBreak() {
        ItemStack boardStack = inventory.get(CIRCUIT_BOARD_SLOT);
        if (boardStack.isEmpty()) return false;
        int durability = boardStack.getMaxDamage() - boardStack.getDamage();
        return !(durability>0);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}