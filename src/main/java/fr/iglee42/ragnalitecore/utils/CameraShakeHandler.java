package fr.iglee42.ragnalitecore.utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CameraShakeHandler {

    private static float shakeTime = 0;
    private static float shakeStrength = 0;

    public static void shake(float duration, float strength) {
        shakeTime = duration / 2f;
        shakeStrength = strength;
    }

    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {

        if (shakeTime <= 0)
            return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;
		if (mc.level == null)
			return;
		if (!mc.options.bobView().get())
			return;

        double partialTick = event.getPartialTick();
        shakeTime-= (float) (partialTick * 0.1f);
        float intensity = shakeStrength * (shakeTime / 20.0f);
        double time = (mc.level.getGameTime() + partialTick);

        float yawOffset = (float) Math.sin(time * 2.0f) * intensity;
        float pitchOffset = (float) Math.cos(time * 1.5f) * intensity;
        float rollOffset = (float) Math.sin(time * 1.5f) * intensity * 0.5f;

        event.setYaw(event.getYaw() + yawOffset);
        event.setPitch(event.getPitch() + pitchOffset);
        event.setRoll(event.getRoll() + rollOffset);
    }
}