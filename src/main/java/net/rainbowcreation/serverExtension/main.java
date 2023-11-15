package net.rainbowcreation.serverExtension;

import ibxm.Player;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.rainbowcreation.serverExtension.utils.Confighandler;
import net.rainbowcreation.serverExtension.utils.Reference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.rainbowcreation.serverExtension.utils.Teleport;
import net.rainbowcreation.serverExtension.utils.Time;
import org.lwjgl.Sys;

import static net.rainbowcreation.serverExtension.utils.Confighandler.settings;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class main {
    @Mod.Instance
    public static main instance;
    public static File config;
    public static final int staticTimeInTicks = settings.TIME * 20;
    public static List<Integer> WARNING_TIME_LIST = new ArrayList<>();

    public static int timeInTicks = staticTimeInTicks;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        List<String> header = Arrays.asList("######################################################################################",
                "#  _____       _       ____                 _____                _   _               #",
                "# |  __ \\     (_)     |  _ \\               / ____|              | | (_)              #",
                "# | |__) |__ _ _ _ __ | |_) | _____      _| |     _ __ ___  __ _| |_ _  ___  _  __   #",
                "# |  _  // _` | | '_ \\|  _ < / _ \\ \\ /\\ / / |    | '__/ _ \\/ _`  | __| |/ _ \\| '_ \\  #",
                "# | | \\ \\ (_| | | | | | |_) | (_) \\ V  V /| |____|  | |  __/ (_| | |_| | (_) | | | | #",
                "# |_|  \\_\\__,_|_|_| |_|____/ \\___/ \\_/\\_/  \\_____|_|  \\___|\\__,_|\\__|_|\\___/|_|  |_| #",
                "#                                                                                    #",
                "################################################################sever-extension#######");
        for (String txt : header) {
            System.out.println(txt);
        }
        int i = settings.WARNING_TIME;
        while (i > 10) {
            WARNING_TIME_LIST.add(i);
            i/=2;
        }
        for (int j = 1; j <= 10; j++)
            WARNING_TIME_LIST.add(j);
        System.out.println(WARNING_TIME_LIST);
    }

    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        PlayerList playerList = world.getMinecraftServer().getPlayerList();
        List<EntityPlayerMP> plist = playerList.getPlayers();
        if (timeInTicks == 0) {
            int amount = 0;
            for (Entity entity : world.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    EntityItem item = (EntityItem) entity;
                    List<String> whitelist = Arrays.asList(Confighandler.whitelist.ITEM_WHITELIST);
                    if (!whitelist.contains(((ResourceLocation) Item.REGISTRY.getNameForObject(item.getItem().getItem())).toString())) {
                        item.setDead();
                        amount++;
                    }
                }
            }
            // V only in server
            /*
            playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Cleared " + TextFormatting.RED + amount + TextFormatting.RESET + " items!"));
            for (EntityPlayerMP player : plist) {
                player.addExperience(50);
            }
            playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Sorry Gift] " + TextFormatting.RESET + "Sorry for roll back here was " + TextFormatting.GREEN + "50" + TextFormatting.RESET + " exps. you will get this gift every 30 minutes!"));
            */
            // A only in server
            timeInTicks = staticTimeInTicks;
        } // V only in server
        /*
        for (int i: WARNING_TIME_LIST) {
            if (i * 20 == timeInTicks) {
                long[] lst = Time.secondToMinute(i);
                TextComponentString text = new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Items will be cleared :");
                if (lst[0] > 0)
                     text.appendSibling(new TextComponentString(" " + TextFormatting.RED + String.valueOf(lst[0]) + TextFormatting.RESET + " minutes"));
                if (lst[1] > 0)
                    text.appendSibling(new TextComponentString(" " + TextFormatting.RED + String.valueOf(lst[1]) + TextFormatting.RESET + " seconds"));
                text.appendText("!!.");
                playerList.sendMessage(text);
            }
        }
         */
        // A only in server
        if (timeInTicks > 0)
            timeInTicks--;
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        double x, y, z;
        x = -23.5;
        y = 63.5;
        z = 0.5;
        //Teleport.teleport(event.player, 0, x, y, z);
        event.player.setPositionAndUpdate(x, y, z);
        //event.player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 60*20, 4));
        event.player.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Queue] " + TextFormatting.RESET + "enter the portal to join queue")); // only in lobby
        //event.player.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Auth] " + TextFormatting.RESET + "please login or register\n/login <password>\nor\n/register <password> <password>"));
    }
}