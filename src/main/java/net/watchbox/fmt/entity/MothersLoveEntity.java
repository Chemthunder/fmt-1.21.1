package net.watchbox.fmt.entity;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.watchbox.fmt.index.FmtDamageSources;

public class MothersLoveEntity extends Entity {
    public int area = 0;
    public int maxArea = 9;

    public MothersLoveEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.area = nbt.getInt("area");
        this.maxArea = nbt.getInt("maxArea");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("area", area);
        nbt.putInt("maxArea", maxArea);
    }

    @Override
    public void tick() {
        if (area != maxArea) {
            area++;
        }
        super.tick();
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (this.hasPassenger(passenger)) {
            passenger.setPosition(this.getX(), this.getY() + 0.009999999776482582, this.getZ());
            // ty aco :33333
        }
        super.updatePassengerPosition(passenger, positionUpdater);
    }
}
