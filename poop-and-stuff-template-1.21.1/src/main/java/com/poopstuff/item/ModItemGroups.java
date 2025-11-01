package com.poopstuff.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import com.poopstuff.PoopAndStuff;
import com.poopstuff.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup POOP_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(PoopAndStuff.MOD_ID, "poopitems"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.POOP))
                    .displayName(Text.translatable("itemgroup.PoopAndStuff.poopitems"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.POOP);
                        entries.add(ModItems.SLIMY_POOP);
                    }).build());

    public static final ItemGroup POOP_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(PoopAndStuff.MOD_ID, "poopblocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.POOP_BLOCK))
                    .displayName(Text.translatable("itemgroup.PoopAndStuff.poopblocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.POOP_BLOCK);
                        entries.add(ModBlocks.SLIMY_POOP_VINES);

                    }).build());


    public static void registerItemGroups() {
        PoopAndStuff.LOGGER.info("Registering Item Groups for " + PoopAndStuff.MOD_ID);
    }
}