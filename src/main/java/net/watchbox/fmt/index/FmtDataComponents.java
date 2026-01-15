package net.watchbox.fmt.index;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.watchbox.fmt.Fmt;

import java.util.LinkedHashMap;
import java.util.Map;

public interface FmtDataComponents {
    Map<ComponentType<?>, Identifier> COMPONENTS = new LinkedHashMap<>();

    ComponentType<Integer> CANVAS_PETALS = create("canvas_petals", new ComponentType.Builder<Integer>()
            .codec(Codec.INT)
            .build()
    );

    ComponentType<Boolean> EPITHET_TWO_HANDED = create("epithet_two_handed", new ComponentType.Builder<Boolean>()
            .codec(Codec.BOOL)
            .build()
    );

    static <T extends ComponentType<?>> T create(String name, T component) {
        COMPONENTS.put(component, Fmt.id(name));
        return component;
    }

    static void index() {
        COMPONENTS.keySet().forEach(component -> {
            Registry.register(Registries.DATA_COMPONENT_TYPE, COMPONENTS.get(component), component);
        });
    }
}
