package net.watchbox.fmt.mixin.fmt;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.watchbox.fmt.cca.entity.ImmobilizedEntityComponent;
import net.watchbox.fmt.cca.entity.PlayerFlashComponent;
import net.watchbox.fmt.entity.AbyssalRemnantEntity;
import net.watchbox.fmt.entity.MothersLoveEntity;
import net.watchbox.fmt.index.FmtDamageSources;
import net.watchbox.fmt.index.FmtEntities;
import net.watchbox.fmt.index.FmtItems;
import net.watchbox.fmt.index.FmtSounds;
import net.watchbox.fmt.utils.DimensionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
                if (victim.hasVehicle() && victim.getVehicle() instanceof MothersLoveEntity entity) {
                    entity.discard();
                    victim.damage(FmtDamageSources.burger(victim), victim.getMaxHealth() * victim.getMaxHealth());

                    if (getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.END_ROD,
                                victim.getX(),
                                victim.getY() + 1.0f,
                                victim.getZ(),
                                50,
                                0,
                                0,
                                0,
                                0.2
                        );
                    }

                    if (player instanceof ScreenShaker shaker) {
                        shaker.addScreenShake(5, 6);
                    }
                } else {
                    MothersLoveEntity love = new MothersLoveEntity(FmtEntities.MOTHERS_LOVE, getWorld());
                    love.setPosition(victim.getPos());
                    victim.startRiding(love);
                    victim.setVelocity(0, 0, 0);

                    if (getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.spawnEntity(love);
                    }
                    cir.setReturnValue(true);
                    victim.setHealth(victim.getMaxHealth());

                    PlayerFlashComponent flash = PlayerFlashComponent.KEY.get(player);
                    flash.flashTicks = 20;
                    flash.sync();
                }
            }

            if (player.getMainHandStack().isOf(FmtItems.CORONERS_EPITHET)) {
                if (getWorld() instanceof ServerWorld serverWorld) {
                    if (!victim.hasVehicle() && !(victim.getVehicle() instanceof AbyssalRemnantEntity)) {
                        AbyssalRemnantEntity remn = new AbyssalRemnantEntity(FmtEntities.ABYSSAL_REMNANT, serverWorld);

                        remn.setPosition(victim.getPos());
                        victim.startRiding(remn);

                        serverWorld.spawnEntity(remn);
                        cir.setReturnValue(true);
                        victim.setHealth(victim.getMaxHealth());


                        PlayerFlashComponent flash = PlayerFlashComponent.KEY.get(player);
                        flash.flashTicks = 20;
                        flash.sync();

                        Box box = new Box(victim.getBlockPos()).expand(50, 50, 50);
                        List<LivingEntity> entities = getWorld().getEntitiesByClass(
                                LivingEntity.class, box,
                                entity -> true
                        );

                        for (LivingEntity entity : entities) {
                            entity.playSound(FmtSounds.EPITHET_EXECUTE, 1, 1);
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInsideWall()Z"))
    private void fmt$noclip(CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (living instanceof ServerPlayerEntity player) {
            MinecraftServer server = living.getServer();
            if (server != null) {
                ServerWorld targetWorld = server.getWorld(DimensionUtils.waitingRoomKey);
                if (targetWorld != null) {
                    BlockPos spawnPos = targetWorld.getSpawnPos();

                    player.teleport(targetWorld, spawnPos.getX() + 5, spawnPos.getY() + 5, spawnPos.getZ() + 5, living.getYaw(), living.getPitch());
                }
            }
        }
    }


    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    private void cancelMovement(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        if ((Object) this instanceof LivingEntity player) {
            if (ImmobilizedEntityComponent.KEY.get(player).immobilizedTicks > 0) {
                cir.setReturnValue(Vec3d.ZERO);
            }
        }
    }

    @Inject(method = "applyFluidMovingSpeed", at = @At("HEAD"), cancellable = true)
    private void cancelFluidMovement(double gravity, boolean falling, Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        if ((Object) this instanceof LivingEntity player) {
            if (ImmobilizedEntityComponent.KEY.get(player).immobilizedTicks > 0) {
                cir.setReturnValue(Vec3d.ZERO);
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void fmt$unleashTheWolfInsideMe(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        ImmobilizedEntityComponent component = ImmobilizedEntityComponent.KEY.get(living);

        if (component.immobilizedTicks > 0) {
            component.immobilizedTicks = 0;
            component.sync();
        }
    }
}
