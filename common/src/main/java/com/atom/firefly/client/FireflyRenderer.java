package com.atom.firefly.client;

import com.atom.firefly.Constants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FireflyRenderer extends EntityRenderer<FireflyEntity, FireflyRenderer.FireflyRenderState> {
    private final FireflyModel model;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/firefly_texture.png");
    private static final ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/firefly_glow.png");

    private static final RenderType SOLID_RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE);
    private static final RenderType GLOW_RENDER_TYPE = RenderType.eyes(GLOW_TEXTURE);

    public FireflyRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FireflyModel(context.bakeLayer(FireflyModel.LAYER_LOCATION));
    }

    @Override
    public FireflyRenderState createRenderState() {
        return new FireflyRenderState();
    }

    @Override
    public void extractRenderState(FireflyEntity entity, FireflyRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.ageInTicks = entity.tickCount + partialTick;
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        // Breathing pulse effect: oscillates smoothly between 0.55 and 1.0
        float pulse = (Mth.sin(state.ageInTicks * 0.15F) + 1.0F) * 0.5F;
        state.glowAlpha = 0.55F + pulse * 0.45F;
    }

    public void render(FireflyRenderState state, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.scale(0.3F, 0.3F, 0.3F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));

        this.model.setupAnim(state);

        VertexConsumer solidConsumer = buffer.getBuffer(SOLID_RENDER_TYPE);
        this.model.renderToBuffer(poseStack, solidConsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);

        // Render emissive glowing tail with smooth pulsating intensity
        int alpha = (int) (state.glowAlpha * 255.0F);
        int glowColor = (alpha << 24) | 0x00FFFFFF;

        VertexConsumer eyesConsumer = buffer.getBuffer(GLOW_RENDER_TYPE);
        this.model.renderToBuffer(poseStack, eyesConsumer, 15728880, OverlayTexture.NO_OVERLAY, glowColor);

        poseStack.popPose();
        super.render(state, poseStack, buffer, packedLight);
    }

    public static class FireflyRenderState extends EntityRenderState {
        public float ageInTicks;
        public float yRot;
        public float glowAlpha = 1.0F;
    }
}