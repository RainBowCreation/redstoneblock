package net.rainbowcreation.serverExtension.utils;

import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.io.File;

public class Reference {
    public static final String MODID = "server_extension";
    public static final String NAME = "server_extension";
    public static final String VERSION = "1.12.2-1";
    public static final String MODE = "lobby"; //[server, lobby]
    public static final SPacketTitle PACKET_TITLE = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString("Welcome to") , 20, 120, 20);
    public static final SPacketTitle PACKET_SUB_TITLE = new SPacketTitle(SPacketTitle.Type.SUBTITLE, new TextComponentString("play." + TextFormatting.GREEN + "R" + TextFormatting.AQUA + "a" + TextFormatting.LIGHT_PURPLE + "i" + TextFormatting.RED + "n" + TextFormatting.YELLOW + "B" + TextFormatting.GREEN + "o" + TextFormatting.AQUA + "w" + TextFormatting.RESET + "Creation.net"), 20, 120, 20);
    public static final SPacketTitle PACKET_MAINTENANCE = new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString(TextFormatting.RED + "Daily Maintenance " + TextFormatting.RESET + "3:00-6:00" + TextFormatting.RED + "AM") , 0, 20, 0);
}