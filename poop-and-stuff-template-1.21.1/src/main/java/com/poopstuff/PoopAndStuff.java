package com.poopstuff;

import com.poopstuff.item.ModItems;
import com.poopstuff.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoopAndStuff implements ModInitializer {
	public static final String MOD_ID = "poop-and-stuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
		LOGGER.info("THIS MOD IS IN BETA");
	}
}