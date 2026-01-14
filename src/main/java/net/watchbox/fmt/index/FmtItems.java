package net.watchbox.fmt.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.watchbox.fmt.Fmt;
import net.watchbox.fmt.item.BeautysCanvasItem;

import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface FmtItems {

    Item BEAUTYS_CANVAS = create("beautys_canvas", BeautysCanvasItem::new, new Item.Settings()
            .maxCount(1)
            .attributeModifiers(BeautysCanvasItem.createAttributeModifiers())
    );

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Fmt.id(name), item);
    }

    static void index() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(FmtItems::addCombatEntries);

        modifyItemNameColor(BEAUTYS_CANVAS, 0xfcdd8d);
    }

    private static void addCombatEntries(FabricItemGroupEntries entries) {
        entries.add(BEAUTYS_CANVAS);
    }
}
