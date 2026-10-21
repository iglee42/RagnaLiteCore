package fr.iglee42.ragnalitecore;

import com.drd.ad_extendra.common.entities.vehicles.CustomRocket;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.entities.mob.SulfurCreeper;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = RagnaLiteCore.MODID)
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

    public static final RegistryEntry<EntityType<SulfurCreeper>> MOON_CREEPER = registerCreeper("moon");
    public static final RegistryEntry<EntityType<SulfurCreeper>> MARS_CREEPER = registerCreeper("mars");
    public static final RegistryEntry<EntityType<SulfurCreeper>> MERCURY_CREEPER = registerCreeper("mercury");
    public static final RegistryEntry<EntityType<SulfurCreeper>> JUPITER_CREEPER = registerCreeper("jupiter");
    public static final RegistryEntry<EntityType<SulfurCreeper>> SATURN_CREEPER = registerCreeper("saturn");
    public static final RegistryEntry<EntityType<SulfurCreeper>> URANUS_CREEPER = registerCreeper("uranus");
    public static final RegistryEntry<EntityType<SulfurCreeper>> NEPTUNE_CREEPER = registerCreeper("neptune");
    public static final RegistryEntry<EntityType<SulfurCreeper>> CERES_CREEPER = registerCreeper("ceres");
    public static final RegistryEntry<EntityType<SulfurCreeper>> PLUTO_CREEPER = registerCreeper("pluto");
    public static final RegistryEntry<EntityType<SulfurCreeper>> HAUMEA_CREEPER = registerCreeper("haumea");
    public static final RegistryEntry<EntityType<SulfurCreeper>> QUAOAR_CREEPER = registerCreeper("quaoar");
    public static final RegistryEntry<EntityType<SulfurCreeper>> MAKEMAKE_CREEPER = registerCreeper("makemake");
    public static final RegistryEntry<EntityType<SulfurCreeper>> ERIS_CREEPER = registerCreeper("eris");
    public static final RegistryEntry<EntityType<SulfurCreeper>> GONGGONG_CREEPER = registerCreeper("gonggong");
    public static final RegistryEntry<EntityType<SulfurCreeper>> SEDNA_CREEPER = registerCreeper("sedna");
    public static final RegistryEntry<EntityType<SulfurCreeper>> ORCUS_CREEPER = registerCreeper("orcus");

    private static RegistryEntry<EntityType<SulfurCreeper>> registerCreeper(String planet) {
        return ENTITY_TYPES.register(planet+"_creeper", () ->
                EntityType.Builder.of(SulfurCreeper::new, MobCategory.MONSTER)
                        .fireImmune()
                        .clientTrackingRange(8)
                        .sized(0.6f, 1.7f)
                        .build(planet+"_creeper"));
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event){
        event.register(MOON_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MARS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MERCURY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(JUPITER_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(SATURN_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(URANUS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(NEPTUNE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(CERES_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(PLUTO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HAUMEA_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(QUAOAR_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MAKEMAKE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ERIS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(GONGGONG_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(SEDNA_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ORCUS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SulfurCreeper::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(EntityAttributeCreationEvent event){
        event.put(MOON_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(MARS_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(MERCURY_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(JUPITER_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(SATURN_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(URANUS_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(NEPTUNE_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(CERES_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(PLUTO_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(HAUMEA_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(QUAOAR_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(MAKEMAKE_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(ERIS_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(GONGGONG_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(SEDNA_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
        event.put(ORCUS_CREEPER.get(), SulfurCreeper.createMobAttributes().build());
    }
}
