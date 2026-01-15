package net.watchbox.fmt.index;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.watchbox.fmt.Fmt;

import java.util.LinkedHashMap;
import java.util.Map;

public interface FmtSounds {
    Map<SoundEvent, Identifier> SOUNDS = new LinkedHashMap<>();

    SoundEvent EPITHET_EXECUTE = create("item.epithet_execute");

    private static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Fmt.id(name));
        SOUNDS.put(soundEvent, Fmt.id(name));
        return soundEvent;
    }

    static void index() {
        SOUNDS.keySet().forEach(soundEvent -> {
            Registry.register(Registries.SOUND_EVENT, SOUNDS.get(soundEvent), soundEvent);
        });
    }
}
