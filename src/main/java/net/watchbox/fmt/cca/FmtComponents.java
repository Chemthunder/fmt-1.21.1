package net.watchbox.fmt.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.watchbox.fmt.cca.entity.ImmobilizedEntityComponent;
import net.watchbox.fmt.cca.entity.PlayerFlashComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class FmtComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, PlayerFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerFlashComponent::new);
        registry.beginRegistration(LivingEntity.class, ImmobilizedEntityComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ImmobilizedEntityComponent::new);
    }
}
