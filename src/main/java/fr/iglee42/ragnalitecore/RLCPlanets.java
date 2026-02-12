package fr.iglee42.ragnalitecore;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class RLCPlanets {
    public static final ResourceKey<Level> SUN = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(RagnaLiteCore.RAGNA_LITE, "sun"));
    public static final ResourceKey<Level> SUN_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(RagnaLiteCore.RAGNA_LITE, "sun_orbit"));
}
