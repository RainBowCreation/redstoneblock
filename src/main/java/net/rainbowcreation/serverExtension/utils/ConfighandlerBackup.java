package net.rainbowcreation.serverExtension.utils;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.rainbowcreation.serverExtension.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Reference.MODID)
public class ConfighandlerBackup {
    public static Configuration config;
    public static int TIME = 60*20;
    public static int WARNING_TIME =  300;
    public static List<Integer> WARNING_TIME_LIST  = new ArrayList<>();
    public static String[] ITEM_WHITELIST = new String[] { "minecraft:diamond", "minecraft:diamond_block" };

    public static void init(File file) {
        config = new Configuration();
        String category;
        category = "general";
        config.addCustomCategoryComment(category,"Time in seconds between each clear");
        TIME = config.getInt("time", category, 1800, 60, 3600, "default 1800 min 60 max 3600");
        config.addCustomCategoryComment(category, "Time in seconds clear is announced to chat before-hand, set to zero to disable warning");
        WARNING_TIME = config.getInt("warning time", category, 300, 0, 1800, "deffult 300 min 0 max 1800");
        config.addCustomCategoryComment(category, "List of items that whitelist to not remove");
        ITEM_WHITELIST = config.getStringList("ItemWhiteList", category, new String[] { "minecraft:diamond", "minecraft:diamond_block" }, "example minecraft:diamond");
        int i = WARNING_TIME;
        while (i >= 1) {
            WARNING_TIME_LIST.add(i);
            i%=2;
        }
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        main.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
        main.config.mkdirs();
        init(new File(main.config.getPath(), Reference.MODID + ".cfg"));
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MODID))
            ConfigManager.load(Reference.MODID, Config.Type.INSTANCE);
    }
}
