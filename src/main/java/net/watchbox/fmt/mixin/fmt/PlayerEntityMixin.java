package net.watchbox.fmt.mixin.fmt;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.watchbox.api.DivineEntity;
import net.watchbox.fmt.entity.MothersLoveEntity;
import net.watchbox.fmt.index.FmtDataComponents;
import net.watchbox.fmt.item.BeautysCanvasItem;
import net.watchbox.fmt.utils.DimensionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends net.minecraft.entity.LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void fmt$regainPetal(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (target instanceof LivingEntity victim) {
            if (player.getMainHandStack().getItem() instanceof BeautysCanvasItem) {
                if (victim.hasVehicle() && victim.getVehicle() instanceof MothersLoveEntity mother) {

                } else {
                    ItemStack stack = player.getMainHandStack();
                    if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                        if (player.getAttackCooldownProgress(0.5F) > 0.9F) {
                            if (stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) != 6) {
                                stack.set(FmtDataComponents.CANVAS_PETALS, stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) + 1);
                            }
                        }
                    }
                }
            }

            if (victim.hasVehicle()) {
                if (player.isSneaking()) {
                    if (victim.getVehicle() instanceof MothersLoveEntity entity) {
                        entity.discard();
                    }
                }
            }
        }
    }

    @Inject(method = "shouldDismount", at = @At("HEAD"), cancellable = true)
    private void fmt$noMoreDismountForYouBih(CallbackInfoReturnable<Boolean> cir) {
        if (this.getVehicle() instanceof DivineEntity) {
            cir.setReturnValue(false);
        }
    }

    @ModifyReturnValue(method = "getBlockInteractionRange", at = @At("RETURN"))
    private double stepOneSayYoullGetItDone(double original) {
        PlayerEntity living = (PlayerEntity) (Object) this;

        if (living.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
            if (!living.isCreative()) {
                return 0;
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "getBlockBreakingSpeed", at = @At("RETURN"))
    private float stepTwoBidTheBoozeAdieu(float original) {
        PlayerEntity living = (PlayerEntity) (Object) this;

        if (living.getWorld().getRegistryKey() == DimensionUtils.waitingRoomKey) {
            if (!living.isCreative()) {
                return 0;
            }
        }
        return original;
    }
}
