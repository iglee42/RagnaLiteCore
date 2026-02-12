package fr.iglee42.ragnalitecore.packets;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CreateMagicCircleS2C(BlockPos pos, ResourceLocation innerTexture, ResourceLocation outerTexture,BlockPos offset) {
    public static void encode(CreateMagicCircleS2C packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeResourceLocation(packet.innerTexture);
        buffer.writeResourceLocation(packet.outerTexture);
        buffer.writeBlockPos(packet.offset);
    }

    public static CreateMagicCircleS2C decode(FriendlyByteBuf buffer) {
        return new CreateMagicCircleS2C(buffer.readBlockPos(), buffer.readResourceLocation(), buffer.readResourceLocation(),buffer.readBlockPos());
    }

    public static void handle(CreateMagicCircleS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,()->()->ClientPacketHandler.handleCreateMagicCircle(packet,ctx.get()));
        });
        ctx.get().setPacketHandled(true);
    }
}
