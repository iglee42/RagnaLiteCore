package fr.iglee42.ragnalitecore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ShockwaveUtil {

    public static void launchBlocks(Level level, BlockPos center, int radius, int height) {

        if (level.isClientSide())
            return;

        RandomSource random = level.getRandom();

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -height, -radius),
                center.offset(radius, -1, radius)
        )) {

            BlockState state = level.getBlockState(pos);

            if (state.isAir())
                continue;

            if (state.hasBlockEntity())
                continue;

            if (state.getDestroySpeed(level, pos) < 0)
                continue;

            if (state.is(Blocks.BEDROCK))
                continue;

            level.setBlock(pos,state.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
            if (random.nextFloat() < 0.7f) {
                continue;
            }

            FallingBlockEntity entity = new FallingBlockEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D, pos.getZ() + 0.5D, state);

            entity.time = 1;

            double dx = pos.getX() + 0.5 - center.getX();
            double dz = pos.getZ() + 0.5 - center.getZ();

            double distance = Math.sqrt(dx * dx + dz * dz);

            if (distance < 0.001)
                distance = 0.001;

            dx /= distance;
            dz /= distance;

            double horizontalStrength = 0.4 + random.nextDouble() * 0.6;
            double verticalStrength = 0.5 + random.nextDouble() * 0.8;

            entity.setDeltaMovement(
                    dx * horizontalStrength,
                    verticalStrength,
                    dz * horizontalStrength
            );

            entity.dropItem = false;
            level.addFreshEntity(entity);
        };
    }
}