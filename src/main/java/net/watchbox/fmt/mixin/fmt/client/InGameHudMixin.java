package net.watchbox.fmt.mixin.fmt.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.cca.entity.PlayerFlashComponent;
import net.watchbox.fmt.utils.DimensionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private static final Identifier VIGNETTE = Fmt.id("textures/render/vignette_death.png");

    @Unique
    private static final Identifier WAITING_ROOM_VIGNETTE = Fmt.id("textures/render/white_vignette.png");

    @Unique
    private static final Identifier WHITE_FLASH = Fmt.id("textures/render/flash.png");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void extraOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {

            if (player.getWorld().getRegistryKey() == DimensionUtils.exileKey) {
                this.renderOverlay(context, VIGNETTE, 0.5f);
            }

            if (player.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
                this.renderOverlay(context, WAITING_ROOM_VIGNETTE, 0.9f);
                this.renderOverlay(context, WHITE_FLASH, 0.2f);
            }

            int ticks = PlayerFlashComponent.KEY.get(player).flashTicks;
            if (ticks > 0) {
                float opacity = ticks > 50 ? 1f : ticks / 50.0f;
                this.renderOverlay(context, WHITE_FLASH, opacity);
            }
        }
    }
}
