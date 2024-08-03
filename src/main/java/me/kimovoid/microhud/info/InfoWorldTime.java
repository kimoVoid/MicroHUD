package me.kimovoid.microhud.info;

import net.minecraft.util.MathHelper;

public class InfoWorldTime extends InfoLine {

    public InfoWorldTime(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        long day = MathHelper.floor_double((double)(this.mc.theWorld.getWorldTime() / 24000L));
        float dayTime = (this.mc.theWorld.getCelestialAngle(1.0f) * 24.0F + 12.0F) % 24.0F;
        int h = (int)Math.floor(dayTime);
        int m = (int)Math.floor(dayTime * 60.0F) - h * 60;
        int s = (int)Math.floor(dayTime * 3600.0F) - h * 3600 - m * 60;
        return String.format("MC time: (day %s) %02d:%02d:%02d", day, h, m, s);
    }
}