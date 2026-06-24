package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.frinn.custommachinery.common.init.Registration;
import fr.frinn.custommachinery.common.requirement.ItemRequirement;
import fr.iglee42.ragnalitecore.cm.CMNewCodecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Registration.class,remap = false)
public class RegistrationMixin {

    @Redirect(method = "lambda$static$71", at = @At(value = "INVOKE", target = "Lfr/frinn/custommachinery/api/requirement/RequirementType;inventory(Lfr/frinn/custommachinery/api/codec/NamedCodec;)Lfr/frinn/custommachinery/api/requirement/RequirementType;"))
    private static RequirementType<ItemRequirement> ragnalite$registerItemRequirement(NamedCodec<ItemRequirement> codec) {
        return RequirementType.inventory(CMNewCodecs.ITEM_REQ_CODEC);
    }

    @Redirect(method = "lambda$static$44", at = @At(value = "INVOKE", target = "Lfr/frinn/custommachinery/api/component/MachineComponentType;create(Lfr/frinn/custommachinery/api/codec/NamedCodec;)Lfr/frinn/custommachinery/api/component/MachineComponentType;"))
    private static MachineComponentType<ItemMachineComponent> ragnalite$registerItemComponent(NamedCodec<ItemMachineComponent> codec) {
        return MachineComponentType.create(CMNewCodecs.ITEM_TEMPLATE_CODEC);
    }
}
