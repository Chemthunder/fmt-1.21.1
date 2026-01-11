package net.watchbox.fmt;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.watchbox.fmt.index.FmtDataComponents;
import net.watchbox.fmt.index.FmtEntities;
import net.watchbox.fmt.index.FmtItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fmt implements ModInitializer {
	public static final String MOD_ID = "fmt";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        FmtItems.index();
        FmtDataComponents.index();
        FmtEntities.index();

		LOGGER.info("Hello Fabric world!");
	}
}