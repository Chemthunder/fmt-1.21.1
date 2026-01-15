package net.watchbox.fmt.mixin.fmt.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.utils.DimensionUtils;
import org.intellij.lang.annotations.JdkConstants;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements SynchronousResourceReloader, AutoCloseable {

    @Unique
    private static final Identifier SILLY = Fmt.id("textures/environment/exile_sun.png");

    @Unique
    private static final Identifier WAITING_ROOM = Fmt.id("textures/environment/waiting_room_sun.png");

    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    private void noMoreCloudsInExile(MatrixStack matrices, Matrix4f matrix4f, Matrix4f matrix4f2, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            if ((player.getWorld().getRegistryKey() == DimensionUtils.exileKey) || (player.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey)) {
                ci.cancel();
            }
        }
    }

    @WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"))
    private void customSunTexture(int texture, Identifier id, Operation<Void> original) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            if (player.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
                original.call(0, WAITING_ROOM);
            } else {
                original.call(texture, id);
            }
        }
    }

    @WrapOperation(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V"))
    private void customSunTextureForExile(int texture, Identifier id, Operation<Void> original) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            if (player.getWorld().getRegistryKey() == DimensionUtils.exileKey) {
                original.call(texture, SILLY);
            } else {
                original.call(texture, id);
            }
        }
    }

}
