package fr.iglee42.ragnalitecore.mixins;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.sugar.Local;
import fr.iglee42.ragnalitecore.RagnaLiteCore;
import fr.iglee42.ragnalitecore.bloodmagic.BloodMagicCustomisationConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.TriPredicate;
import wayoftime.bloodmagic.altar.AltarTier;
import wayoftime.bloodmagic.altar.ComponentType;
import wayoftime.bloodmagic.compat.patchouli.RegisterPatchouliMultiblocks;
import wayoftime.bloodmagic.impl.BloodMagicAPI;
import wayoftime.bloodmagic.ritual.EnumRuneType;

import java.util.*;

@Mixin(value = RegisterPatchouliMultiblocks.class,remap = false)
public class RegisterPatchouliMultiblocksMixin {

    @Unique
    private static final List<Character> AUTHORIZED_CHARACTERS = List.of(
            'A', 'D', 'E', 'F','H',
            'I','J','K','L','M','N','O','Q','T','U','V','W','X','Y','Z',
            '1','2','3','4','5','6','7','8','9'
    );

    @Unique
    private static Map<Character, ComponentType> rmlite$componentTypeMap = Maps.newHashMap();

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/api/PatchouliAPI$IPatchouliAPI;makeMultiblock([[Ljava/lang/String;[Ljava/lang/Object;)Lvazkii/patchouli/api/IMultiblock;",ordinal = 1))
    private IMultiblock ragnamodlite$addNewStateMatchers(PatchouliAPI.IPatchouliAPI instance, String[][] pattern, Object[] objects){
        BloodMagicAPI bmAPI = BloodMagicAPI.INSTANCE;
        if (BloodMagicCustomisationConfig.debug) {
            BloodMagicCustomisationConfig.LOGGER.info("Adding new state matchers for Blood Magic Multiblocks, Pattern : ");
            for (int i = 0; i < pattern.length; ++i) {
                for (int j = 0; j < pattern[i].length; ++j) {
                    BloodMagicCustomisationConfig.LOGGER.info("pattern[{}][{}] = \"{}\";", i, j, pattern[i][j]);
                }
                BloodMagicCustomisationConfig.LOGGER.info("");
            }
        }
        List<Object> matchers = new ArrayList<>(List.of(objects));
        rmlite$componentTypeMap.forEach((c,type)->{
            matchers.add(c);
            matchers.add(new BMStateMatcher(bmAPI.getComponentStates(type)));
        });
        return instance.makeMultiblock(pattern,matchers.toArray());
    }

    @Inject(method = "makePattern",at= @At(value = "INVOKE", target = "Lwayoftime/bloodmagic/altar/ComponentType;name()Ljava/lang/String;"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private void ragnamodlite$addNewComponentTypesToPattern(Map<BlockPos, EnumRuneType> ritualMap, Map<BlockPos, ComponentType> altarMap, CallbackInfoReturnable<String[][]> cir, @Local(name = "row") StringBuilder row, @Local(name = "component") ComponentType component){
        String name = component.name();
        if (BloodMagicCustomisationConfig.isVanillaComponentType(name)) return;
        Character used;
        if (rmlite$componentTypeMap.containsValue(component)){
            used = rmlite$componentTypeMap.entrySet().stream().filter(e->e.getValue().equals(component)).map(Map.Entry::getKey).findFirst().orElseThrow(()->new IllegalStateException("Character not found even when the component type is in the Map ??"));
        } else {
            used = AUTHORIZED_CHARACTERS.stream().filter(c->!rmlite$componentTypeMap.containsKey(c)).findFirst().orElseThrow(()->new IllegalStateException("No more characters available for new component types"));
            if (BloodMagicCustomisationConfig.debug) BloodMagicCustomisationConfig.LOGGER.info("Added new component type {} with character {} to the pattern",name,used);
        }
        rmlite$componentTypeMap.put(used,component);
        row.append(used);
    }

    private static class BMStateMatcher implements IStateMatcher
    {
        private final List<BlockState> render; // BlockStates to Render
        private final List<BlockState> valid; // BlockStates that are Valid.

        private BMStateMatcher(Block singleBlockRender, List<BlockState> valid)
        {
            List<BlockState> render = Lists.newArrayList();
            render.add(singleBlockRender.defaultBlockState());
            this.render = render;
            this.valid = valid;
        }

        private BMStateMatcher(List<BlockState> renderAndValid)
        {
            this.render = renderAndValid;
            this.valid = renderAndValid;
        }

        @Override
        public BlockState getDisplayedState(long ticks)
        {
            if (render.isEmpty())
            {
                return Blocks.BEDROCK.defaultBlockState(); // show something impossible
            } else
            {
                int idx = (int) ((ticks / 20) % render.size());
                return render.get(idx);
            }
        }

        @Override
        public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate()
        {
            return (w, p, s) -> valid.contains(s);
        }

    }
}
