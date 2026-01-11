package net.watchbox.fmt.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.client.entity.render.MothersLoveEntityRenderer;
import net.watchbox.fmt.entity.MothersLoveEntity;

public interface FmtEntities {
    EntityType<MothersLoveEntity> MOTHERS_LOVE = create(
            "mothers_love",
            EntityType.Builder.create(
                    MothersLoveEntity::new,
                    SpawnGroup.MISC
            ).dimensions(1.6f, 0.3f)
    );

    static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Fmt.id(name));
        return Registry.register(Registries.ENTITY_TYPE, key.getValue(), builder.build(String.valueOf(key)));
    }

    static void index() {
//        // Entities are Registered Statically
        //   FabricDefaultAttributeRegistry.register(SHOCKWAVE, ShockwaveEntity.createAttribute());
    }

    static void clientIndex() {
        EntityRendererRegistry.register(MOTHERS_LOVE, MothersLoveEntityRenderer::new);
    }
}
