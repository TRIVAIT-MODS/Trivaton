package org.trivait.trivaton.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.trivait.trivaton.block.entity.ModBlockEntities;
import org.trivait.trivaton.block.entity.custom.ThioriteCrystalGenratorBlockEntity;

public class ThioriteCrystalGeneratorBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final MapCodec<ThioriteCrystalGeneratorBlock> CODEC = ThioriteCrystalGeneratorBlock.createCodec(ThioriteCrystalGeneratorBlock::new);
    public static IntProperty CRYSTAL_STAGE = IntProperty.of("crystal_stage", 0, 4);

    private static final VoxelShape SHAPE =
            Block.createCuboidShape(1, 1, 1, 15, 16, 15);

    public ThioriteCrystalGeneratorBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(CRYSTAL_STAGE, 0));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if(world.isClient()) {
            return null;
        }

        return validateTicker(type, ModBlockEntities.THIORITE_CRYSTAL_GENERATOR_BE,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            if (world.getBlockEntity(pos) instanceof ThioriteCrystalGenratorBlockEntity blockEntity){
                player.openHandledScreen(blockEntity);
            }

            return ItemActionResult.SUCCESS;
        }

        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof ThioriteCrystalGenratorBlockEntity) {
                ItemScatterer.spawn(world, pos, ((ThioriteCrystalGenratorBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ThioriteCrystalGenratorBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CRYSTAL_STAGE);
    }
}
