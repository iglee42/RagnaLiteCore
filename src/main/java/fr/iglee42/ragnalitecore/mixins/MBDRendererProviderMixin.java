package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.mbd2.client.renderer.MBDBESRenderer;
import com.lowdragmc.mbd2.client.renderer.MBDBlockRenderer;
import com.lowdragmc.mbd2.common.machine.MBDMachine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircle;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircleModel;
import fr.iglee42.ragnalitecore.utils.MBDRitualCircleDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MBDBlockRenderer.class)
public class MBDRendererProviderMixin {

    @Inject(method = "lambda$render$14",at = @At(value = "TAIL"))
    private static void rlc$addRitualCircleRender(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, MBDMachine machine, CallbackInfo ci){
        if (Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(blockEntity) instanceof MBDMagicCircleModel renderer){
             if (machine instanceof MBDMagicCircle circle && circle.rlc$getMagicCircle() != null && machine.getDefinition() instanceof MBDRitualCircleDefinition def) {
                 stack.pushPose();
                 BlockPos offset = def.rlc$getRitualCircleProperties().offset();
                 stack.translate(offset.getX(),offset.getY(),offset.getZ());
                 circle.rlc$getMagicCircle().render(stack, partialTicks, buffer, combinedLight,renderer.rlc$getMagicCircleModel());
                 stack.popPose();
             }
        }

    }

}
