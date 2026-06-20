package fr.iglee42.ragnalitecore.mixins;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.client.dimension.ModSkyRenderer;
import fr.iglee42.ragnalitecore.rift.ClientRiftManager;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = ModSkyRenderer.class,remap = false)
public class AdAstraSkyRendererMixin {

    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;blendFuncSeparate(Lcom/mojang/blaze3d/platform/GlStateManager$SourceFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DestFactor;Lcom/mojang/blaze3d/platform/GlStateManager$SourceFactor;Lcom/mojang/blaze3d/platform/GlStateManager$DestFactor;)V",shift = At.Shift.AFTER),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void ragnalite$renderRift(ClientLevel level, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog, CallbackInfo ci, BufferBuilder bufferBuilder){
        if (level != null) ClientRiftManager.renderRift(poseStack,bufferBuilder,level);
    }
}
