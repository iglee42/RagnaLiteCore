package fr.iglee42.ragnalitecore;

import fr.iglee42.ragnalitecore.packets.CreateMagicCircleS2C;
import fr.iglee42.ragnalitecore.packets.DeleteMagicCircleS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class RLCPackets {
    public static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RagnaLiteCore.MODID, "packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(CreateMagicCircleS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CreateMagicCircleS2C::decode)
                .encoder(CreateMagicCircleS2C::encode)
                .consumerMainThread(CreateMagicCircleS2C::handle)
                .add();

        net.messageBuilder(DeleteMagicCircleS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DeleteMagicCircleS2C::decode)
                .encoder(DeleteMagicCircleS2C::encode)
                .consumerMainThread(DeleteMagicCircleS2C::handle)
                .add();


    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}