package com.poopstuff.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import com.poopstuff.PoopAndStuff;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item POOP = registerItem("poop", new Item(new Item.Settings()));
    public static final Item SLIMY_POOP = registerItem("slimy_poop", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(PoopAndStuff.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PoopAndStuff.LOGGER.info("Registering Mod Items for " + PoopAndStuff.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(POOP);
            entries.add(SLIMY_POOP);
        });
    }
}