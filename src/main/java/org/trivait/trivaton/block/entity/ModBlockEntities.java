package org.trivait.trivaton.block.entity;


import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.block.entity.custom.CircuitBoardCrafterBlockEntity;
import org.trivait.trivaton.block.entity.custom.ThioriteCrystalGenratorBlockEntity;
import org.trivait.trivaton.block.entity.custom.SieveBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<SieveBlockEntity> SIEVE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Trivaton.id("sieve_be"),
                    BlockEntityType.Builder.create(SieveBlockEntity::new, ModBlocks.SIEVE).build(null));
    public static final BlockEntityType<CircuitBoardCrafterBlockEntity> CIRCUIT_BOARD_CRAFTER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Trivaton.id("circuit_board_crafter_be"),
                    BlockEntityType.Builder.create(CircuitBoardCrafterBlockEntity::new, ModBlocks.CIRCUIT_BOARD_CRAFTER).build(null));
    public static final BlockEntityType<ThioriteCrystalGenratorBlockEntity> THIORITE_CRYSTAL_GENERATOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Trivaton.id("thorite_crystal_generator_be"),
                    BlockEntityType.Builder.create(ThioriteCrystalGenratorBlockEntity::new, ModBlocks.THIORITE_CRYSTAL_GENERATOR).build(null));

    public static void register() {

    }
}
