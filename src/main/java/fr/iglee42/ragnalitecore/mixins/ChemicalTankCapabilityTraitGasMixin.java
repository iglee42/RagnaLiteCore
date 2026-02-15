package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.mbd2.integration.mekanism.trait.chemical.ChemicalTankCapabilityTrait;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = ChemicalTankCapabilityTrait.Gas.class,remap = false)
public class ChemicalTankCapabilityTraitGasMixin {

    @ModifyArg(method = "createStorage",at = @At(value = "INVOKE", target = "Lcom/lowdragmc/mbd2/integration/mekanism/trait/chemical/ChemicalStorage$Gas;<init>(JLjava/util/function/Predicate;Lmekanism/api/chemical/attribute/ChemicalAttributeValidator;)V"),index = 2)
    private ChemicalAttributeValidator rlc$allowRadioactiveGases(@Nullable ChemicalAttributeValidator attributeValidator){
        return ChemicalAttributeValidator.ALWAYS_ALLOW;
    }
}
