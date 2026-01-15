package net.watchbox.fmt.utils;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.watchbox.fmt.Fmt;

public class DimensionUtils {
    public static RegistryKey<World> exileKey = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Fmt.MOD_ID, "exile"));
    public static RegistryKey<World> waitingRoomKey = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(Fmt.MOD_ID, "waiting_room"));
}
