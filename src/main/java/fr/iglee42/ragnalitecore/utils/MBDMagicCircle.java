package fr.iglee42.ragnalitecore.utils;

import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import org.jetbrains.annotations.Nullable;

public interface MBDMagicCircle {

    @Nullable MagicCircle rlc$getMagicCircle();
    void rlc$setMagicCircle(@Nullable MagicCircle magicCircle);
    default boolean rlc$hasMagicCircle(){
        return rlc$getMagicCircle() != null;
    }
}
