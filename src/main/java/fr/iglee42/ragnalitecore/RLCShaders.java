package fr.iglee42.ragnalitecore;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RagnaLiteCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class RLCShaders {
    public static final float[] COSMIC_UVS = new float[40];
    public static TextureAtlasSprite[] COSMIC_SPRITES = new TextureAtlasSprite[10];
    public static TextureAtlasSprite SINGULARITY_LAYER_0;
    public static TextureAtlasSprite SINGULARITY_LAYER_1;

    public static ShaderInstance COSMIC_SHADER;

    public static Uniform cosmicTime;
    public static Uniform cosmicYaw;
    public static Uniform cosmicPitch;
    public static Uniform cosmicExternalScale;
    public static Uniform cosmicOpacity;
    public static Uniform cosmicUVs;


    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        try {
           event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "cosmic"), DefaultVertexFormat.BLOCK), shader -> {
                COSMIC_SHADER = shader;
                cosmicTime = COSMIC_SHADER.getUniform("time");
                cosmicYaw = COSMIC_SHADER.getUniform("yaw");
                cosmicPitch = COSMIC_SHADER.getUniform("pitch");
                cosmicExternalScale = COSMIC_SHADER.getUniform("externalScale");
                cosmicOpacity = COSMIC_SHADER.getUniform("opacity");
                cosmicUVs = COSMIC_SHADER.getUniform("cosmicuvs");
                COSMIC_SHADER.apply();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onTexturesSwitchPost(TextureStitchEvent.Post event) {
        if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            for (int i = 0; i < COSMIC_SPRITES.length; i++) {
                COSMIC_SPRITES[i] = event.getAtlas().getSprite(ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "shaders/cosmic/cosmic_" + i));
                COSMIC_UVS[i * 4] = COSMIC_SPRITES[i].getU0();
                COSMIC_UVS[i * 4 + 1] = COSMIC_SPRITES[i].getV0();
                COSMIC_UVS[i * 4 + 2] = COSMIC_SPRITES[i].getU1();
                COSMIC_UVS[i * 4 + 3] = COSMIC_SPRITES[i].getV1();
            }

            SINGULARITY_LAYER_0 = event.getAtlas().getSprite(ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "rift/rift_0"));
            SINGULARITY_LAYER_1 = event.getAtlas().getSprite(ResourceLocation.fromNamespaceAndPath(RagnaLiteCore.MODID, "rift/rift_1"));

        }
    }


}