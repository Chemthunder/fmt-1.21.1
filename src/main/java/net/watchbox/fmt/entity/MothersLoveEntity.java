package net.watchbox.fmt.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MothersLoveEntity extends Entity {
    public int area = 0;
    public int maxArea = 32;

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

    public void killAnimation(LivingEntity victim, World world) {

    }
}
