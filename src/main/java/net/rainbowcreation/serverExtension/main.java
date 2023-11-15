package net.rainbowcreation.serverExtension;

import ibxm.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
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
import java.util.Spliterator;

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
        System.out.println("Mode:" +Reference.MODE);
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
            if (Reference.MODE.equals("server")) {
                playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Cleared " + TextFormatting.RED + amount + TextFormatting.RESET + " items!"));
            }
            timeInTicks = staticTimeInTicks;
        }
        switch (Reference.MODE) {
            case ("server"): {
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
                break;
            }
            case ("lobby"): {
                if (timeInTicks%20 != 0)
                    break;
                int[] time = Time.getCurrentTime();
                if (time[0] >= 3 && time[0] < 6) {
                    TextComponentString text = new TextComponentString("Time remaining ");
                    if (time[0] < 5)
                        text.appendSibling(new TextComponentString(TextFormatting.RED + String.valueOf(5 - time[0]) + TextFormatting.RESET + ":"));
                    String minute = String.valueOf(59 - time[1]);
                    String second = String.valueOf(59 - time[2]);
                    if (minute.length() == 1)
                        minute = "0" + minute;
                    if (second.length() == 1)
                        second = "0" + second;
                    SPacketTitle timeTitle = new SPacketTitle(SPacketTitle.Type.SUBTITLE, text.appendSibling(new TextComponentString(TextFormatting.RED + minute + TextFormatting.RESET + ":" + TextFormatting.RED + second)), 0, 20, 0);
                    for (EntityPlayerMP playerMP : plist) {
                        playerMP.connection.sendPacket(Reference.PACKET_MAINTENANCE);
                        playerMP.connection.sendPacket(timeTitle);
                    }
                }
                else if (time[0] == 6 && time[1] == 0 && time[2] == 0)
                    playerList.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Maintenance Completed] " + TextFormatting.RESET + "please reconnect the server to enter the portal!"));
                break;
            }
        }
        if (timeInTicks > 0)
            timeInTicks--;
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //V only in looby
        EntityPlayer player = event.player;
        switch (Reference.MODE) {
            case ("lobby"): {
                double x, y, z;
                x = -23.5;
                y = 63.5;
                z = 0.5;
                player.setPositionAndUpdate(x, y, z);
                player.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Queue] " + TextFormatting.RESET + "enter the portal to join queue"));
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                SPacketTitle packetActionBar = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, new TextComponentString(player.getName()), 20,120,20);
                playerMP.connection.sendPacket(Reference.PACKET_TITLE);
                playerMP.connection.sendPacket(Reference.PACKET_SUB_TITLE);
                playerMP.connection.sendPacket(packetActionBar);
                break;
            }
            case ("server"): {
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 60 * 20, 4));
                player.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Auth] " + TextFormatting.RESET + "please login or register\n/login <password>\nor\n/register <password> <password>"));
                break;
            }
        }
        /*
        if (getSkinStorage().getSkin(event.player.func_110124_au()) == SkinStorage.DEFAULT_SKIN)
            getSkinStorage().setSkin(event.player.func_110124_au(), MojangSkinProvider.getSkin(event.player.func_146103_bH().getName()));
        applySkin((EntityPlayerMP)event.player, getSkinStorage().getSkin(event.player.func_110124_au()));
        */
    }
    /*

    @SubscribeEvent
    public void onPlayerLeaving(PlayerEvent.PlayerLoggedOutEvent event) {
        getSkinStorage().removeSkin(event.player.func_110124_au());
    }

    public static void onClosingServer(FMLServerStoppingEvent ev
                                       ent) {
        for (EntityPlayerMP player : server.func_184103_al().func_181057_v())
            getSkinStorage().removeSkin(player.func_110124_au());
    }

     */
}
