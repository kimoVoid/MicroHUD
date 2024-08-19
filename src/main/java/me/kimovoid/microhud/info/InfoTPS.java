package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.TPSData;

public class InfoTPS extends InfoLine {

    public InfoTPS(int order) {
        super(order);
    }

    @Override
    public boolean canRender() {
        return MicroHUD.INSTANCE.tps.tps != -1;
    }

    @Override
    public String getLineString() {
        return String.format("Server %s", getTPS());
    }

    /* Will be used for tab list soon(tm) */
    public static String getTPS() {
        TPSData tpsData = MicroHUD.INSTANCE.tps;
        return String.format("TPS: %s%s §r(MSPT: %s%s§r)",
                tpsData.getTPSColor(), tpsData.tps, tpsData.getMSPTColor(), tpsData.mspt);
    }
}