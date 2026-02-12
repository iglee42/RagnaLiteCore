package fr.iglee42.ragnalitecore.mixins;

import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import fr.iglee42.ragnalitecore.RLCPlanets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = PlanetsScreen.class,remap = false)
public abstract class PlanetsScreenMixin {

    @Shadow
    private @Nullable Planet selectedPlanet;

    @Shadow
    protected abstract void addSpaceStationButtons(ResourceKey<Level> dimension);

    @Inject(method = "createSelectedPlanetButtons", at =@At("HEAD"),cancellable = true)
    private void rlc$createSelectedPlanetButtons(CallbackInfo ci) {
        if (selectedPlanet != null && selectedPlanet.dimension().equals(RLCPlanets.SUN)){
            this.addSpaceStationButtons(this.selectedPlanet.orbitIfPresent());
            ci.cancel();
        }
    }

    @Inject(method = "getSpaceStationRecipeTooltip", at = @At(value = "INVOKE", target = "Learth/terrarium/adastra/common/menus/PlanetsMenu;getLandingPos(Lnet/minecraft/resources/ResourceKey;Z)Lnet/minecraft/core/BlockPos;"),cancellable = true,locals = LocalCapture.CAPTURE_FAILSOFT)
    private void rlc$tooltipForExistingSunStation(ResourceKey<Level> planet, CallbackInfoReturnable<Tooltip> cir, List tooltip) {
        if (planet.equals(RLCPlanets.SUN_ORBIT)) {
            if (!((PlanetsScreen) (Object) this).getMenu().getOwnedAndTeamSpaceStations(planet).isEmpty()){
                cir.setReturnValue(Tooltip.create(Component.translatable("tooltip.ragnalitecore.sun_station_already_exists").withStyle(ChatFormatting.RED)));
            }
        }
    }
}
