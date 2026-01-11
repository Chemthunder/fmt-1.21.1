package net.watchbox.fmt.client.entity.render;

import com.nitron.nitrogen.render.RenderUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.entity.MothersLoveEntity;

public class MothersLoveEntityRenderer extends EntityRenderer<MothersLoveEntity> {
    private final Identifier KILL_TEXTURE = Fmt.id("textures/render/love_kfx");


    public MothersLoveEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(MothersLoveEntity entity) {
        return KILL_TEXTURE;
    }

    @Override
    public void render(MothersLoveEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(
                        RenderLayer.getEntitySolid(
                                KILL_TEXTURE
                        )
                ),
                entity.getBlockPos(),
                entity.area,
                50,
                0
        );

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
