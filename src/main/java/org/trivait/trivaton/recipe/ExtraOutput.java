package org.trivait.trivaton.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ExtraOutput(ItemStack stack, float chance, boolean replace) {

    public static final Codec<ExtraOutput> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemStack.CODEC.fieldOf("result").forGetter(ExtraOutput::stack),
            Codec.FLOAT.fieldOf("chance").forGetter(ExtraOutput::chance),
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(ExtraOutput::replace)
    ).apply(inst, ExtraOutput::new));

    // Исправлено: сетевой кодек генерируется напрямую из JSON кодека
    public static final PacketCodec<RegistryByteBuf, ExtraOutput> STREAM_CODEC =
            PacketCodecs.codec(CODEC).cast(); // Добавили .cast() для апкаста буфера
}
