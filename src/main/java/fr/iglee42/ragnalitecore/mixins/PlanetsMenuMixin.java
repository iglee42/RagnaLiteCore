package fr.iglee42.ragnalitecore.mixins;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import fr.iglee42.ragnalitecore.RLCPlanets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = PlanetsMenu.class,remap = false)
public abstract class PlanetsMenuMixin {

    @Shadow
    public abstract List<Pair<String, SpaceStation>> getOwnedAndTeamSpaceStations(ResourceKey<Level> dimension);

    @Inject(method = "canConstruct", at =@At("RETURN"),cancellable = true)
    private void rlc$disableIfStationAlreadyExists(ResourceKey<Level> dimension, CallbackInfoReturnable<Boolean> cir) {
        boolean returned = cir.getReturnValue();
        if (dimension.equals(RLCPlanets.SUN_ORBIT)){
            cir.setReturnValue(returned && getOwnedAndTeamSpaceStations(dimension).isEmpty());
        }
    }
}
