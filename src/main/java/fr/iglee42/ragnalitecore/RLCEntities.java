package fr.iglee42.ragnalitecore;

import com.drd.ad_extendra.common.entities.vehicles.CustomRocket;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class RLCEntities {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, RagnaLiteCore.MODID);

    public static final RegistryEntry<EntityType<Rocket>> TIER_12_ROCKET = registerRocket(12, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_13_ROCKET = registerRocket(13, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_14_ROCKET = registerRocket(14, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_15_ROCKET = registerRocket(15, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_16_ROCKET = registerRocket(16, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_17_ROCKET = registerRocket(17, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_18_ROCKET = registerRocket(18, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_19_ROCKET = registerRocket(19, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_20_ROCKET = registerRocket(20, 1.1f, 9.0f);
    public static final RegistryEntry<EntityType<Rocket>> TIER_21_ROCKET = registerRocket(21, 1.1f, 9.0f);
    private static RegistryEntry<EntityType<Rocket>> registerRocket(int tier, float width, float height) {
        return ENTITY_TYPES.register("tier_" + tier + "_rocket", () ->
                EntityType.Builder.<Rocket>of((type,level)->new Rocket(type,level,RLCRockets.ROCKET_PROPERTIES.get(type)), MobCategory.MISC)
                        .fireImmune()
                        .clientTrackingRange(10)
                        .sized(width, height)
                        .build("tier_" + tier + "_rocket"));
    }
}
