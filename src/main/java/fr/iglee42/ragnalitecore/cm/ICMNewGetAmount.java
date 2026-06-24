package fr.iglee42.ragnalitecore.cm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface ICMNewGetAmount {
    int ragnalitecore$getItemAmount(String slot, Item item, @Nullable CompoundTag nbt, String tag);
}
