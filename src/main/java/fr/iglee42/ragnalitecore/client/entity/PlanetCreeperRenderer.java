package fr.iglee42.ragnalitecore.client.entity;

import earth.terrarium.adastra.client.renderers.entities.mobs.SulfurCreeperRenderer;
import earth.terrarium.adastra.common.entities.mob.SulfurCreeper;
import fr.iglee42.ragnalitecore.RagnaLiteCore;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class PlanetCreeperRenderer extends SulfurCreeperRenderer {
    public PlanetCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SulfurCreeper entity) {
        EntityType<?> type = entity.getType();
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        return ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "textures/entity/" + id.getPath() + ".png");
    }
}
