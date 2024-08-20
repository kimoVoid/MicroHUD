package me.kimovoid.microhud.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.kimovoid.microhud.data.DataMobCaps;
import me.kimovoid.microhud.data.DataStorage;
import me.kimovoid.microhud.data.DataTPS;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;

public class EventListener {

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load ev) {
        DataStorage.INSTANCE.resetStorage();
        DataTPS.INSTANCE.resetTPS();
        DataMobCaps.INSTANCE.resetMobCaps();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent ev) {
        if (!Minecraft.getMinecraft().isSingleplayer()
                && ev.message.getUnformattedText().startsWith("Seed: ")) {
            DataStorage.INSTANCE.seed = Long.parseLong(ev.message.getUnformattedText().replace("Seed: ", ""));
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                    new ChatComponentText("§e§l[MicroHUD]§r: Client seed has been set"));
        }
    }
}