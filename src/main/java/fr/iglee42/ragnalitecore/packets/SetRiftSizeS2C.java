package fr.iglee42.ragnalitecore.packets;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SetRiftSizeS2C(float size,boolean isExpanding) {
    public static void encode(SetRiftSizeS2C packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.size);
        buffer.writeBoolean(packet.isExpanding);
    }

    public static SetRiftSizeS2C decode(FriendlyByteBuf buffer) {
        return new SetRiftSizeS2C(buffer.readFloat(),buffer.readBoolean());
    }

    public static void handle(SetRiftSizeS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->ClientPacketHandler.handleSetRiftSize(packet,ctx.get()));
        });
        ctx.get().setPacketHandled(true);
    }
}
