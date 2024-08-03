package me.kimovoid.microhud.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import me.kimovoid.microhud.MicroHUD;
import net.minecraft.client.Minecraft;

public class TickListener {

    private int tpsTimer = 20;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent ev) {
        if (this.tpsTimer == 0) {
            this.tpsTimer = 20;
            if (Minecraft.getMinecraft().isSingleplayer()) {
                try {
                    MicroHUD.INSTANCE.tps.getClientTPS();
                    MicroHUD.INSTANCE.mobCaps.getClientMobCap();
                } catch (NullPointerException ignored) {}
            }
        }

        this.tpsTimer--;
    }
}
