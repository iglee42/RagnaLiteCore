package fr.iglee42.ragnalitecore;

import earth.terrarium.adastra.common.entities.multipart.MultipartPartEntity;
import earth.terrarium.adastra.common.entities.vehicles.Rocket.RocketProperties;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class RLCRockets {
    private static final RocketProperties TIER_12_PROPERTIES = new RocketProperties(12, RLCItems.TIER_12_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_12_FUEL);
    private static final RocketProperties TIER_13_PROPERTIES = new RocketProperties(13, RLCItems.TIER_13_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_13_FUEL);
    private static final RocketProperties TIER_14_PROPERTIES = new RocketProperties(14, RLCItems.TIER_14_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_14_FUEL);
    private static final RocketProperties TIER_15_PROPERTIES = new RocketProperties(15, RLCItems.TIER_15_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_15_FUEL);
    private static final RocketProperties TIER_16_PROPERTIES = new RocketProperties(16, RLCItems.TIER_16_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_16_FUEL);
    private static final RocketProperties TIER_17_PROPERTIES = new RocketProperties(17, RLCItems.TIER_17_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_17_FUEL);
    private static final RocketProperties TIER_18_PROPERTIES = new RocketProperties(18, RLCItems.TIER_18_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_18_FUEL);
    private static final RocketProperties TIER_19_PROPERTIES = new RocketProperties(19, RLCItems.TIER_19_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_19_FUEL);
    private static final RocketProperties TIER_20_PROPERTIES = new RocketProperties(20, RLCItems.TIER_20_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_20_FUEL);
    private static final RocketProperties TIER_21_PROPERTIES = new RocketProperties(21, RLCItems.TIER_21_ROCKET.get(), 2.2f, RLCTags.Fluids.TIER_21_FUEL);

    public static final Map<EntityType<?>, RocketProperties> ROCKET_PROPERTIES = Map.of(
            RLCEntities.TIER_12_ROCKET.get(), TIER_12_PROPERTIES,
            RLCEntities.TIER_13_ROCKET.get(), TIER_13_PROPERTIES,
            RLCEntities.TIER_14_ROCKET.get(), TIER_14_PROPERTIES,
            RLCEntities.TIER_15_ROCKET.get(), TIER_15_PROPERTIES,
            RLCEntities.TIER_16_ROCKET.get(), TIER_16_PROPERTIES,
            RLCEntities.TIER_17_ROCKET.get(), TIER_17_PROPERTIES,
            RLCEntities.TIER_18_ROCKET.get(), TIER_18_PROPERTIES,
            RLCEntities.TIER_19_ROCKET.get(), TIER_19_PROPERTIES,
            RLCEntities.TIER_20_ROCKET.get(), TIER_20_PROPERTIES,
            RLCEntities.TIER_21_ROCKET.get(), TIER_21_PROPERTIES
            );


}