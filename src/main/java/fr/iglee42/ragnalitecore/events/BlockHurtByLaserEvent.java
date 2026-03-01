package fr.iglee42.ragnalitecore.events;

import lombok.Getter;
import mekanism.api.math.FloatingLong;
import mekanism.common.tile.laser.TileEntityBasicLaser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Getter
public class BlockHurtByLaserEvent extends BlockEvent{
    private final TileEntityBasicLaser laser;
    private final FloatingLong attackEnergy;

    public BlockHurtByLaserEvent(LevelAccessor level, BlockPos pos, BlockState state, TileEntityBasicLaser laser, FloatingLong attackEnergy) {
        super(level,pos,state);
        this.laser = laser;
        this.attackEnergy = attackEnergy;
    }

}
