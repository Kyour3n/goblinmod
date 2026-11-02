package com.example.goblinmod.client;

import com.example.goblinmod.GoblinMod;
import com.example.goblinmod.entity.GoblinEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * Goblin model, 64x64 texture. Texture layout (u, v) per box:
 *   head 0,0 | body 0,16 | right_arm 20,16 | left_arm 32,16 | right_leg 44,16
 *   left_leg 0,32 | right_ear 32,0 | left_ear 32,6 | nose 44,0
 *   dagger 48,8 | loincloth 0,48
 */
public class GoblinModel extends HierarchicalModel<GoblinEntity> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GoblinMod.MODID, "goblin"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightEar;
    private final ModelPart leftEar;

    public GoblinModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightEar = head.getChild("right_ear");
        this.leftEar = head.getChild("left_ear");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Head (with ears and a big hooked nose as children so they follow head motion)
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 11.0F, -1.0F));
        head.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, -3.0F, -4.0F));
        head.addOrReplaceChild("right_ear",
                CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.4F));
        head.addOrReplaceChild("left_ear",
                CubeListBuilder.create().texOffs(32, 6).mirror().addBox(0.0F, -1.5F, -0.5F, 4.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.4F));

        // Body with a loincloth attached
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 7.0F, 4.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        body.addOrReplaceChild("loincloth",
                CubeListBuilder.create().texOffs(0, 48).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 4.0F, 1.0F),
                PartPose.ZERO);

        // Arms. The right hand carries a dagger.
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(20, 16).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offset(-4.5F, 12.0F, 0.0F));
        rightArm.addOrReplaceChild("dagger",
                CubeListBuilder.create().texOffs(48, 8).addBox(-0.5F, 4.5F, -7.0F, 1.0F, 1.0F, 6.0F),
                PartPose.ZERO);
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(32, 16).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offset(4.5F, 12.0F, 0.0F));

        // Legs
        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(44, 16).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
                PartPose.offset(-1.5F, 18.0F, 0.0F));
        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 32).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
                PartPose.offset(1.5F, 18.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(GoblinEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        // Back to the rest pose each frame, then layer procedural motion + keyframes on top.
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Head looks at its target
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;

        // Scurrying walk cycle: fast, short strides with a bouncy body
        float cycle = limbSwing * 1.1F;
        float amount = Math.min(limbSwingAmount * 1.4F, 1.0F);
        this.rightLeg.xRot = Mth.cos(cycle) * 1.5F * amount;
        this.leftLeg.xRot = Mth.cos(cycle + Mth.PI) * 1.5F * amount;
        this.rightArm.xRot = Mth.cos(cycle + Mth.PI) * 1.1F * amount;
        this.leftArm.xRot = Mth.cos(cycle) * 1.1F * amount;

        float bob = Math.abs(Mth.cos(cycle)) * 1.4F * amount;
        this.body.y -= bob;
        this.head.y -= bob;
        this.rightArm.y -= bob;
        this.leftArm.y -= bob;

        // Body and head lean into the run
        this.body.xRot += 0.25F * amount;
        this.body.yRot += Mth.cos(cycle) * 0.15F * amount;

        // Floppy ears flap while running
        float flap = Mth.sin(ageInTicks * 0.9F) * 0.15F * amount + 0.3F * amount;
        this.rightEar.zRot -= flap;
        this.leftEar.zRot += flap;

        // Aggressive stance: dagger arm raised, off hand ready
        if (entity.isAggressive()) {
            this.rightArm.xRot -= 1.2F;
            this.rightArm.zRot -= 0.1F;
            this.leftArm.xRot -= 0.5F;
            this.leftArm.zRot += 0.1F;
        }

        this.animate(entity.idleAnimationState, GoblinAnimations.IDLE, ageInTicks);
        this.animate(entity.attackAnimationState, GoblinAnimations.ATTACK, ageInTicks);
    }
}
