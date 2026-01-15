package net.watchbox.fmt.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.watchbox.fmt.Fmt;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ImmobilizedEntityComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ImmobilizedEntityComponent> KEY = ComponentRegistry.getOrCreate(Fmt.id("immobilized"), ImmobilizedEntityComponent.class);
    private final LivingEntity player;
    public int immobilizedTicks = 0;

    public ImmobilizedEntityComponent(LivingEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }


    @Override
    public void tick() {
        if (immobilizedTicks > 0) {
            immobilizedTicks--;
            if (immobilizedTicks == 0) {
                sync();
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.immobilizedTicks = nbtCompound.getInt("immobilizedTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("immobilizedTicks", immobilizedTicks);
    }
}
