package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.common.requirement.ItemRequirement;
import fr.frinn.custommachinery.common.util.ingredient.IIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRequirement.class)
public interface ItemRequirementAccessor {

    @Accessor("item")
    IIngredient<Item> ragnalite$getItem();

    @Accessor("slot")
    String ragnalite$getSlot();

    @Accessor("nbt")
    @Nullable CompoundTag ragnalite$getNbt();

    @Accessor("amount")
    int ragnalite$getAmount();
}
