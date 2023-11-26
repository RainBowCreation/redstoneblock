package net.rainbowcreation.redstoneblock;

import net.rainbowcreation.redstoneblock.utils.IString;
import net.rainbowcreation.redstoneblock.utils.Reference;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]")
public class Main {
  public static Logger LOGGER = FMLLog.log;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) throws Exception {
    LOGGER = event.getModLog();
    for (String txt: Reference.HEADER)
      LOGGER.info(txt);
    LOGGER.info(IString.genHeader(Reference.NAME+":"+Reference.VERSION));
  }
}