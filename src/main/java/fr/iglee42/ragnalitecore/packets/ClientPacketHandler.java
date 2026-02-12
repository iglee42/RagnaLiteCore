package fr.iglee42.ragnalitecore.packets;

import com.lowdragmc.mbd2.common.blockentity.MachineBlockEntity;
import com.lowdragmc.mbd2.common.machine.MBDMachine;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class ClientPacketHandler {

    public static void handleCreateMagicCircle(CreateMagicCircleS2C packet, NetworkEvent.Context ctx) {
        assert ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT;

        var level = Minecraft.getInstance().level;
        if (level != null && level.getBlockEntity(packet.pos()) instanceof MachineBlockEntity mbe && mbe.getMetaMachine() instanceof MBDMachine machine){
            if (machine instanceof MBDMagicCircle casted)
                casted.rlc$setMagicCircle(new MagicCircle(level,packet.pos().offset(packet.offset()),packet.innerTexture(),packet.outerTexture()));
        }
    }

    public static void handleDeleteMagicCircle(DeleteMagicCircleS2C packet, NetworkEvent.Context ctx) {
        assert ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT;

        var level = Minecraft.getInstance().level;
        if (level != null && level.getBlockEntity(packet.pos()) instanceof MachineBlockEntity mbe && mbe.getMetaMachine() instanceof MBDMachine machine){
            if (machine instanceof MBDMagicCircle casted)
                casted.rlc$setMagicCircle(null);
        }
    }

}
