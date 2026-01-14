package net.watchbox.fmt.index;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.watchbox.fmt.Fmt;

public interface FmtDamageSources {
    RegistryKey<DamageType> BURGER = of("burger");

    static DamageSource burger(LivingEntity entity) {
        return entity.getDamageSources().create(BURGER); }

    private static RegistryKey<DamageType> of(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Fmt.id(name));
    }
}
