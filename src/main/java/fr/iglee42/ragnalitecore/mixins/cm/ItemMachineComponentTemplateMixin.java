package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.common.component.ItemMachineComponent;
import fr.iglee42.ragnalitecore.cm.ICMTagUser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemMachineComponent.Template.class,remap = false)
public class ItemMachineComponentTemplateMixin implements ICMTagUser {

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

    @Inject(method = "build(Lfr/frinn/custommachinery/api/component/IMachineComponentManager;)Lfr/frinn/custommachinery/common/component/ItemMachineComponent;",at = @At("RETURN"),cancellable = true)
    private void ragnalite$setTagToComponent(IMachineComponentManager manager, CallbackInfoReturnable<ItemMachineComponent> cir){
        ItemMachineComponent component = cir.getReturnValue();
        ((ICMTagUser)component).ragnalite$setTag(ragnalite$tag);
        cir.setReturnValue(component);
    }
}
