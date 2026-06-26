package org.trivait.trivaton.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.trivait.trivaton.Trivaton;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Integer> CREATIVE_CIRCUIT_BOARD_LEVEL =
            register("creative_circuit_board_level", builder -> builder.codec(Codec.INT));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Trivaton.id(name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register() {

    }
}