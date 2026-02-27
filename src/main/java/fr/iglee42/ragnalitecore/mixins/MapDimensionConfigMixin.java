package fr.iglee42.ragnalitecore.mixins;

import com.robertx22.library_of_exile.config.map_dimension.MapDimensionConfig;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.dimension.WipeDimensionFeature;
import com.robertx22.library_of_exile.main.ExileLog;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

@Mixin(value = MapDimensionConfig.class,remap = false)
public class MapDimensionConfigMixin {

    @Inject(method = "lambda$register$7",at = @At(value = "INVOKE", target = "Lcom/robertx22/library_of_exile/dimension/WipeDimensionFeature;OnStartResetMap(Lcom/robertx22/library_of_exile/dimension/MapDimensionInfo;Lnet/minecraft/resources/ResourceLocation;)V",shift = At.Shift.BEFORE),locals = LocalCapture.CAPTURE_FAILSOFT,cancellable = true)
    private static void rewriteDimensionWipe(MapDimensionConfig CONFIG, MapDimensionInfo info, ResourceLocation mapId, ServerAboutToStartEvent event, CallbackInfo ci){
        long startMillis = System.currentTimeMillis();
        MutableBoolean wiped = new MutableBoolean(true);
        try (LevelStorageSource.LevelStorageAccess access = ((MinecraftServerAccessor)event.getServer()).rlc$getStorageSource()){
            rlc$deleteDirectoryRecursion(access.getDimensionPath(ResourceKey.create(Registries.DIMENSION,mapId)));
            ExileLog.get().log("[RagnaLiteCore] Taken {} seconds to wipe dimension " + access.getDimensionPath(ResourceKey.create(Registries.DIMENSION,mapId)) +"!", String.format("%.2f", (System.currentTimeMillis() - startMillis) / 1000f));
        } catch (NoSuchFileException | FileNotFoundException ex){
            ExileLog.get().log("[RagnaLiteCore] Dimension " + mapId +" doesn't exist so no need to wipe !");
            ci.cancel();
            return;
        } catch (IOException ex){
            ExileLog.get().log("[RagnaLiteCore] Failed to wipe dimension " + mapId +"! Using old function to do it !", ex);
            wiped.setFalse();
        }

        if (wiped.getValue()) {
            ci.cancel();
            return;
        }
    }

    @Unique
    private static void rlc$deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for(Path entry : entries) {
                    rlc$deleteDirectoryRecursion(entry);
                }
            }
        }

        Files.delete(path);
    }



}
