package fr.iglee42.ragnalitecore.bloodmagic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import wayoftime.bloodmagic.altar.AltarComponent;

import java.util.function.Consumer;

public interface AltarComponentProvider {

    void addComponents(Consumer<AltarComponent> components);

    void read(JsonObject json) throws JsonParseException;

}
