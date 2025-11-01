package com.poopstuff.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import com.poopstuff.PoopAndStuff;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ModBlocks {
    public static final Block SLIMY_POOP_VINES = registerBlock("slimy_poop_vines",
            new Block(AbstractBlock.Settings.create()
                    .strength(0.2f)
                    .slipperiness(0.4f)
                    .noCollision()
                    .sounds(BlockSoundGroup.SLIME)));

    public static final Block POOP_BLOCK = registerBlock("poop_block",
            new Block(AbstractBlock.Settings.create()
                    .noCollision()
                    .nonOpaque()
                    .strength(4f)
                    .requiresTool().sounds(BlockSoundGroup.SLIME)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(PoopAndStuff.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(PoopAndStuff.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        PoopAndStuff.LOGGER.info("Registering Mod Blocks for " + PoopAndStuff.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.SLIMY_POOP_VINES);
            entries.add(ModBlocks.POOP_BLOCK);
        });
    }
}