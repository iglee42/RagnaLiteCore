package fr.iglee42.ragnalitecore.rift;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RiftingBlockRenderer implements BlockEntityRenderer<RiftingBlockEntity> {
   public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");

   public RiftingBlockRenderer(BlockEntityRendererProvider.Context p_173529_) {
   }

   public void render(RiftingBlockEntity p_112140_, float p_112141_, PoseStack p_112142_, MultiBufferSource p_112143_, int p_112144_, int p_112145_) {
      long i = p_112140_.getLevel().getGameTime();
      List<RiftingBlockEntity.BeaconBeamSection> list = p_112140_.getBeamSections();
      int j = 0;

      for(int k = 0; k < list.size(); ++k) {
         RiftingBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
         renderBeaconBeam(p_112142_, p_112143_, p_112141_, i, j, k == list.size() - 1 ? 1024 : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
         j += beaconblockentity$beaconbeamsection.getHeight();
      }

   }

   private static void renderBeaconBeam(PoseStack p_112177_, MultiBufferSource p_112178_, float p_112179_, long p_112180_, int p_112181_, int p_112182_, float[] p_112183_) {
      BeaconRenderer.renderBeaconBeam(p_112177_, p_112178_, BEAM_LOCATION, p_112179_, 1.0F, p_112180_, p_112181_, p_112182_, p_112183_, 0.2F, 0.25F);
   }

   public boolean shouldRenderOffScreen(RiftingBlockEntity p_112138_) {
      return true;
   }

   public int getViewDistance() {
      return 256;
   }

   public boolean shouldRender(RiftingBlockEntity p_173531_, Vec3 p_173532_) {
      return Vec3.atCenterOf(p_173531_.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(p_173532_.multiply(1.0D, 0.0D, 1.0D), (double)this.getViewDistance());
   }
}