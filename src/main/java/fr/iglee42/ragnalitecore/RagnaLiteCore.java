package fr.iglee42.ragnalitecore;

import com.mojang.logging.LogUtils;
import fr.iglee42.ragnalitecore.bloodmagic.BloodMagicCustomisationConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import wayoftime.bloodmagic.common.item.BloodMagicItems;
import wayoftime.bloodmagic.common.item.BloodOrb;
import wayoftime.bloodmagic.compat.patchouli.RegisterPatchouliMultiblocks;
import wayoftime.bloodmagic.core.AnointmentRegistrar;
import wayoftime.bloodmagic.core.LivingArmorRegistrar;
import wayoftime.bloodmagic.core.registry.AlchemyArrayRegistry;
import wayoftime.bloodmagic.core.registry.OrbRegistry;
import wayoftime.bloodmagic.impl.BloodMagicAPI;
import wayoftime.bloodmagic.impl.BloodMagicCorePlugin;
import wayoftime.bloodmagic.ritual.ModRituals;
import wayoftime.bloodmagic.structures.ModRoomPools;

import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RagnaLiteCore.MODID)
public class RagnaLiteCore {

    public static final String MODID = "ragnalitecore";
    public static final String RAGNA_LITE = "ragnalite";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(),MODID);
    private static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",()-> CreativeModeTab.builder()
            .icon(RLCItems.TIER_12_ROCKET.get()::getDefaultInstance)
            .title(Component.translatable("modname.ragnalitecore"))
            .build());



    public RagnaLiteCore() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onLoadComplete);

        MinecraftForge.EVENT_BUS.register(this);

        TABS.register(modEventBus);
        RLCItems.ITEMS.init();
        RLCEntities.ENTITY_TYPES.init();
        RLCPackets.register();

        try {
            BloodMagicCustomisationConfig.load();
        } catch (IOException e) {
            LOGGER.error("Failed to load Blood Magic Customisation Config", e);
            BloodMagicCustomisationConfig.configLoaded = false;
        }
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //event.enqueueWork(RLCPackets::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(TAB.getKey())){
            RLCItems.ITEMS.stream().forEach(event::accept);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        BloodMagicCustomisationConfig.initApi(BloodMagicAPI.INSTANCE, VanillaRegistries.createLookup());
    }

}
