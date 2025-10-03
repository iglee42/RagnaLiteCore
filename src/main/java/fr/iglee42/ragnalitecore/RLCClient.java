package fr.iglee42.ragnalitecore;

import com.drd.ad_extendra.client.models.entities.vehicles.CustomRocketModel;
import com.drd.ad_extendra.client.renderers.entities.vehicles.CustomRocketRenderer;
import earth.terrarium.adastra.client.forge.AdAstraClientForge;
import earth.terrarium.adastra.client.renderers.entities.vehicles.RocketRenderer;
import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.BiConsumer;

@Mod.EventBusSubscriber(modid = RagnaLiteCore.MODID, value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class RLCClient {


    public static final ResourceLocation TIER_12_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_12_rocket.png");
    public static final ResourceLocation TIER_13_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_13_rocket.png");
    public static final ResourceLocation TIER_14_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_14_rocket.png");
    public static final ResourceLocation TIER_15_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_15_rocket.png");
    public static final ResourceLocation TIER_16_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_16_rocket.png");
    public static final ResourceLocation TIER_17_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_17_rocket.png");
    public static final ResourceLocation TIER_18_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_18_rocket.png");
    public static final ResourceLocation TIER_19_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_19_rocket.png");
    public static final ResourceLocation TIER_20_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_20_rocket.png");
    public static final ResourceLocation TIER_21_TEXTURE = new ResourceLocation("ragnalitecore", "textures/entity/rocket/tier_21_rocket.png");


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        onRegisterItemRenderers(AdAstraClientForge.ITEM_RENDERERS::put);
    }

    @SubscribeEvent
    public static void registerEntitiesRenderer(EntityRenderersEvent.RegisterRenderers event){
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_12_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_12_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_13_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_13_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_14_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_14_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_15_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_15_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_16_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_16_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_17_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_17_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_18_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_18_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_19_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_19_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_20_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_20_TEXTURE));
        ClientHooks.registerEntityRenderer(RLCEntities.TIER_21_ROCKET, c -> new RocketRenderer(c, CustomRocketModel.TIER_11_LAYER, TIER_21_TEXTURE));
    }

    public static void onRegisterItemRenderers(BiConsumer<Item, BlockEntityWithoutLevelRenderer> consumer) {
        // Rockets
        consumer.accept(RLCItems.TIER_12_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_12_TEXTURE));
        consumer.accept(RLCItems.TIER_13_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_13_TEXTURE));
        consumer.accept(RLCItems.TIER_14_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_14_TEXTURE));
        consumer.accept(RLCItems.TIER_15_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_15_TEXTURE));
        consumer.accept(RLCItems.TIER_16_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_16_TEXTURE));
        consumer.accept(RLCItems.TIER_17_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_17_TEXTURE));
        consumer.accept(RLCItems.TIER_18_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_18_TEXTURE));
        consumer.accept(RLCItems.TIER_19_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_19_TEXTURE));
        consumer.accept(RLCItems.TIER_20_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_20_TEXTURE));
        consumer.accept(RLCItems.TIER_21_ROCKET.get(), new RocketRenderer.ItemRenderer(CustomRocketModel.TIER_11_LAYER, TIER_21_TEXTURE));

    }

}
