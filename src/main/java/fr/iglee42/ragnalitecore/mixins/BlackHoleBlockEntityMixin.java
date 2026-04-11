package fr.iglee42.ragnalitecore.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity;
import fr.iglee42.ragnalitecore.events.ItemAbsorbedByBlackHoleEvent;
import fr.iglee42.ragnalitecore.utils.BlackHoleThrows;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = BlackHoleBlockEntity.class,remap = true)
public abstract class BlackHoleBlockEntityMixin implements BlackHoleThrows {

    @Shadow
    protected abstract void throwOutItemStack(Level level, ItemStack stack, Vec3 pos);

    @Inject(method = "serverTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void rlc$absorbItem(Level level, BlockPos pos, BlockState state, BlackHoleBlockEntity blockEntity, CallbackInfo ci, @Local(name="entity")Entity entity) {
        if (entity instanceof ItemEntity itemEntity)
            MinecraftForge.EVENT_BUS.post(new ItemAbsorbedByBlackHoleEvent(itemEntity, blockEntity));
    }


    @Override
    public void ragnalitecore$throwsItemStack(Level level, ItemStack stack, Vec3 pos) {
        throwOutItemStack(level, stack, pos);
    }
}
