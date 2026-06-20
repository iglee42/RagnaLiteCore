package fr.iglee42.ragnalitecore.rift;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import fr.iglee42.ragnalitecore.RLCShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;

import static fr.iglee42.ragnalitecore.RLCShaders.COSMIC_UVS;

public class ClientRiftManager {
    public static final float MAX_RIFT_SIZE = 15f;
    public static float riftSize = 0f;
    public static boolean isExpanding = false;

    public static void renderRift(PoseStack poseStack, BufferBuilder builder, Level level) {

        poseStack.pushPose();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        // Setup Shader
        final Minecraft mc = Minecraft.getInstance();
        float scale = 1f;
        float yaw = (float) (mc.player.getYRot() * 2.0f * Math.PI / 360.0);
        float pitch = -(float) (mc.player.getXRot() * 2.0f * Math.PI / 360.0);
        RLCShaders.cosmicTime.set(mc.level.getGameTime() % Integer.MAX_VALUE);
        RLCShaders.cosmicYaw.set(yaw);
        RLCShaders.cosmicPitch.set(pitch);
        RLCShaders.cosmicExternalScale.set(scale);
        RLCShaders.cosmicOpacity.set(1.0F);

        if (RLCShaders.cosmicUVs != null) {
            RLCShaders.cosmicUVs.set(COSMIC_UVS);
        }

        // Orientation fixe dans le ciel
        float progress = Mth.clamp(riftSize / MAX_RIFT_SIZE, 0.0F, 1.0F);
        float minSpeed = 0.5F;
        float maxSpeed = 2.0F;

        float speed;

        if (isExpanding) {
            float t = 1.0F - progress;
            speed = minSpeed + (maxSpeed - minSpeed) * t * t;
        } else {
            speed = minSpeed;
        }

        float angle = level.getGameTime() * speed;

        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.setShader(() -> RLCShaders.COSMIC_SHADER);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        TextureAtlasSprite layer0 = RLCShaders.SINGULARITY_LAYER_0;
        TextureAtlasSprite layer1 = RLCShaders.SINGULARITY_LAYER_1;

        if (layer0 == null || layer1 == null) {
            poseStack.popPose();
            return;
        }

        // Première couche
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        int packedLight = LightTexture.FULL_BRIGHT;
        addBlockVertex(builder, matrix, -riftSize, 101.0F, -riftSize, layer0.getU0(), layer0.getV0(), packedLight);
        addBlockVertex(builder, matrix, riftSize, 101.0F, -riftSize, layer0.getU1(), layer0.getV0(), packedLight);
        addBlockVertex(builder, matrix, riftSize, 101.0F, riftSize, layer0.getU1(), layer0.getV1(), packedLight);
        addBlockVertex(builder, matrix, -riftSize, 101.0F, riftSize, layer0.getU0(), layer0.getV1(), packedLight);

        BufferUploader.drawWithShader(builder.end());

        // Deuxième couche
        poseStack.mulPose(Axis.YP.rotationDegrees(level.getGameTime() * (speed * 2)));
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        Matrix4f matrix2 = poseStack.last().pose();
        addBlockVertex(builder, matrix2, -riftSize, 100.99F, -riftSize, layer1.getU0(), layer1.getV0(), packedLight);
        addBlockVertex(builder, matrix2, riftSize, 100.99F, -riftSize, layer1.getU1(), layer1.getV0(), packedLight);
        addBlockVertex(builder, matrix2, riftSize, 100.99F, riftSize, layer1.getU1(), layer1.getV1(), packedLight);
        addBlockVertex(builder, matrix2, -riftSize, 100.99F, riftSize, layer1.getU0(), layer1.getV1(), packedLight);

        BufferUploader.drawWithShader(builder.end());
        poseStack.popPose();
    }

    private static void addBlockVertex(BufferBuilder builder, Matrix4f matrix, float x, float y, float z, float u, float v, int packedLight) {
        builder.vertex(matrix, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .uv2(packedLight)
                .normal(0.0F, 1.0F, 0.0F)
                .endVertex();
    }
}
