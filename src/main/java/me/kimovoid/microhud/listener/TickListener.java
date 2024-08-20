package me.kimovoid.microhud.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import me.kimovoid.microhud.data.DataMobCaps;
import me.kimovoid.microhud.data.DataTPS;
import net.minecraft.client.Minecraft;

public class TickListener {

    private int tpsTimer = 20;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent ev) {
        if (this.tpsTimer == 0) {
            this.tpsTimer = 20;
            if (Minecraft.getMinecraft().isSingleplayer()) {
                try {
                    DataTPS.INSTANCE.getClientTPS();
                    DataMobCaps.INSTANCE.getClientMobCap();
                } catch (NullPointerException ignored) {}
            }
        }

        this.tpsTimer--;
    }
}
