package net.watchbox.fmt.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.client.entity.model.MothersLoveEntityModel;

import java.util.LinkedHashMap;
import java.util.Map;

public interface FmtEntityModelLayers {
    Map<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider> MODEL_LAYERS = new LinkedHashMap<>();

    EntityModelLayer MOTHERS_LOVE = createModelLayer("mothers_love", MothersLoveEntityModel::getTexturedModelData);

    private static EntityModelLayer createModelLayer(String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer entityModelLayer = createMain(name);
        MODEL_LAYERS.put(entityModelLayer, provider);
        return entityModelLayer;
    }

    private static EntityModelLayer createMain(String id) {
        return create(id, "main");
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Fmt.id(id), layer);
    }

    static void index() {
        MODEL_LAYERS.forEach(EntityModelLayerRegistry::registerModelLayer);
    }
}
