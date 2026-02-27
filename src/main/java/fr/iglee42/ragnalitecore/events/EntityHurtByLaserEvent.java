package fr.iglee42.ragnalitecore.events;

import lombok.Getter;
import mekanism.api.math.FloatingLong;
import mekanism.common.tile.laser.TileEntityBasicLaser;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

@Getter
public class EntityHurtByLaserEvent extends EntityEvent {
    private final TileEntityBasicLaser laser;
    private final FloatingLong attackEnergy;

    public EntityHurtByLaserEvent(Entity entity, TileEntityBasicLaser laser,FloatingLong attackEnergy) {
        super(entity);
        this.laser = laser;
        this.attackEnergy = attackEnergy;
    }

}
