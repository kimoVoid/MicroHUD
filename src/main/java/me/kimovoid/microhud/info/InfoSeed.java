package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;
import net.minecraft.client.Minecraft;

public class InfoSeed extends InfoLine {

    public InfoSeed(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        return String.format("Seed: %s", Minecraft.getMinecraft().isSingleplayer()
                ? Minecraft.getMinecraft().getIntegratedServer().worldServerForDimension(0).getSeed()
                : MicroHUD.INSTANCE.seed);
    }
}