package fr.iglee42.ragnalitecore.mixins;

import com.lowdragmc.lowdraglib.gui.editor.annotation.Configurable;
import com.lowdragmc.mbd2.common.machine.definition.MBDMachineDefinition;
import com.lowdragmc.mbd2.common.machine.definition.config.ConfigBlockProperties;
import com.lowdragmc.mbd2.common.machine.definition.config.ConfigItemProperties;
import com.lowdragmc.mbd2.common.machine.definition.config.ConfigRecipeLogicSettings;
import com.lowdragmc.mbd2.common.machine.definition.config.MachineState;
import fr.iglee42.ragnalitecore.utils.MBDConfigRitualCircleProperties;
import fr.iglee42.ragnalitecore.utils.MBDRitualCircleDefinition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MBDMachineDefinition.class,remap = false)
public class MBDMachineDefinitionMixin implements MBDRitualCircleDefinition {

    @Unique
    @Nullable
    @Configurable(name = "config.definition.ritualCircle", subConfigurable = true, collapse = false)
    private MBDConfigRitualCircleProperties rlc$ritualCircleProperties;

    @Inject(method = "<init>",at = @At("RETURN"))
    private void rlc$addRitualCircle(ResourceLocation id, MachineState rootState, ConfigBlockProperties blockProperties, ConfigItemProperties itemProperties, MBDMachineDefinition.ConfigMachineSettingsFactory machineSettingsFactory, ConfigRecipeLogicSettings recipeLogicSettings, MBDMachineDefinition.ConfigPartSettingsFactory partSettingsFactory, CallbackInfo ci){
        rlc$ritualCircleProperties = MBDConfigRitualCircleProperties.builder().build();
    }

    @Inject(method = "lambda$loadProductiveTag$5",at = @At("RETURN"))
    private void rlc$loadRitualCircle(CompoundTag definitionTag, CompoundTag projectTag, CallbackInfo ci){
        rlc$ritualCircleProperties.deserializeNBT(definitionTag.getCompound("rlc$ritualCircleProperties"));
    }

    @Override
    public MBDConfigRitualCircleProperties rlc$getRitualCircleProperties() {
        return rlc$ritualCircleProperties;
    }
}