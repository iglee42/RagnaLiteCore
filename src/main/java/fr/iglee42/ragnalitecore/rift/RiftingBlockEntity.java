package fr.iglee42.ragnalitecore.rift;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import fr.iglee42.ragnalitecore.RLCBlocks;
import fr.iglee42.ragnalitecore.RLCPackets;
import fr.iglee42.ragnalitecore.RagnaLiteCore;
import fr.iglee42.ragnalitecore.packets.LaunchShakeS2C;
import fr.iglee42.ragnalitecore.packets.SetRiftSizeS2C;
import fr.iglee42.ragnalitecore.utils.ShockwaveUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = RagnaLiteCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RiftingBlockEntity extends BlockEntity {

    private static final int TIME = 400;
    List<RiftingBlockEntity.BeaconBeamSection> beamSections = Lists.newArrayList();
    int levels;
    private List<RiftingBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.newArrayList();
    private int lastCheckY;
    private boolean isLaunched;
    private int activationTime;
    private final List<ServerPlayer> affectedPlayers = Lists.newArrayList();

    public RiftingBlockEntity(BlockPos p_155088_, BlockState p_155089_) {
        super(RLCBlocks.RIFTING_BLOCK_ENTITY.get(), p_155088_, p_155089_);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RiftingBlockEntity be) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        BlockPos cursor;

        if (be.lastCheckY < y) {
            cursor = pos;
            be.checkingBeamSections = Lists.newArrayList();
            be.lastCheckY = pos.getY() - 1;
        } else {
            cursor = new BlockPos(x, be.lastCheckY + 1, z);
        }

        RiftingBlockEntity.BeaconBeamSection section =
                be.checkingBeamSections.isEmpty()
                        ? null
                        : be.checkingBeamSections.get(0);

        int worldTop = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        for (int i = 0; i < 10 && cursor.getY() <= worldTop; ++i) {
            BlockState stateAt = level.getBlockState(cursor);

            if (section == null) {
                section = new RiftingBlockEntity.BeaconBeamSection(new float[]{1.0F, 1.0F, 1.0F});
                be.checkingBeamSections.add(section);
            }

            if (stateAt.getLightBlock(level, cursor) >= 15 && !stateAt.is(Blocks.BEDROCK)) {
                be.checkingBeamSections.clear();
                be.lastCheckY = worldTop;
                break;
            }

            section.increaseHeight();

            cursor = cursor.above();
            ++be.lastCheckY;
        }

        int previousLevels = be.levels;

        if (!be.checkingBeamSections.isEmpty()) {
            be.levels = updateBase(level, x, y, z);
        }
        if (be.levels == 4 && !be.beamSections.isEmpty()) {
            playSound(level, pos, SoundEvents.BEACON_AMBIENT);
            if (be.isLaunched) {
                be.activationTime--;
                if (be.activationTime <= 0) {
                    be.isLaunched = false;
                    List<ServerPlayer> players = be.affectedPlayers;
                    for (ServerPlayer player : players) {
                        if (!player.isAlive()) continue;
                        RLCPackets.sendToPlayer(new SetRiftSizeS2C(ClientRiftManager.MAX_RIFT_SIZE,false),player);
                        RiftManager.get(level).unlockPlayer(player);
                    }
                    ShockwaveUtil.launchBlocks(level,pos,4,4);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    EntityType.LIGHTNING_BOLT.create((ServerLevel) level,new CompoundTag(), bolt->bolt.setVisualOnly(true),pos, MobSpawnType.TRIGGERED,false,false);
                }
                if (!level.isClientSide && be.isLaunched){
                    float progress = (TIME - be.activationTime) / (float) TIME;
                    float size = ClientRiftManager.MAX_RIFT_SIZE * progress;
                    List<ServerPlayer> players = be.affectedPlayers;
                    for (ServerPlayer player : players) {
                        if (!player.isAlive()) continue;
                        RLCPackets.sendToPlayer(new SetRiftSizeS2C(size,true),player);
                    }
                }
            }
        } else {
            be.isLaunched = false;
        }

        if (be.lastCheckY >= worldTop) {
            be.lastCheckY = level.getMinBuildHeight() - 1;

            boolean wasActive = previousLevels > 3;
            be.beamSections = be.checkingBeamSections;
            boolean isActive = be.levels == 4;
            if (!level.isClientSide) {

                if (!wasActive && isActive) {
                    playSound(level, pos, SoundEvents.BEACON_ACTIVATE);
                    be.affectedPlayers.clear();
                    List<ServerPlayer> players = level.getNearbyPlayers(TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight(), null, new AABB(pos).inflate(32)).stream().map(ServerPlayer.class::cast).collect(Collectors.toUnmodifiableList());
                    be.affectedPlayers.addAll(players);
                    for (ServerPlayer player : players) {
                        RLCPackets.sendToPlayer(new LaunchShakeS2C(250, 0.15f), player);
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, TIME, 1, false, false));
                    }
                    be.activationTime = TIME;
                    be.isLaunched = true;
                } else if (wasActive && !isActive) {
                    playSound(level, pos, SoundEvents.BEACON_DEACTIVATE);
                    be.isLaunched = false;
                }

            }
            if (!isActive)
                be.beamSections.clear();
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {

        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();

        RiftingBlockEntity beacon = BlockPos.betweenClosedStream(new AABB(pos).inflate(5)).filter(p -> level.getBlockEntity(p) instanceof RiftingBlockEntity).map(p -> (RiftingBlockEntity) level.getBlockEntity(p)).findFirst().orElse(null);

        if (beacon != null && beacon.activationTime > 10 && beacon.isLaunched) {

            BlockPos beaconPos = beacon.getBlockPos();

            if (pos.equals(beaconPos)) {
                event.setCanceled(true);
                return;
            }

            for (int layer = 1; layer <= 4; layer++) {

                int radius = 5 - layer;

                if (pos.getY() == beaconPos.getY() - layer
                        && Math.abs(pos.getX() - beaconPos.getX()) <= radius
                        && Math.abs(pos.getZ() - beaconPos.getZ()) <= radius) {

                    event.setCanceled(true);
                    return;
                }
            }
        }
    }

    private static int updateBase(Level level, int x, int y, int z) {
        int i = 0;

        for (int j = 1; j <= 4; i = j++) {
            int k = y - j;
            if (k < level.getMinBuildHeight()) {
                break;
            }

            boolean flag = true;

            TagKey<Block> levelTag = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "rifting_block_base_" + j));
            for (int l = x - j; l <= x + j && flag; ++l) {
                for (int i1 = z - j; i1 <= z + j; ++i1) {
                    if (!level.getBlockState(new BlockPos(l, k, i1)).is(levelTag)) {
                        flag = false;
                        break;
                    }
                }
            }

            if (!flag) {
                break;
            }
        }

        return i;
    }

    public static void playSound(Level p_155104_, BlockPos p_155105_, SoundEvent p_155106_) {
        p_155104_.playSound(null, p_155105_, p_155106_, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public void setRemoved() {
        playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
        super.setRemoved();
    }

    public List<RiftingBlockEntity.BeaconBeamSection> getBeamSections() {
        return this.levels == 0 ? ImmutableList.of() : this.beamSections;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public void setLevel(Level p_155091_) {
        super.setLevel(p_155091_);
        this.lastCheckY = p_155091_.getMinBuildHeight() - 1;
    }

    public static class BeaconBeamSection {
        final float[] color;
        private int height;

        public BeaconBeamSection(float[] p_58718_) {
            this.color = p_58718_;
            this.height = 1;
        }

        protected void increaseHeight() {
            ++this.height;
        }

        public float[] getColor() {
            return this.color;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
