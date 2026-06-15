package fr.iglee42.ragnalitecore.bloodmagic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.BlockPos;
import wayoftime.bloodmagic.altar.AltarComponent;
import wayoftime.bloodmagic.altar.ComponentType;

import java.util.Arrays;
import java.util.function.Consumer;

public class ForAltarComponent implements AltarComponentProvider{

    private ComponentType type;
    private boolean upgradeSlot = false;
    private int min = 0;
    private int max = 0;
    private String posFormatter = "";

    @Override
    public void addComponents(Consumer<AltarComponent> components) {

        for (int i = min; i <= max; i++) {
            String[] posParts = posFormatter.split(",");
            int x = 0, y = 0, z = 0;
            for (int j = 0; j < 3; j++) {
                if (posParts[j].equalsIgnoreCase("i")) {
                    if (j == 0) x = i;
                    else if (j == 1) y = i;
                    else if (j == 2) z = i;
                } else {
                    try {
                        int value = Integer.parseInt(posParts[j]);
                        if (j == 0) x = value;
                        else if (j == 1) y = value;
                        else if (j == 2) z = value;
                    } catch (NumberFormatException e) {
                        throw new JsonParseException("Invalid value for pos for component: " + posParts[j]);
                    }
                }
            }
            AltarComponent component = new AltarComponent(new BlockPos(x, y, z), type);
            if (upgradeSlot) component.setUpgradeSlot();
            components.accept(component);
        }
    }

    @Override
    public void read(JsonObject json) throws JsonParseException {
        if (!json.has("component") || !json.get("component").isJsonPrimitive() || !json.get("component").getAsJsonPrimitive().isString()) throw new JsonParseException("Invalid component type for component: " + json.get("component"));
        ComponentType t = ComponentType.getType(json.get("component").getAsString().toUpperCase());
        if (t == null) throw new JsonParseException("Invalid component type: " + json.get("component").getAsString());
        this.type = t;

        if (!json.has("min") || !json.get("min").isJsonPrimitive() || !json.get("min").getAsJsonPrimitive().isNumber()) throw new JsonParseException("Invalid min for component: " + json.get("min"));
        if (!json.has("max") || !json.get("max").isJsonPrimitive() || !json.get("max").getAsJsonPrimitive().isNumber()) throw new JsonParseException("Invalid max for component: " + json.get("max"));

        this.min = json.get("min").getAsInt();
        this.max = json.get("max").getAsInt();

        if (!json.has("posFormatter") || !json.get("posFormatter").isJsonPrimitive() || !json.get("posFormatter").getAsJsonPrimitive().isString()) throw new JsonParseException("Invalid posFormatter for component: " + json.get("posFormatter"));
        String formatter = json.get("posFormatter").getAsString();
        String[] poses = formatter.split(",");
        if (poses.length != 3) throw new JsonParseException("Invalid posFormatter for component: " + formatter);
        if (Arrays.stream(poses).noneMatch(s->s.equalsIgnoreCase("i"))) throw new JsonParseException("Invalid posFormatter for component: " + formatter + " (must contain at least one 'i')");
        this.posFormatter = formatter;

        if (json.has("upgradeSlot") && json.get("upgradeSlot").isJsonPrimitive() && json.get("upgradeSlot").getAsJsonPrimitive().isBoolean()) {
            this.upgradeSlot = json.get("upgradeSlot").getAsBoolean();
        }
    }
}
