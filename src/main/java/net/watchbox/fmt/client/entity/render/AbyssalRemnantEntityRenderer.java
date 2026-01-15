package net.watchbox.fmt.client.entity.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.client.entity.model.AbyssalRemnantEntityModel;
import net.watchbox.fmt.entity.AbyssalRemnantEntity;
import net.watchbox.fmt.index.FmtEntityModelLayers;

public class AbyssalRemnantEntityRenderer extends EntityRenderer<AbyssalRemnantEntity> {
    public static final Identifier TEXTURE = Fmt.id("textures/entity/abyssal_remnant.png");
    private final Identifier KILL_TEXTURE = Fmt.id("textures/render/love_kfx.png");
    private final AbyssalRemnantEntityModel remnantModel;

    public AbyssalRemnantEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.remnantModel = new AbyssalRemnantEntityModel(ctx.getPart(FmtEntityModelLayers.ABYSSAL_REMNANT));
    }

    @Override
    public Identifier getTexture(AbyssalRemnantEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(AbyssalRemnantEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * 1));
        matrices.translate(0, -4.4, 0);
        if (!entity.isPassengerDead) {
            matrices.scale(3, 3, 3);
        }
        this.remnantModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0xFFFFFF);

        matrices.pop();

        matrices.push();
        if (entity.isPassengerDead) {
            matrices.scale(entity.getSize(), 0, entity.getSize());
        }
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
