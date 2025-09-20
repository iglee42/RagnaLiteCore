package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.mbd2.client.renderer.MBDBESRenderer;
import com.lowdragmc.mbd2.client.renderer.MBDBlockRenderer;
import com.lowdragmc.mbd2.common.machine.MBDMachine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircle;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircleModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MBDBESRenderer.class)
public class MBDBESRendererMixin implements MBDMagicCircleModel {

    @Unique
    private MagicCircleModel rlc$magicCircleModel;

    @Inject(method = "<init>",at = @At(value = "TAIL"))
    private void rlc$initMagicCircleModel(BlockEntityRendererProvider.Context context, CallbackInfo ci){
          rlc$magicCircleModel = new MagicCircleModel(context);
    }

    @Override
    public MagicCircleModel rlc$getMagicCircleModel() {
        return rlc$magicCircleModel;
    }
}
