package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.api.component.IMachineComponentTemplate;
import fr.frinn.custommachinery.api.integration.jei.IRecipeHelper;
import fr.frinn.custommachinery.client.integration.jei.wrapper.ItemIngredientWrapper;
import fr.iglee42.ragnalitecore.cm.ICMTagUser;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemIngredientWrapper.class,remap = false)
public class ItemIngredientWrapperMixin implements ICMTagUser {

    @Unique
    private String ragnalite$tag = "";

    @Inject(method = "lambda$setupRecipe$1", at = @At("RETURN"), cancellable = true)
    private void ragnalite$dontPutIfNotTag(List<ItemStack> ingredients, IRecipeHelper helper, IMachineComponentTemplate<?> t, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(cir.getReturnValue() && (ragnalite$tag.isEmpty() || ((ICMTagUser)t).ragnalite$getTag().equals(ragnalite$tag)));
    }

    @Override
    public String ragnalite$getTag() {
        return ragnalite$tag;
    }

    @Override
    public void ragnalite$setTag(String tag) {
        this.ragnalite$tag = tag;
    }
}
