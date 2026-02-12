package fr.iglee42.ragnalitecore.mixins;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import com.drd.ad_extendra.common.entities.vehicles.CustomRocket;
import fr.iglee42.ragnalitecore.RLCRockets;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = FuelLoaderBlockEntity.class,remap = false)
public class FuelLoaderBlockEntityMixin {

    @Inject(method = "getFluidTags",at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void rlc$addFuelsFromOtherMods(CallbackInfoReturnable<List<TagKey<Fluid>>> cir, List<TagKey<Fluid>> list){
        CustomRocket.AD_EXTENDRA_ROCKET_PROPERTIES.values().stream().map(i->i.fuel()).forEach(list::add);
        RLCRockets.ROCKET_PROPERTIES.values().stream().map(i->i.fuel()).forEach(list::add);
    }

}
