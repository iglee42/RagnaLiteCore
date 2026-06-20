package fr.iglee42.ragnalitecore.rift;

import fr.iglee42.ragnalitecore.RLCPackets;
import fr.iglee42.ragnalitecore.RagnaLiteCore;
import fr.iglee42.ragnalitecore.packets.SetRiftSizeS2C;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RagnaLiteCore.MODID)
public class RiftManager extends SavedData {

    private final List<UUID> unlockedPlayers;

    public RiftManager() {
        this.unlockedPlayers = Lists.newArrayList();
    }

    private RiftManager(CompoundTag tag){
        this();
        if (tag.contains("players", CompoundTag.TAG_LIST)){
            tag.getList("players",CompoundTag.TAG_STRING).stream().filter(t->t instanceof StringTag).map(StringTag.class::cast)
                    .map(t->UUID.fromString(t.getAsString())).forEach(unlockedPlayers::add);
        }
    }


    public static RiftManager get(Level level){
        if (level.isClientSide || level.getServer() == null){
            throw new RuntimeException(new IllegalAccessException("RiftManager should not be accessed on the client side"));
        }
        return get(level.getServer());
    }

    public static RiftManager get(MinecraftServer server){
        DimensionDataStorage storage = server.overworld().getDataStorage();
        return storage.computeIfAbsent(RiftManager::new,RiftManager::new, RagnaLiteCore.RAGNA_LITE + "_rift_manager");
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag tag) {
        ListTag players = new ListTag();
        unlockedPlayers.stream().map(UUID::toString).map(StringTag::valueOf).forEach(players::add);
        tag.put("players",players);
        return tag;
    }

    public void unlockPlayer(Player player){
        UUID uuid = player.getUUID();
        if (!unlockedPlayers.contains(uuid)){
            unlockedPlayers.add(uuid);
            setDirty();
        }
    }

    public boolean hasPlayerUnlocked(Player player){
        return unlockedPlayers.contains(player.getUUID());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        RiftManager manager = RiftManager.get(player.level());
        RLCPackets.sendToPlayer(new SetRiftSizeS2C(manager.hasPlayerUnlocked(player) ? ClientRiftManager.MAX_RIFT_SIZE : 0 ,false), (ServerPlayer) player);
    }

}
