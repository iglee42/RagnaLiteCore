package fr.iglee42.ragnalitecore.packets;


import com.lowdragmc.mbd2.common.blockentity.MachineBlockEntity;
import com.lowdragmc.mbd2.common.machine.MBDMachine;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record DeleteMagicCircleS2C(BlockPos pos) {

    public static void encode(DeleteMagicCircleS2C packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static DeleteMagicCircleS2C decode(FriendlyByteBuf buffer) {
        return new DeleteMagicCircleS2C(buffer.readBlockPos());
    }

    public static void handle(DeleteMagicCircleS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level level = Minecraft.getInstance().level;
            if (level != null && level.getBlockEntity(packet.pos()) instanceof MachineBlockEntity mbe && mbe.getMetaMachine() instanceof MBDMachine machine){
                if (machine instanceof MBDMagicCircle casted)
                    casted.rlc$setMagicCircle(null);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
