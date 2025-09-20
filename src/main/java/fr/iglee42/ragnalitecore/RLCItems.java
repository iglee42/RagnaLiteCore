package fr.iglee42.ragnalitecore;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.common.items.vehicles.RocketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
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


    private static RegistryEntry<Item> registerRocket(int tier, Supplier<EntityType<?>> entity) {
        return ITEMS.register("tier_" + tier + "_rocket", () -> new RocketItem(entity, new Item.Properties().stacksTo(1).fireResistant()));
    }
}
