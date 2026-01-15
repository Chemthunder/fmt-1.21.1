package net.watchbox.fmt.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.watchbox.api.DivineEntity;
import net.watchbox.fmt.utils.DimensionUtils;

public class AbyssalRemnantEntity extends Entity implements DivineEntity {
    public int ticks = 0;
    public int beamRadius = 0;
    public int maxBeamRadius = 5;
    public float size = 3.0f;
    public float maxSize = 3;

    public boolean summonBeam = false;
    public boolean isPassengerDead = false;

    public AbyssalRemnantEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        ticks++;

        if (ticks == 40) {
            summonBeam = true;
            isPassengerDead = true;
            if (beamRadius != maxBeamRadius) {
                beamRadius++;
            }

            for (LivingEntity livingEntity : this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(3.0F, 100.0F, 3.0F))) {
                if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                    begone(serverPlayerEntity, serverPlayerEntity.getWorld());
                } else {
                    livingEntity.damage(livingEntity.getDamageSources().outOfWorld(), livingEntity.getMaxHealth() * 5);
                }
            }
        }

        if (ticks >= 100) {
            size -= 0.1f;
        }

        if (ticks >= 200) {
            this.discard();
        }


        super.tick();
    }

    private void begone(ServerPlayerEntity living, World world) {
        MinecraftServer server = living.getServer();
        if (server != null) {
            ServerWorld targetWorld = server.getWorld(DimensionUtils.exileKey);
            if (targetWorld != null) {
                BlockPos spawnPos = targetWorld.getSpawnPos();

                living.teleport(targetWorld, spawnPos.getX() + 5, spawnPos.getY() + 5, spawnPos.getZ() + 5, living.getYaw(), living.getPitch());
            }
        }
    }

    public float getSize() {
        return size;
    }


    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.ticks = nbt.getInt("ticks");
        this.beamRadius = nbt.getInt("beamRadius");
        this.maxBeamRadius = nbt.getInt("maxBeamRadius");
        this.size = nbt.getFloat("size");
        this.maxSize = nbt.getFloat("maxSize");

        this.summonBeam = nbt.getBoolean("summonBeam");
        this.isPassengerDead = nbt.getBoolean("isPassengerDead");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("ticks", ticks);
        nbt.putInt("beamRadius", beamRadius);
        nbt.putInt("maxBeamRadius", maxBeamRadius);
        nbt.putFloat("size", size);
        nbt.putFloat("maxSize", maxSize);

        nbt.putBoolean("summonBeam", summonBeam);
        nbt.putBoolean("isPassengerDead", isPassengerDead);
    }
}
