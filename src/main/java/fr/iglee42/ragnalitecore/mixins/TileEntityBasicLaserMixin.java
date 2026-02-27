package fr.iglee42.ragnalitecore.mixins;

import com.mojang.logging.LogUtils;
import fr.iglee42.ragnalitecore.events.EntityHurtByLaserEvent;
import mekanism.api.math.FloatingLong;
import mekanism.common.lib.math.Pos3D;
import mekanism.common.tile.laser.TileEntityBasicLaser;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(value = TileEntityBasicLaser.class)
public abstract class TileEntityBasicLaserMixin {

    @Inject(method = "onUpdateServer",at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void rlc$invokeEvent(CallbackInfo ci, FloatingLong firing, Direction direction, Level level, Pos3D from, Pos3D _to, BlockHitResult result, float laserEnergyScale, FloatingLong remainingEnergy, List hitEntities, Pos3D finalFrom, FloatingLong energyPerDamage, Iterator var12, Entity entity, boolean updateEnergyScale, FloatingLong value, float damage, float health, int totemTimesUsed, boolean damaged){
        if (damaged){
            MinecraftForge.EVENT_BUS.post(new EntityHurtByLaserEvent(entity, (TileEntityBasicLaser) (Object) this, remainingEnergy));
        }
    }

}
