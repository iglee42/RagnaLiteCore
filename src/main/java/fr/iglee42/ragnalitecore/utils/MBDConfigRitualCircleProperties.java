package fr.iglee42.ragnalitecore.utils;

import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.lowdragmc.lowdraglib.gui.editor.annotation.Configurable;
import com.lowdragmc.lowdraglib.gui.editor.annotation.NumberRange;
import com.lowdragmc.lowdraglib.gui.editor.configurator.ConfiguratorGroup;
import com.lowdragmc.lowdraglib.gui.editor.configurator.IConfigurable;
import com.lowdragmc.lowdraglib.gui.editor.configurator.SelectorConfigurator;
import com.lowdragmc.lowdraglib.gui.editor.configurator.WrapperConfigurator;
import com.lowdragmc.lowdraglib.gui.editor.ui.Editor;
import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.syncdata.IPersistedSerializable;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.mbd2.api.recipe.MBDRecipeType;
import com.lowdragmc.mbd2.common.gui.editor.MachineEditor;
import com.lowdragmc.mbd2.common.gui.editor.MachineProject;
import com.lowdragmc.mbd2.common.gui.editor.texture.IRendererSlotTexture;
import com.lowdragmc.mbd2.common.machine.definition.config.toggle.ToggleCreativeTab;
import com.lowdragmc.mbd2.common.machine.definition.config.toggle.ToggleRenderer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Accessors(fluent = true)
public class MBDConfigRitualCircleProperties implements IConfigurable, IPersistedSerializable {

    @Configurable(name = "config.ritual_circle.use_ritual_circle")
    @Builder.Default
    private boolean useRitualCircle = false;

    @Configurable(name = "config.ritual_circle.inner_texture")
    @Builder.Default
    private ResourceLocation innerCircleTexture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/inner/union.png");

    @Configurable(name = "config.ritual_circle.outer_texture")
    @Builder.Default
    private ResourceLocation outerCircleTexture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/magic_circle/outer/pure.png");

    @Configurable(name = "config.ritual_circle.offset")
    @Builder.Default
    @NumberRange(range = {-16,16})
    private BlockPos offset = BlockPos.ZERO;

    @Override
    public void buildConfigurator(ConfiguratorGroup father) {
        IConfigurable.super.buildConfigurator(father);
        father.addConfigurators(new WrapperConfigurator("config.ritual_circle.inner_preview",
                new ImageWidget(0,0,128,128,() -> new ResourceTexture(innerCircleTexture))
        ),new WrapperConfigurator("config.ritual_circle.outer_preview",
                new ImageWidget(0,0,128,128,() -> new ResourceTexture(outerCircleTexture))
        ));
    }
}
