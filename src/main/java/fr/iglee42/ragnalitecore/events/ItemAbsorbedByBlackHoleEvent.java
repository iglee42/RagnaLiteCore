package fr.iglee42.ragnalitecore.events;

import com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity;
import fr.iglee42.ragnalitecore.utils.BlackHoleThrows;
import lombok.Getter;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Getter
public class ItemAbsorbedByBlackHoleEvent extends ItemEvent {

    private final BlackHoleBlockEntity blackHole;

    public ItemAbsorbedByBlackHoleEvent(ItemEntity itemEntity, BlackHoleBlockEntity blackHole) {
        super(itemEntity);
        this.blackHole = blackHole;
    }

    public void throwsItemStack(ItemStack stack){
        ((BlackHoleThrows) blackHole).ragnalitecore$throwsItemStack(blackHole.getLevel(), stack, blackHole.getBlockPos().getCenter());
    }
}
