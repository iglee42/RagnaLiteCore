package fr.iglee42.ragnalitecore.packets;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record LaunchShakeS2C(int time, float amplifier) {
    public static void encode(LaunchShakeS2C packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.time);
        buffer.writeFloat(packet.amplifier);
    }

    public static LaunchShakeS2C decode(FriendlyByteBuf buffer) {
        return new LaunchShakeS2C(buffer.readInt(), buffer.readFloat());
    }

    public static void handle(LaunchShakeS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->ClientPacketHandler.handleSendShake(packet,ctx.get()));
        });
        ctx.get().setPacketHandled(true);
    }
}
