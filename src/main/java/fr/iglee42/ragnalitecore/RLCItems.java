package fr.iglee42.ragnalitecore;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.items.vehicles.RocketItem;
import earth.terrarium.adastra.common.utils.PlatformUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RLCItems {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, RagnaLiteCore.MODID);

    // Rockets
    public static final RegistryEntry<Item> TIER_12_ROCKET = registerRocket(12, RLCEntities.TIER_12_ROCKET::get);
    public static final RegistryEntry<Item> TIER_13_ROCKET = registerRocket(13, RLCEntities.TIER_13_ROCKET::get);
    public static final RegistryEntry<Item> TIER_14_ROCKET = registerRocket(14, RLCEntities.TIER_14_ROCKET::get);
    public static final RegistryEntry<Item> TIER_15_ROCKET = registerRocket(15, RLCEntities.TIER_15_ROCKET::get);
    public static final RegistryEntry<Item> TIER_16_ROCKET = registerRocket(16, RLCEntities.TIER_16_ROCKET::get);
    public static final RegistryEntry<Item> TIER_17_ROCKET = registerRocket(17, RLCEntities.TIER_17_ROCKET::get);
    public static final RegistryEntry<Item> TIER_18_ROCKET = registerRocket(18, RLCEntities.TIER_18_ROCKET::get);
    public static final RegistryEntry<Item> TIER_19_ROCKET = registerRocket(19, RLCEntities.TIER_19_ROCKET::get);
    public static final RegistryEntry<Item> TIER_20_ROCKET = registerRocket(20, RLCEntities.TIER_20_ROCKET::get);
    public static final RegistryEntry<Item> TIER_21_ROCKET = registerRocket(21, RLCEntities.TIER_21_ROCKET::get);

    public static final RegistryEntry<Item> MOON_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("moon", RLCEntities.MOON_CREEPER);
    public static final RegistryEntry<Item> MARS_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("mars", RLCEntities.MARS_CREEPER);
    public static final RegistryEntry<Item> MERCURY_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("mercury", RLCEntities.MERCURY_CREEPER);
    public static final RegistryEntry<Item> JUPITER_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("jupiter", RLCEntities.JUPITER_CREEPER);
    public static final RegistryEntry<Item> SATURN_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("saturn", RLCEntities.SATURN_CREEPER);
    public static final RegistryEntry<Item> URANUS_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("uranus", RLCEntities.URANUS_CREEPER);
    public static final RegistryEntry<Item> NEPTUNE_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("neptune", RLCEntities.NEPTUNE_CREEPER);
    public static final RegistryEntry<Item> CERES_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("ceres", RLCEntities.CERES_CREEPER);
    public static final RegistryEntry<Item> PLUTO_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("pluto", RLCEntities.PLUTO_CREEPER);
    public static final RegistryEntry<Item> HAUMEA_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("haumea", RLCEntities.HAUMEA_CREEPER);
    public static final RegistryEntry<Item> QUAOAR_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("quaoar", RLCEntities.QUAOAR_CREEPER);
    public static final RegistryEntry<Item> MAKEMAKE_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("makemake", RLCEntities.MAKEMAKE_CREEPER);
    public static final RegistryEntry<Item> ERIS_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("eris", RLCEntities.ERIS_CREEPER);
    public static final RegistryEntry<Item> GONGGONG_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("gonggong", RLCEntities.GONGGONG_CREEPER);
    public static final RegistryEntry<Item> SEDNA_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("sedna", RLCEntities.SEDNA_CREEPER);
    public static final RegistryEntry<Item> ORCUS_CREEPER_SPAWN_EGG = createCreeperSpawnEgg("orcus", RLCEntities.ORCUS_CREEPER);

    private static RegistryEntry<Item> registerRocket(int tier, Supplier<EntityType<?>> entity) {
        return ITEMS.register("tier_" + tier + "_rocket", () -> new RocketItem(entity, new Item.Properties().stacksTo(1).fireResistant()));
    }

    private static RegistryEntry<Item> createCreeperSpawnEgg(String planet, Supplier<? extends EntityType<? extends Mob>> type){
        return ITEMS.register(planet + "_creeper_spawn_egg", PlatformUtils.createSpawnEggItem(type, 0xd48f30, 0xac791c, new Item.Properties()));
    }
}
