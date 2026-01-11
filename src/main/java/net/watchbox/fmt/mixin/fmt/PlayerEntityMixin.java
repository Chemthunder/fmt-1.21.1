package net.watchbox.fmt.mixin.fmt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.watchbox.fmt.entity.MothersLoveEntity;
import net.watchbox.fmt.index.FmtDataComponents;
import net.watchbox.fmt.index.FmtEntities;
import net.watchbox.fmt.item.BeautysCanvasItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
                    mother.killAnimation(victim, player.getWorld());
                } else {
                    ItemStack stack = player.getMainHandStack();
                    if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                        if (stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) != 6) {
                            stack.set(FmtDataComponents.CANVAS_PETALS, stack.getOrDefault(FmtDataComponents.CANVAS_PETALS, 0) + 1);
                        }
                    }
                }
            }
        }
    }
}
