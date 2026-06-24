package fr.iglee42.ragnalitecore.cm;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.variant.IComponentVariant;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.frinn.custommachinery.common.component.variant.item.DefaultItemComponentVariant;
import fr.frinn.custommachinery.common.init.Registration;
import fr.frinn.custommachinery.common.requirement.ItemRequirement;
import fr.frinn.custommachinery.common.util.ingredient.IIngredient;
import fr.frinn.custommachinery.impl.codec.DefaultCodecs;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import fr.frinn.custommachinery.impl.component.variant.ItemComponentVariant;
import fr.frinn.custommachinery.impl.requirement.AbstractChanceableRequirement;
import fr.frinn.custommachinery.impl.requirement.AbstractRequirement;
import fr.iglee42.ragnalitecore.mixins.cm.ItemRequirementAccessor;

import java.util.Collections;
import java.util.Optional;

public class CMNewCodecs {

    public static final NamedCodec<ItemRequirement> ITEM_REQ_CODEC = NamedCodec.record(itemRequirementInstance ->
            itemRequirementInstance.group(
                    RequirementIOMode.CODEC.fieldOf("mode").forGetter(AbstractRequirement::getMode),
                    IIngredient.ITEM.fieldOf("item").forGetter(requirement -> ((ItemRequirementAccessor) requirement).ragnalite$getItem()),
                    NamedCodec.INT.fieldOf("amount").forGetter(requirement -> ((ItemRequirementAccessor) requirement).ragnalite$getAmount()),
                    DefaultCodecs.COMPOUND_TAG.optionalFieldOf("nbt").forGetter(requirement -> Optional.ofNullable(((ItemRequirementAccessor) requirement).ragnalite$getNbt())),
                    NamedCodec.doubleRange(0.0, 1.0).optionalFieldOf("chance", 1.0D).forGetter(AbstractChanceableRequirement::getChance),
                    NamedCodec.STRING.optionalFieldOf("slot", "").forGetter(requirement -> ((ItemRequirementAccessor) requirement).ragnalite$getSlot()),
                    NamedCodec.STRING.optionalFieldOf("tag", "").forGetter(req -> ((ICMTagUser) req).ragnalite$getTag())
            ).apply(itemRequirementInstance, (mode, item, amount, nbt, chance, slot, tag) -> {
                ItemRequirement requirement = new ItemRequirement(mode, item, amount, nbt.orElse(null), slot);
                requirement.setChance(chance);
                ((ICMTagUser) requirement).ragnalite$setTag(tag);
                return requirement;
            }), "Item requirement"
    );


    public static final NamedCodec<ItemMachineComponent.Template> ITEM_TEMPLATE_CODEC = NamedCodec.record(itemMachineComponentTemplate ->
            itemMachineComponentTemplate.group(
                    NamedCodec.STRING.fieldOf("id").forGetter(template -> template.id()),
                    ComponentIOMode.CODEC.optionalFieldOf("mode", ComponentIOMode.BOTH).forGetter(template -> template.mode()),
                    NamedCodec.INT.optionalFieldOf("capacity", 64).forGetter(template -> template.capacity()),
                    NamedCodec.INT.optionalFieldOf("max_input").forGetter(template -> template.maxInput() == template.capacity() ? Optional.empty() : Optional.of(template.maxInput())),
                    NamedCodec.INT.optionalFieldOf("max_output").forGetter(template -> template.maxOutput() == template.capacity() ? Optional.empty() : Optional.of(template.maxOutput())),
                    IIngredient.ITEM.listOf().optionalFieldOf("filter", Collections.emptyList()).forGetter(template -> template.filter()),
                    NamedCodec.BOOL.optionalFieldOf("whitelist", false).forGetter(template -> template.whitelist()),
                    IComponentVariant.codec(Registration.ITEM_MACHINE_COMPONENT).orElse(DefaultItemComponentVariant.INSTANCE).forGetter(template -> template.variant()),
                    SideConfig.Template.CODEC.optionalFieldOf("config").forGetter(template -> template.config() == template.mode().getBaseConfig() ? Optional.empty() : Optional.of(template.config())),
                    NamedCodec.BOOL.optionalFieldOf("locked", false).aliases("lock").forGetter(template -> template.locked()),
                    NamedCodec.STRING.optionalFieldOf("tag","").forGetter(template -> ((ICMTagUser) (Object) template).ragnalite$getTag())
            ).apply(itemMachineComponentTemplate, (id, mode, capacity, maxInput, maxOutput, filter, whitelist, variant, config, locked, tag) -> {

                        ItemMachineComponent.Template template = new ItemMachineComponent.Template(id, mode, capacity, maxInput.orElse(capacity), maxOutput.orElse(capacity), filter, whitelist, (ItemComponentVariant) variant, config.orElse(mode.getBaseConfig()), locked);
                        ((ICMTagUser) (Object) template).ragnalite$setTag(tag);
                        return template;
                    }
            ), "Item machine component"
    );

}
