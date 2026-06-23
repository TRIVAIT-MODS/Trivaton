package org.trivait.trivaton.block.entity;


import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.trivait.trivaton.Trivaton;
import org.trivait.trivaton.block.ModBlocks;
import org.trivait.trivaton.block.entity.custom.SieveBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<SieveBlockEntity> SIEVE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Trivaton.id("sieve_be"),
                    BlockEntityType.Builder.create(SieveBlockEntity::new, ModBlocks.SIEVE).build(null));

    public static void register() {

    }
}
