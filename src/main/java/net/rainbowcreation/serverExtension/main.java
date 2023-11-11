package net.rainbowcreation.serverExtension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.MobEffects;
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
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class main {
    @Mod.Instance
    public static main instance;
    public static File config;
    public static final int staticTimeInTicks = Confighandler.TIME * 20;

    public static int timeInTicks = staticTimeInTicks;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Confighandler.registerConfig(event);
    }
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        PlayerList playerList = world.getMinecraftServer().getPlayerList();
        if (timeInTicks == 0) {
            int amount = 0;
            for (Entity entity : world.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    EntityItem item = (EntityItem) entity;
                    List<String> whitelist = Arrays.asList(Confighandler.ITEM_WHITELIST);
                    if (!whitelist.contains(((ResourceLocation) Item.REGISTRY.getNameForObject(item.getItem().getItem())).toString())) {
                        item.setDead();
                        amount++;
                    }
                }
            }
            playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Cleared " + TextFormatting.RED + amount + TextFormatting.RESET + " items!"));
            timeInTicks = staticTimeInTicks;
        }
        for (int i: Confighandler.WARNING_TIME_LIST) {
            if (i * 20 == timeInTicks) {
                if (i % 60 > 1)
                    playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Items will be cleared in " + TextFormatting.RED + i % 60 + TextFormatting.RESET + " minutes!"));
                else
                    playerList.sendMessage((ITextComponent) new TextComponentString(TextFormatting.BOLD + "[Clear Lag] " + TextFormatting.RESET + "Items will be cleared in " + TextFormatting.RED + i + TextFormatting.RESET + " seconds!"));
            }
            if (timeInTicks > 0)
                timeInTicks--;
        }
    }
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 15*20, 2));
        event.player.sendMessage(new TextComponentString(TextFormatting.BOLD + "[Auth] " + TextFormatting.RESET + "please login or register\n/login <password>\nor\n/register <password> <password>"));
    }
}