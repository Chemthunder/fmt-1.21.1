package net.watchbox.fmt.mixin.fmt.client;

import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.watchbox.fmt.entity.MothersLoveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BipedEntityModel.class})
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> {
    @Inject(
            method = {"setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V"},
            at = {@At("HEAD")}
    )
    private void fmt$setPoseForSitting(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof MothersLoveEntity) {
            this.riding = false;
        }
    }

    // ty aco :3
}