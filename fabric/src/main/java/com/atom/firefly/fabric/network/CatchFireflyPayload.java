package com.atom.firefly.fabric.network;

import com.atom.firefly.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CatchFireflyPayload(boolean mainHand) implements CustomPacketPayload {
    public static final Type<CatchFireflyPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "catch_firefly")
    );

    public static final StreamCodec<FriendlyByteBuf, CatchFireflyPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            CatchFireflyPayload::mainHand,
            CatchFireflyPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
