package net.watchbox.fmt.mixin.fmt;

import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.watchbox.fmt.entity.MothersLoveEntity;
import net.watchbox.fmt.index.FmtEntities;
import net.watchbox.fmt.index.FmtItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void fmt$killEffect(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity victim = (LivingEntity) (Object) this;
        Entity attacker = victim.getAttacker();

        if (attacker instanceof PlayerEntity player) {
            if (player.getMainHandStack().isOf(FmtItems.BEAUTYS_CANVAS)) {
                MothersLoveEntity love = new MothersLoveEntity(FmtEntities.MOTHERS_LOVE, getWorld());
                love.setPosition(victim.getPos());
                victim.startRiding(love);
                victim.setVelocity(0, 0, 0);

                if (getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnEntity(love);
                }
                cir.setReturnValue(true);
                victim.setHealth(victim.getMaxHealth());
            }
        }
    }
}
