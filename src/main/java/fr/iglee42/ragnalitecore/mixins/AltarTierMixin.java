package fr.iglee42.ragnalitecore.mixins;

import fr.iglee42.ragnalitecore.bloodmagic.BloodMagicCustomisationConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wayoftime.bloodmagic.altar.AltarComponent;
import wayoftime.bloodmagic.altar.AltarTier;

import java.util.Map;
import java.util.function.Consumer;

@Mixin(value = AltarTier.class,remap = false)
public class AltarTierMixin {

    @Shadow
    @Final
    private static AltarTier[] $VALUES;

    @Redirect(method = "<init>",at = @At(value = "INVOKE", target = "Lwayoftime/bloodmagic/altar/AltarTier;buildComponents(Ljava/util/function/Consumer;)V"))
    private void ragnalite$modifyComponents(AltarTier instance, Consumer<AltarComponent> altarComponentConsumer){
        if (!BloodMagicCustomisationConfig.configLoaded) {
            instance.buildComponents(altarComponentConsumer);
            return;
        }
        int tier = instance.ordinal() + 1;
        if (!BloodMagicCustomisationConfig.tiers.containsKey(tier)){
            instance.buildComponents(altarComponentConsumer);
            return;
        }
        BloodMagicCustomisationConfig.addAltarComponents(tier,altarComponentConsumer);
    }

}
