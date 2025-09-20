package fr.iglee42.ragnalitecore;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class RLCTags {
    public static class Fluids{
        public static final TagKey<Fluid> TIER_12_FUEL = tag("tier_12_rocket_fuel");
        public static final TagKey<Fluid> TIER_13_FUEL = tag("tier_13_rocket_fuel");
        public static final TagKey<Fluid> TIER_14_FUEL = tag("tier_14_rocket_fuel");
        public static final TagKey<Fluid> TIER_15_FUEL = tag("tier_15_rocket_fuel");
        public static final TagKey<Fluid> TIER_16_FUEL = tag("tier_16_rocket_fuel");
        public static final TagKey<Fluid> TIER_17_FUEL = tag("tier_17_rocket_fuel");
        public static final TagKey<Fluid> TIER_18_FUEL = tag("tier_18_rocket_fuel");
        public static final TagKey<Fluid> TIER_19_FUEL = tag("tier_19_rocket_fuel");
        public static final TagKey<Fluid> TIER_20_FUEL = tag("tier_20_rocket_fuel");
        public static final TagKey<Fluid> TIER_21_FUEL = tag("tier_21_rocket_fuel");
        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registries.FLUID, new ResourceLocation(RagnaLiteCore.MODID, name));
        }
    }
}
