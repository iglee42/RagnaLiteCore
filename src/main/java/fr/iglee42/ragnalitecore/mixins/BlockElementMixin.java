package fr.iglee42.ragnalitecore.mixins;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockElement;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockElement.Deserializer.class)
public abstract class BlockElementMixin {

    @Shadow
    protected abstract Vector3f getVector3f(JsonObject p_111335_, String p_111336_);

    @Inject(method = "getTo",at = @At(value = "HEAD"),cancellable = true)
    private void ragnalite$allowBypassToLimits(JsonObject object, CallbackInfoReturnable<Vector3f> cir){
        if (object.has("override_limits")){
            if (object.get("override_limits").getAsBoolean()){
                cir.setReturnValue(getVector3f(object,"to"));
            }
        }
    }

    @Inject(method = "getFrom",at = @At(value = "HEAD"),cancellable = true)
    private void ragnalite$allowBypassFromLimits(JsonObject object, CallbackInfoReturnable<Vector3f> cir){
        if (object.has("override_limits")){
            if (object.get("override_limits").getAsBoolean()){
                cir.setReturnValue(getVector3f(object,"from"));
            }
        }
    }
}