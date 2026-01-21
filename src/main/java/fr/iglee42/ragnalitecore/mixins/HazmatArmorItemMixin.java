package fr.iglee42.ragnalitecore.mixins;

import com.github.alexmodguy.alexscaves.server.item.HazmatArmorItem;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.capabilities.radiation.item.RadiationShieldingHandler;
import mekanism.common.integration.gender.GenderCapabilityHelper;
import mekanism.common.item.gear.ItemHazmatSuitArmor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = HazmatArmorItem.class)
public class HazmatArmorItemMixin extends ArmorItem {

    public HazmatArmorItemMixin(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        ItemCapabilityWrapper wrapper = new ItemCapabilityWrapper(stack, RadiationShieldingHandler.create(item -> ItemHazmatSuitArmor.getShieldingByArmor(getType())));
        GenderCapabilityHelper.addGenderCapability(this, wrapper::add);
        return wrapper;
    }


}
