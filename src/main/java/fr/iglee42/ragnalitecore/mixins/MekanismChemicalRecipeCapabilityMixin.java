package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.mbd2.integration.mekanism.MekanismChemicalRecipeCapability;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.IChemicalTank;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MekanismChemicalRecipeCapability.class,remap = false)
public class MekanismChemicalRecipeCapabilityMixin<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, TANK extends IChemicalTank<CHEMICAL, STACK>> {

    @Redirect(method = "createPreviewWidget(Lmekanism/api/chemical/ChemicalStack;)Lcom/lowdragmc/lowdraglib/gui/widget/Widget;",at = @At(value = "INVOKE", target = "Lmekanism/api/chemical/ChemicalTankBuilder;create(JLmekanism/api/IContentsListener;)Lmekanism/api/chemical/IChemicalTank;"))
    private TANK rlc$allowAllChemicals(ChemicalTankBuilder<CHEMICAL,STACK,TANK> tankBuilder, long capacity, @Nullable IContentsListener listener){
        return tankBuilder.createAllValid(capacity,listener);
    }

    @Redirect(method = "bindXEIWidget",at = @At(value = "INVOKE", target = "Lmekanism/api/chemical/ChemicalTankBuilder;create(JLmekanism/api/IContentsListener;)Lmekanism/api/chemical/IChemicalTank;"))
    private TANK rlc$allowAllChemicalsInXEI(ChemicalTankBuilder<CHEMICAL,STACK,TANK> tankBuilder, long capacity, @Nullable IContentsListener listener){
        return tankBuilder.createAllValid(capacity,listener);
    }

}
