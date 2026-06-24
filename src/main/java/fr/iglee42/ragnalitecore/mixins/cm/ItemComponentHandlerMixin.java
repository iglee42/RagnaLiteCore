package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.frinn.custommachinery.common.component.handler.ItemComponentHandler;
import fr.frinn.custommachinery.common.util.Utils;
import fr.iglee42.ragnalitecore.cm.ICMNewGetAmount;
import fr.iglee42.ragnalitecore.cm.ICMTagUser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Predicate;

@Mixin(value = ItemComponentHandler.class,remap = false)
public class ItemComponentHandlerMixin implements ICMNewGetAmount {

    @Shadow
    @Final
    private List<ItemMachineComponent> inputs;

    @Override
    public int ragnalitecore$getItemAmount(String slot, Item item, @Nullable CompoundTag nbt, String tag) {
        Predicate<ItemMachineComponent> nbtPredicate = component -> nbt == null || nbt.isEmpty() || (component.getItemStack().getTag() != null && Utils.testNBT(component.getItemStack().getTag(), nbt));
        Predicate<ItemMachineComponent> slotPredicate = component -> slot.isEmpty() || component.getId().equals(slot);
        Predicate<ItemMachineComponent> tagPredicate = component -> tag.isEmpty() || ((ICMTagUser)component).ragnalite$getTag().equals(tag);
        return this.inputs.stream().filter(component -> component.getItemStack().getItem() == item && tagPredicate.test(component) && nbtPredicate.test(component) && slotPredicate.test(component))
                .mapToInt(component -> component.getItemStack().getCount())
                .sum();
    }

}
