package net.rainbowcreation.serverExtension.utils;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MODID)
@Config(modid = Reference.MODID, name = Reference.NAME, category = "general")
public class Confighandler {
    public static Settings settings = new Settings();
    public static class Settings {
        @Config.Comment({"Time in seconds between each clear"})
        public int TIME = 1800;

        @Comment({"Time in seconds clear is announced to chat before-hand, set to zero to disable warning"})
        public int WARNING_TIME = 900;

        @Comment({"Set to false to disable item clearing"})
        public boolean clearItems = true;
    }

    public static Whitelist whitelist = new Whitelist();

    public static class Whitelist {
        @Comment({"Item IDs of items to be ignored when clearing, these items will not be cleared. Format: modid:itemid, ex: minecraft:diamond"})
        public String[] ITEM_WHITELIST = new String[] { "minecraft:diamond", "minecraft:diamond_block", "xpbook:xp_book" };
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("clearlag"))
            ConfigManager.load("clearlag", Config.Type.INSTANCE);
    }
}
