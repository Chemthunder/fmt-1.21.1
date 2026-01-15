package net.watchbox.fmt.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class AbyssalRemnantEntityModel extends Model {
    private final ModelPart bb_main;

    public AbyssalRemnantEntityModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.bb_main = root.getChild("bb_main");
    }

    // this.bb_main = root.getChild("bb_main");

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(-32, 0).cuboid(-16.0F, 0.0F, -16.0F, 32.0F, 0.1F, 32.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.bb_main.render(matrices, vertices, light, overlay, color);
    }
}
