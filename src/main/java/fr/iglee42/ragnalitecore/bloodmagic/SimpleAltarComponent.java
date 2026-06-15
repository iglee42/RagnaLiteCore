package fr.iglee42.ragnalitecore.bloodmagic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.BlockPos;
import wayoftime.bloodmagic.altar.AltarComponent;
import wayoftime.bloodmagic.altar.ComponentType;

import java.util.function.Consumer;

public class SimpleAltarComponent implements AltarComponentProvider{

    private ComponentType type;
    private BlockPos pos = BlockPos.ZERO;
    private boolean upgradeSlot = false;

    @Override
    public void addComponents(Consumer<AltarComponent> components) {
        AltarComponent component = new AltarComponent(pos,type);
        if (upgradeSlot) component.setUpgradeSlot();
        components.accept(component);
    }

    @Override
    public void read(JsonObject json) {
        if (!json.has("component") || !json.get("component").isJsonPrimitive() || !json.get("component").getAsJsonPrimitive().isString()) throw new JsonParseException("Invalid component type for component: " + json.get("component"));
        ComponentType t = ComponentType.getType(json.get("component").getAsString().toUpperCase());
        if (t == null) throw new JsonParseException("Invalid component type: " + json.get("component").getAsString());
        this.type = t;

        if (!json.has("pos") || !json.get("pos").isJsonArray() || json.getAsJsonArray("pos").size() != 3) throw new JsonParseException("Invalid pos for component: " + json.get("pos"));

        JsonArray posArray = json.getAsJsonArray("pos");
        this.pos = new BlockPos(posArray.get(0).getAsInt(), posArray.get(1).getAsInt(), posArray.get(2).getAsInt());

        if (json.has("upgradeSlot") && json.get("upgradeSlot").isJsonPrimitive() && json.get("upgradeSlot").getAsJsonPrimitive().isBoolean()) {
            this.upgradeSlot = json.get("upgradeSlot").getAsBoolean();
        }
    }
}
