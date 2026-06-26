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
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
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
import org.trivait.trivaton.gui.custom.CircuitBoardCrafterScreenHandler;
import org.trivait.trivaton.gui.custom.SieveScreenHandler;
import org.trivait.trivaton.item.custom.CircuitBoardItem;
import org.trivait.trivaton.recipe.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CircuitBoardCrafterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(11, ItemStack.EMPTY);

    private static final int OUTPUT_SLOT = 9;
    private static final int CIRCUIT_BOARD_SLOT = 10;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 600;
    private boolean isCrafting = false;

    private int particleCooldown = 0;

    private int durabilityCooldown = 0;

    public CircuitBoardCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CIRCUIT_BOARD_CRAFTER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CircuitBoardCrafterBlockEntity.this.progress;
                    case 1 -> CircuitBoardCrafterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CircuitBoardCrafterBlockEntity.this.progress = value;
                    case 1 -> CircuitBoardCrafterBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    private int getCircuitBoardBoost() {
        if (!inventory.get(CIRCUIT_BOARD_SLOT).isEmpty()) {
            if (inventory.get(CIRCUIT_BOARD_SLOT).getItem() instanceof CircuitBoardItem circuitBoardItem) {
                return circuitBoardItem.getLevel(inventory.get(CIRCUIT_BOARD_SLOT));
            }
        }
        return 0;
    }

    public int getProgress() {
        return progress;
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
        return Text.translatable("block.trivaton.circuit_board_crafter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CircuitBoardCrafterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("circuit_board_crafter.progress", progress);
        nbt.putInt("circuit_board_crafter.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("circuit_board_crafter.progress");
        maxProgress = nbt.getInt("circuit_board_crafter.max_progress");
        super.readNbt(nbt, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            markDirty(world, pos, state);
            isCrafting = true;

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else if (progress > 0){
            progress-=8;
        } else if (progress < 0) {
            progress = 0;
        }

        if (isCrafting && progress > 0) {
            if (!inventory.get(CIRCUIT_BOARD_SLOT).isEmpty()) {
                durabilityCooldown++;
                if (durabilityCooldown >= 5) {
                    durabilityCooldown = 0;
                    ItemStack boardStack = inventory.get(CIRCUIT_BOARD_SLOT);
                    int durability = boardStack.getMaxDamage() - boardStack.getDamage();
                    if (durability>0) {
                        boardStack.damage(1, ((ServerWorld) world), null, item -> {
                        });
                    }
                }
            }

            particleCooldown++;
            particleCooldown+=getCircuitBoardBoost();
            if (particleCooldown >= 20) {
                particleCooldown = 0;
                world.playSound(null, pos, SoundEvents.BLOCK_LANTERN_HIT, SoundCategory.BLOCKS, 1, 0.3f);
                ((ServerWorld)world).spawnParticles(
                        ParticleTypes.COMPOSTER,
                        pos.getX() + 0.5,
                        pos.getY() + 1.1,
                        pos.getZ() + 0.5,
                        10,
                        0.2,
                        0,
                        0.2,
                        0
                );

                for (int i = 0; i<=8; i++) {
                    if (!inventory.get(i).isEmpty()) {
                        ((ServerWorld)world).spawnParticles(
                                new ItemStackParticleEffect(ParticleTypes.ITEM, inventory.get(i)),
                                pos.getX() + 0.5,
                                pos.getY() + 1.1,
                                pos.getZ() + 0.5,
                                3,
                                0.2,
                                0,
                                0.2,
                                0
                        );
                    }
                }
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 600;
    }

    private boolean isCircuitBoardBreak() {
        ItemStack boardStack = inventory.get(CIRCUIT_BOARD_SLOT);
        if (boardStack.isEmpty()) return false;
        int durability = boardStack.getMaxDamage() - boardStack.getDamage();
        return !(durability>0);
    }

    private void craftItem() {
        Optional<RecipeEntry<CircuitBoardCrafterRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack output = recipe.get().value().output();
        CraftingRecipeInput input = this.getCraftingInput();

        for (int i = 0; i < 9; i++) {
            this.removeStack(i, 1);
        }

        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
        this.progress+=getCircuitBoardBoost();
    }

    private boolean hasRecipe() {
        if (isCircuitBoardBreak()) return false;
        Optional<RecipeEntry<CircuitBoardCrafterRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }
    private CraftingRecipeInput getCraftingInput() {
        java.util.List<ItemStack> craftingStacks = new java.util.ArrayList<>();
        for (int i = 0; i < 9; i++) {
            craftingStacks.add(this.getStack(i));
        }
        return CraftingRecipeInput.create(3, 3, craftingStacks);
    }

    private Optional<RecipeEntry<CircuitBoardCrafterRecipe>> getCurrentRecipe() {
        if (this.getWorld() == null) return Optional.empty();

        CraftingRecipeInput input = this.getCraftingInput();

        Optional<RecipeEntry<CircuitBoardCrafterRecipe>> customRecipe = this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.CIRCUIT_BOARD_CRAFTER_TYPE, input, this.getWorld());

        if (customRecipe.isPresent()) {
            return customRecipe;
        }

        return Optional.empty();
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
