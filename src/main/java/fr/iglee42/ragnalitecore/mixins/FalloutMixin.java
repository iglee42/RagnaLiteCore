package fr.iglee42.ragnalitecore.mixins;

import appeng.worldgen.meteorite.MeteoriteBlockPutter;
import appeng.worldgen.meteorite.fallout.Fallout;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Fallout.class, remap = false)
public class FalloutMixin {

    @Shadow
    @Final
    protected RandomSource random;

    @Shadow
    @Final
    private MeteoriteBlockPutter putter;

    @Shadow
    @Final
    private BlockState skyStone;

    /**
     * @author iglee42
     * @reason Replace with custom random block selection
     */
    @Overwrite
    public void getRandomFall(LevelAccessor level, BlockPos pos) {
        var a = random.nextFloat();
        if (a > 0.9f) {
            this.putter.put(level, pos, ModBlocks.MOON_STONE.get().defaultBlockState());
        } else if (a > 0.8f) {
            this.putter.put(level, pos, ModBlocks.MOON_COBBLESTONE.get().defaultBlockState());
        } else {
            this.putter.put(level, pos, ModBlocks.MOON_SAND.get().defaultBlockState());
        }
    }

    /**
     * @author iglee42
     * @reason Replace with custom random block selection
     */
    @Overwrite
    public void getRandomInset(LevelAccessor level, BlockPos pos) {
        var a = random.nextFloat();
        if (a > 0.9f) {
            this.putter.put(level, pos, ModBlocks.MOON_COBBLESTONE.get().defaultBlockState());
        } else if (a > 0.8f) {
            this.putter.put(level, pos, ModBlocks.MOON_STONE.get().defaultBlockState());
        } else if (a > 0.7f) {
            this.putter.put(level, pos, ModBlocks.MOON_SAND.get().defaultBlockState());
        } else if (a > 0.6f) {
            this.putter.put(level, pos, this.skyStone);
        } else if (a > 0.5f) {
            this.putter.put(level, pos, ModBlocks.MOON_SAND.get().defaultBlockState());
        } else {
            this.putter.put(level, pos, Blocks.AIR.defaultBlockState());
        }
    }
}
