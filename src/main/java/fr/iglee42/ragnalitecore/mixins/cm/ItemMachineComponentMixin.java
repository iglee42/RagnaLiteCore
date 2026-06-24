package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.iglee42.ragnalitecore.cm.ICMTagUser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ItemMachineComponent.class,remap = false)
public class ItemMachineComponentMixin implements ICMTagUser {

    @Unique
    private String ragnalite$tag = "";


    @Override
    public String ragnalite$getTag() {
        return ragnalite$tag;
    }

    @Override
    public void ragnalite$setTag(String tag) {
        this.ragnalite$tag = tag;
    }
}
