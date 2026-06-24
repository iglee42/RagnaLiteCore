package fr.iglee42.ragnalitecore.mixins.cm;

import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientWrapper;
import fr.frinn.custommachinery.common.component.handler.ItemComponentHandler;
import fr.frinn.custommachinery.common.requirement.ItemRequirement;
import fr.iglee42.ragnalitecore.cm.ICMNewGetAmount;
import fr.iglee42.ragnalitecore.cm.ICMTagUser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemRequirement.class,remap = false)
public class ItemRequirementMixin implements ICMTagUser {

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

    @Redirect(method = "lambda$test$6",at= @At(value = "INVOKE", target = "Lfr/frinn/custommachinery/common/component/handler/ItemComponentHandler;getItemAmount(Ljava/lang/String;Lnet/minecraft/world/item/Item;Lnet/minecraft/nbt/CompoundTag;)I"))
    private int ragnalite$redirectGetItemAmountTest(ItemComponentHandler instance, String slot, Item item, CompoundTag nbt){
        return ((ICMNewGetAmount)instance).ragnalitecore$getItemAmount(slot,item,nbt,ragnalite$tag);
    }

    @Redirect(method = "lambda$processStart$7",at= @At(value = "INVOKE", target = "Lfr/frinn/custommachinery/common/component/handler/ItemComponentHandler;getItemAmount(Ljava/lang/String;Lnet/minecraft/world/item/Item;Lnet/minecraft/nbt/CompoundTag;)I"))
    private int ragnalite$redirectGetItemAmountMaxExtract(ItemComponentHandler instance, String slot, Item item, CompoundTag nbt){
        return ((ICMNewGetAmount)instance).ragnalitecore$getItemAmount(slot,item,nbt,ragnalite$tag);
    }

    @Redirect(method = "processStart(Lfr/frinn/custommachinery/common/component/handler/ItemComponentHandler;Lfr/frinn/custommachinery/api/crafting/ICraftingContext;)Lfr/frinn/custommachinery/api/crafting/CraftingResult;",at= @At(value = "INVOKE", target = "Lfr/frinn/custommachinery/common/component/handler/ItemComponentHandler;getItemAmount(Ljava/lang/String;Lnet/minecraft/world/item/Item;Lnet/minecraft/nbt/CompoundTag;)I"))
    private int ragnalite$redirectGetItemAmountProcessStart(ItemComponentHandler instance, String slot, Item item, CompoundTag nbt){
        return ((ICMNewGetAmount)instance).ragnalitecore$getItemAmount(slot,item,nbt,ragnalite$tag);
    }

    @Inject(method = "getJEIIngredientWrappers", at = @At("RETURN"), cancellable = true)
    private void ragnalite$setTagToJEIWrappers(IMachineRecipe recipe, CallbackInfoReturnable<List<IJEIIngredientWrapper<ItemStack>>> cir) {
        List<IJEIIngredientWrapper<ItemStack>> wrappers = cir.getReturnValue();
        for (IJEIIngredientWrapper<ItemStack> wrapper : wrappers) {
            if (wrapper instanceof ICMTagUser tagUser) {
                tagUser.ragnalite$setTag(ragnalite$tag);
            }
        }
        cir.setReturnValue(wrappers);
    }
}
