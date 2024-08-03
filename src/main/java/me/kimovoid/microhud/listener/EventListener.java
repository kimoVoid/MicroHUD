package me.kimovoid.microhud.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.MobCapData;
import me.kimovoid.microhud.data.TPSData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;

public class EventListener {

    @SubscribeEvent
    public void onJoinWorld(WorldEvent.Load ev) {
        MicroHUD.INSTANCE.ping = 0;
        MicroHUD.INSTANCE.tps = new TPSData();
        MicroHUD.INSTANCE.mobCaps = new MobCapData();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent ev) {
        if (!Minecraft.getMinecraft().isSingleplayer()
                && ev.message.getUnformattedText().startsWith("Seed: ")) {
            MicroHUD.INSTANCE.seed = Long.parseLong(ev.message.getUnformattedText().replace("Seed: ", ""));
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                    new ChatComponentText("§e§l[MicroHUD]§r: Client seed has been set"));
        }
    }
}
