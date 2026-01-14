package net.watchbox.fmt.client;

import net.fabricmc.api.ClientModInitializer;
import net.watchbox.fmt.index.FmtEntities;
import net.watchbox.fmt.index.FmtEntityModelLayers;

public class FmtClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FmtEntities.clientIndex();
        FmtEntityModelLayers.index();
    }
}
