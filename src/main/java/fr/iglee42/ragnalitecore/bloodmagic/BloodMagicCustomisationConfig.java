package fr.iglee42.ragnalitecore.bloodmagic;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import com.robertx22.library_of_exile.main.ExileLog;
import fr.iglee42.ragnalitecore.RagnaLiteCore;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Unique;
import wayoftime.bloodmagic.altar.AltarComponent;
import wayoftime.bloodmagic.altar.ComponentType;
import wayoftime.bloodmagic.api.IBloodMagicAPI;
import wayoftime.bloodmagic.impl.BloodMagicAPI;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BloodMagicCustomisationConfig {

    public static Logger LOGGER = LoggerFactory.getLogger("RagnaLiteBloodMagicCustomisation");

    public static boolean configLoaded = false;
    private static File configFile;

    public static Map<String, JsonArray> componentTypes = new HashMap<>();
    public static Map<Integer, List<AltarComponentProvider>> tiers = new HashMap<>();
    public static boolean debug = false;

    public static void load() throws IOException {
        configLoaded = true;
        configFile = new File(FMLPaths.CONFIGDIR.get().toFile(),"rmlite-bloodmagic-customisation.json");
        if (configFile.exists()){
            JsonObject config = new Gson().fromJson(new FileReader(configFile),JsonObject.class);
            if (config.has("debug") && config.get("debug").isJsonPrimitive() && config.get("debug").getAsJsonPrimitive().isBoolean()){
                debug = config.get("debug").getAsBoolean();
            }
            
            if (config.has("componentTypes") && config.get("componentTypes").isJsonObject()){
                JsonObject componentTypesJson = config.getAsJsonObject("componentTypes");
                for (String key : componentTypesJson.keySet()){
                    if (componentTypesJson.get(key).isJsonArray()){
                        componentTypes.put(key,componentTypesJson.getAsJsonArray(key));
                    }
                }
            }

            if (config.has("tiers") && config.get("tiers").isJsonObject()){
                JsonObject tiersJson = config.getAsJsonObject("tiers");
                for (String key : tiersJson.keySet()){
                    if (tiersJson.get(key).isJsonArray()){
                        JsonArray tierJson = tiersJson.getAsJsonArray(key);
                        try {
                            int tier = Integer.parseInt(key);
                            if (tier < 2 || tier > 6){
                                LOGGER.error("Invalid tier key in config: {}. Must be between 2 and 6 (Tier 1 must always be the Altar alone)", key);
                                continue;
                            }
                            List<AltarComponentProvider> providers = new ArrayList<>();
                            for (JsonElement element : tierJson) {
                                if (!element.isJsonObject()){
                                    LOGGER.error("Invalid component in tier {}: {}. Must be a JSON object", tier, element);
                                    continue;
                                }
                                JsonObject providerJson = element.getAsJsonObject();
                                AltarComponentProvider provider;
                                if (providerJson.has("type") && providerJson.get("type").isJsonPrimitive() && providerJson.get("type").getAsString().equalsIgnoreCase("for")){
                                    provider = new ForAltarComponent();
                                } else {
                                    provider = new SimpleAltarComponent();
                                }
                                try {
                                    provider.read(providerJson);
                                } catch (JsonParseException e){
                                    LOGGER.error("Invalid component in tier {}: {}. Error: {}", tier, providerJson, e.getMessage());
                                    continue;
                                }
                                providers.add(provider);
                            }
                            tiers.put(tier,providers);
                        } catch (NumberFormatException e){
                           LOGGER.error("Invalid tier key in config: {}", key);
                        }
                    }
                }
            }
        } else {
            JsonObject config = new JsonObject();
            config.addProperty("debug",false);
            config.add("componentTypes",new JsonObject());
            config.add("tiers",new JsonObject());
            FileWriter writer = new FileWriter(configFile);
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(config));
            writer.close();
        }
    }

    public static void initApi(IBloodMagicAPI apiInterface, HolderLookup.Provider registries){
        BloodMagicAPI api = (BloodMagicAPI)apiInterface;
        if (!configLoaded) return;

        // Unregister all existing component types if present in the config
        componentTypes.keySet().forEach(s->{
            ComponentType type = ComponentType.getType(s);
            if (type != null) {
                List<BlockState> existing = new ArrayList<>(api.getComponentStates(type));
                if (existing != null) {
                    existing.forEach(state -> api.unregisterAltarComponent(state, type.name()));
                }
            }
        });

        // Register the new blockstates for components
        componentTypes.forEach((s,a)->{
            ComponentType type = ComponentType.getType(s);
            if (type != null) {
                for (JsonElement jsonElement : a) {
                    if (jsonElement.isJsonObject()) {
                        JsonObject obj = jsonElement.getAsJsonObject();
                        List<String> ignoredProperties = new ArrayList<>();
                        if (obj.has("ignoredProperties") && obj.get("ignoredProperties").isJsonArray()) {
                            JsonArray ignoredProps = obj.getAsJsonArray("ignoredProperties");
                            for (JsonElement prop : ignoredProps) {
                                if (prop.isJsonPrimitive() && prop.getAsJsonPrimitive().isString()) {
                                    ignoredProperties.add(prop.getAsString());
                                }
                            }
                        }
                        BlockState state = BlockState.CODEC.decode(RegistryOps.create(JsonOps.INSTANCE,registries),obj).result().map(Pair::getFirst).orElse(Blocks.AIR.defaultBlockState());

                        if (state.isAir()) continue;
                        Map<Property<?>,Comparable<?>> verifiedProperties = state.getValues().entrySet().stream().filter(e->!ignoredProperties.contains(e.getKey().getName())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        if (debug){
                            StringBuilder builder = new StringBuilder("Add block {} for component {} | Verified Properties : [");
                            verifiedProperties.keySet().forEach(prop->
                                    builder.append(prop.getName()).append("=").append(verifiedProperties.get(prop)).append(", "));
                            builder.append("] | Ignored Properties : [");
                            ignoredProperties.forEach(prop->builder.append(prop).append(", "));
                            builder.append("]");
                            LOGGER.info(builder.toString(),state.getBlock().builtInRegistryHolder().key().location(),type.name());
                        }
                        List<BlockState> validStates = state.getBlock().getStateDefinition().getPossibleStates().stream().filter(bs->{
                            Map<Property<?>,Comparable<?>> sProperties = bs.getValues();
                            for (Map.Entry<Property<?>,Comparable<?>> entry : verifiedProperties.entrySet()){
                                if (!sProperties.containsKey(entry.getKey()) || !Objects.equals(sProperties.get(entry.getKey()), entry.getValue())){
                                    return false;
                                }
                            }
                            return true;
                        }).toList();
                        validStates.forEach(bs->api.registerAltarComponent(bs,type.name()));
                    }
                }
            }
        });

        if (debug) {
            LOGGER.info("Modified component types for Blood Magic : ");
            componentTypes.forEach((s, a) -> {
                ComponentType type = ComponentType.getType(s);
                if (type != null) {
                    StringBuilder msg = new StringBuilder(type.name() + " => [");
                    List<BlockState> existing = new ArrayList<>(api.getComponentStates(type));
                    if (existing != null) {
                        existing.forEach(state -> msg.append(state.toString()).append(", "));
                    }
                    msg.append("]");
                    LOGGER.info(" - {}", msg);
                }
            });
        }


    }

    public static boolean isVanillaComponentType(String name){
        name = name.toUpperCase();
        return name.equals("GLOWSTONE") || name.equals("BLOODSTONE") || name.equals("BEACON") || name.equals("BLOODRUNE") || name.equals("CRYSTAL") || name.equals("NOTAIR");
    }

    public static void addAltarComponents(int tier, Consumer<AltarComponent> consumer){
        if (!configLoaded) return;
        if (tier > 1 && BloodMagicCustomisationConfig.tiers.containsKey(tier - 1)){
            addAltarComponents(tier-1,consumer);
        }
        BloodMagicCustomisationConfig.tiers.get(tier).forEach(comp ->
                comp.addComponents(consumer));
    }



}
