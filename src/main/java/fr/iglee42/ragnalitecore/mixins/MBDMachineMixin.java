package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.mbd2.api.machine.IMachine;
import com.lowdragmc.mbd2.api.recipe.MBDRecipe;
import com.lowdragmc.mbd2.common.machine.MBDMachine;
import com.lowdragmc.mbd2.common.machine.definition.MBDMachineDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import fr.iglee42.ragnalitecore.RLCPackets;
import fr.iglee42.ragnalitecore.packets.CreateMagicCircleS2C;
import fr.iglee42.ragnalitecore.packets.DeleteMagicCircleS2C;
import fr.iglee42.ragnalitecore.utils.MBDMagicCircle;
import fr.iglee42.ragnalitecore.utils.MBDRitualCircleDefinition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MBDMachine.class,remap = false)
public abstract class MBDMachineMixin implements MBDMagicCircle {

    @Shadow @Final private MBDMachineDefinition definition;


    @Unique
    @Nullable
    private MagicCircle rlc$magicCircle;

    @Inject(method = "clientTick",at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"))
    private void rlc$clientTick(CallbackInfo ci){
        if (rlc$getMagicCircle() != null){
            rlc$getMagicCircle().tick();
        }
    }

    @Inject(method = "beforeWorking",at = @At(value = "HEAD"))
    private void rlc$beforeWorking(MBDRecipe recipe, CallbackInfoReturnable<Boolean> cir){
        if (definition instanceof MBDRitualCircleDefinition def){
            if (def.rlc$getRitualCircleProperties().useRitualCircle()){
                RLCPackets.sendToClients(new CreateMagicCircleS2C(((IMachine)this).getPos(),def.rlc$getRitualCircleProperties().innerCircleTexture(),def.rlc$getRitualCircleProperties().outerCircleTexture(),def.rlc$getRitualCircleProperties().offset()));
            }
        }
    }

    @Inject(method = "afterWorking",at = @At(value = "HEAD"))
    private void rlc$afterWorking(CallbackInfo ci){
        if (definition instanceof MBDRitualCircleDefinition def){
            if (def.rlc$getRitualCircleProperties().useRitualCircle()){
                RLCPackets.sendToClients(new DeleteMagicCircleS2C(((IMachine)this).getPos()));
            }
        }
    }



    @Override
    public @Nullable MagicCircle rlc$getMagicCircle() {
        return rlc$magicCircle;
    }

    @Override
    public void rlc$setMagicCircle(@Nullable MagicCircle magicCircle) {
        this.rlc$magicCircle = magicCircle;
    }
}
