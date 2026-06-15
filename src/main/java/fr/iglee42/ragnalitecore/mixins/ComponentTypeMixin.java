package fr.iglee42.ragnalitecore.mixins;

import fr.iglee42.ragnalitecore.bloodmagic.BloodMagicCustomisationConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wayoftime.bloodmagic.altar.ComponentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

@Mixin(value = ComponentType.class,remap = false)
public class ComponentTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static ComponentType[] $VALUES;

    @Invoker("<init>")
    public static ComponentType rmlite$init(String internalName, int internalId) {
        throw new AssertionError();
    }

    @Unique
    private static ComponentType rmlite$addVariant(String internalName) {
        ArrayList<ComponentType> variants = new ArrayList<>(Arrays.asList($VALUES));
        ComponentType casing = rmlite$init(internalName,
                variants.get(variants.size() - 1).ordinal() + 1);
        variants.add(casing);
        ComponentTypeMixin.$VALUES = variants.toArray(new ComponentType[0]);
        return casing;
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void rmlite$addCustomComponentTypes(CallbackInfo ci) {
        if (BloodMagicCustomisationConfig.configLoaded){
            BloodMagicCustomisationConfig.componentTypes.keySet().stream().filter(Predicate.not(BloodMagicCustomisationConfig::isVanillaComponentType)).forEach(s->rmlite$addVariant(s.toUpperCase()));
        }
    }



}
