package net.watchbox.fmt.client.entity.render;

import com.nitron.nitrogen.render.RenderUtils;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.client.entity.model.MothersLoveEntityModel;
import net.watchbox.fmt.entity.MothersLoveEntity;
import net.watchbox.fmt.index.FmtEntityModelLayers;

public class MothersLoveEntityRenderer extends EntityRenderer<MothersLoveEntity> {
    private final Identifier KILL_TEXTURE = Fmt.id("textures/render/love_kfx.png");
    public static final Identifier TEXTURE = Fmt.id("textures/entity/new.png");
    private final MothersLoveEntityModel chainModel;


    public MothersLoveEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.chainModel = new MothersLoveEntityModel(ctx.getPart(FmtEntityModelLayers.MOTHERS_LOVE));
    }

     // this.plumeModel = new PlumeEntityModel(context.getPart(ModModelLayers.PLUME));

    @Override
    public Identifier getTexture(MothersLoveEntity entity) {
        return KILL_TEXTURE;
    }

    @Override
    public void render(MothersLoveEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * 1));
        matrices.translate(0, -4.4, 0);
        matrices.scale(3f, 3f, 3f);
        this.chainModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0xFFFFFF);

        matrices.pop();

        matrices.push();
        matrices.translate(-entity.getX(), -entity.getY(), -entity.getZ());
        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(
                        KILL_TEXTURE, true
                )),
                entity.getBlockPos(),
                entity.area,
                50,
                0
        );

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public boolean shouldRender(MothersLoveEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }


}
