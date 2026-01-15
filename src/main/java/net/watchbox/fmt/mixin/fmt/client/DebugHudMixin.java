package net.watchbox.fmt.mixin.fmt.client;

import com.sun.jna.platform.unix.X11;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.watchbox.fmt.utils.DimensionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Inject(method = "drawRightText", at = @At("HEAD"), cancellable = true)
    private void disableRightText(DrawContext context, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            if (player.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
                if (!player.isCreative()) {
                    ci.cancel();
                    context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("<o>"), 1, 1, 0xff0062);
                    context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("Can't ruin the fun now, could we?"), 1, 15, 0xff0062);

                    context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("Waiting Room"), 1, 30, 0xff0062);
                }
            }
        }
    }

    @Inject(method = "drawLeftText", at = @At("HEAD"), cancellable = true)
    private void disableLeftText(DrawContext context, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            if (player.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
                if (!player.isCreative()) {
                    ci.cancel();
                }
            }
        }
    }
}
