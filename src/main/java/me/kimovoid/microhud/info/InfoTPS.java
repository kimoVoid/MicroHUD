package me.kimovoid.microhud.info;

import me.kimovoid.microhud.data.DataTPS;

public class InfoTPS extends InfoLine {

    public InfoTPS(int order) {
        super(order);
    }

    @Override
    public boolean canRender() {
        return DataTPS.INSTANCE.tps != -1;
    }

    @Override
    public String getLineString() {
        return String.format("Server %s", getTPS());
    }

    public static String getTPS() {
        return String.format("TPS: %s%s §r(MSPT: %s%s§r)",
                DataTPS.INSTANCE.getTPSColor(), DataTPS.INSTANCE.tps, DataTPS.INSTANCE.getMSPTColor(), DataTPS.INSTANCE.mspt);
    }
}