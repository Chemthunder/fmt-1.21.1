package net.watchbox.fmt.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.watchbox.fmt.Fmt;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class MothersLoveEntityModel extends Model {
	private final ModelPart sigil;

    public MothersLoveEntityModel(ModelPart sigil) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.sigil = sigil;
    }

    public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sigil = modelPartData.addChild("sigil", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.sigil.render(matrices, vertices, light, overlay, color);
    }
}