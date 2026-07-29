package com.atom.firefly.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.atom.firefly.Constants;

public class FireflyModel extends EntityModel<FireflyRenderer.FireflyRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "firefly"), "main"
    );

    private final ModelPart left_wing;
    private final ModelPart right_wing;

    public FireflyModel(ModelPart root) {
        super(root);
        this.left_wing = root.getChild("left_wing");
        this.right_wing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("back_emissive", CubeListBuilder.create().texOffs(0, 14).addBox(-4.0F, -5.0F, -1.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 21.0F, 4.0F));

        partdefinition.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(12, 24).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 24).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, -4.0F));

        partdefinition.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(18, 24).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 24).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 3.0F));

        partdefinition.addOrReplaceChild("BackLeftLeg2", CubeListBuilder.create().texOffs(18, 24).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 24).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, -1.0F));

        partdefinition.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(24, 20).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 24).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, -4.0F));

        partdefinition.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(22, 24).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 23).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, 3.0F));

        partdefinition.addOrReplaceChild("BackRightLeg2", CubeListBuilder.create().texOffs(22, 24).addBox(1.0F, -3.0F, -2.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 23).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 24.0F, -1.0F));

        partdefinition.addOrReplaceChild("AntenneDroite", CubeListBuilder.create().texOffs(24, 20).addBox(1.0F, -3.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(1.0F, -3.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 24).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 17.0F, -6.0F));

        partdefinition.addOrReplaceChild("AntenneGauche", CubeListBuilder.create().texOffs(20, 24).addBox(1.0F, 0.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(1.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 15.0F, -7.0F));

        partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(26, 20).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 20).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 20).addBox(0.0F, -4.0F, 1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(0.0F, -4.0F, 2.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(2, 24).addBox(0.0F, -5.0F, 3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 23).addBox(0.0F, -5.0F, 4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 16.0F, -1.0F));

        partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(4, 24).addBox(0.0F, -5.0F, 3.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 24).addBox(0.0F, -4.0F, 2.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 24).addBox(0.0F, -4.0F, 1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 24).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 23).addBox(0.0F, -5.0F, 4.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 26).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 16.0F, -1.0F));

        partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(20, 14).addBox(-3.0F, -7.0F, -8.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -8.0F, -6.0F, 5.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(FireflyRenderer.FireflyRenderState state) {
        float flapSpeed = 1.2F;
        float angle = Mth.sin(state.ageInTicks * flapSpeed) * 0.6F;
        this.left_wing.zRot = angle;
        this.right_wing.zRot = -angle;
    }
}