package net.rainbowcreation.redstoneblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.rainbowcreation.redstoneblock.utils.IString;
import net.rainbowcreation.redstoneblock.utils.Reference;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

import static net.rainbowcreation.redstoneblock.config.RedstoneBlockConfig.blockedList;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]")
public class Main {
  public static Logger LOGGER = FMLLog.log;
  public static final Set<Block> redstoneRelatedBlocks = new HashSet<>();

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) throws Exception {
    LOGGER = event.getModLog();
    for (String txt: Reference.HEADER)
      LOGGER.info(txt);
    LOGGER.info(IString.genHeader(Reference.NAME+":"+Reference.VERSION));

    if (blockedList.BUTTON) {
      redstoneRelatedBlocks.add(Blocks.STONE_BUTTON);
      redstoneRelatedBlocks.add(Blocks.WOODEN_BUTTON);
    }
    if (blockedList.COMPARATOR) {
      redstoneRelatedBlocks.add(Blocks.UNPOWERED_COMPARATOR);
      redstoneRelatedBlocks.add(Blocks.POWERED_COMPARATOR);
    }
    if (blockedList.DAYLIGHT_SENSOR) {
      redstoneRelatedBlocks.add(Blocks.DAYLIGHT_DETECTOR);
      redstoneRelatedBlocks.add(Blocks.DAYLIGHT_DETECTOR_INVERTED);
    }
    if (blockedList.DOOR) {
      redstoneRelatedBlocks.add(Blocks.ACACIA_DOOR);
      redstoneRelatedBlocks.add(Blocks.DARK_OAK_DOOR);
      redstoneRelatedBlocks.add(Blocks.IRON_DOOR);
      redstoneRelatedBlocks.add(Blocks.BIRCH_DOOR);
      redstoneRelatedBlocks.add(Blocks.JUNGLE_DOOR);
      redstoneRelatedBlocks.add(Blocks.OAK_DOOR);
      redstoneRelatedBlocks.add(Blocks.SPRUCE_DOOR);
    }
    if (blockedList.NOTE_BLOCK) {
      redstoneRelatedBlocks.add(Blocks.NOTEBLOCK);
    }
    if (blockedList.DISPENSER) {
      redstoneRelatedBlocks.add(Blocks.DISPENSER);
    }
    if (blockedList.DROPPER) {
      redstoneRelatedBlocks.add(Blocks.DROPPER);
    }
    if (blockedList.FENCE_GATE) {
      redstoneRelatedBlocks.add(Blocks.ACACIA_FENCE_GATE);
      redstoneRelatedBlocks.add(Blocks.BIRCH_FENCE_GATE);
      redstoneRelatedBlocks.add(Blocks.JUNGLE_FENCE_GATE);
      redstoneRelatedBlocks.add(Blocks.OAK_FENCE_GATE);
      redstoneRelatedBlocks.add(Blocks.SPRUCE_FENCE_GATE);
      redstoneRelatedBlocks.add(Blocks.DARK_OAK_FENCE_GATE);
    }
    if (blockedList.LEVER) {
      redstoneRelatedBlocks.add(Blocks.LEVER);
    }
    if (blockedList.TRAP_DOOR) {
      redstoneRelatedBlocks.add(Blocks.TRAPDOOR);
      redstoneRelatedBlocks.add(Blocks.IRON_TRAPDOOR);
    }
    if (blockedList.TRAP_CHEST) {
      redstoneRelatedBlocks.add(Blocks.TRAPPED_CHEST);
    }
    if (blockedList.OBSERVER) {
      redstoneRelatedBlocks.add(Blocks.OBSERVER);
    }
    if (blockedList.HOPPER) {
      redstoneRelatedBlocks.add(Blocks.HOPPER);
    }
    if (blockedList.PISTON) {
      redstoneRelatedBlocks.add(Blocks.PISTON);
    }
    if (blockedList.STICKY_PISTON) {
      redstoneRelatedBlocks.add(Blocks.STICKY_PISTON);
    }
    if (blockedList.PRESSURE_PLATE) {
      redstoneRelatedBlocks.add(Blocks.STONE_PRESSURE_PLATE);
      redstoneRelatedBlocks.add(Blocks.WOODEN_PRESSURE_PLATE);
      redstoneRelatedBlocks.add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
      redstoneRelatedBlocks.add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
    }
    if (blockedList.REDSTONE_BLOCK) {
      redstoneRelatedBlocks.add(Blocks.REDSTONE_BLOCK);
    }
    if (blockedList.REDSTONE_DUST) {
      redstoneRelatedBlocks.add(Blocks.REDSTONE_WIRE);
    }
    if (blockedList.REDSTONE_LAMP) {
      redstoneRelatedBlocks.add(Blocks.REDSTONE_LAMP);
    }
    if (blockedList.TNT) {
      redstoneRelatedBlocks.add(Blocks.TNT);
    }
    if (blockedList.REDSTONE_TORCH) {
      redstoneRelatedBlocks.add(Blocks.REDSTONE_TORCH);
    }
    if (blockedList.REPEATER) {
      redstoneRelatedBlocks.add(Blocks.POWERED_REPEATER);
      redstoneRelatedBlocks.add(Blocks.UNPOWERED_COMPARATOR);
    }
    if (blockedList.TRIPWIRE_HOOK) {
      redstoneRelatedBlocks.add(Blocks.TRIPWIRE_HOOK);
    }
  }
}