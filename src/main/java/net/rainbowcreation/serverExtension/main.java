package net.rainbowcreation.serverExtension;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.rainbowcreation.serverExtension.utils.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class main {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 15*20, 2));
        event.player.sendMessage(new TextComponentString("please login or register\n/login <password>\nor\n/register <password> <password>"));
    }
}