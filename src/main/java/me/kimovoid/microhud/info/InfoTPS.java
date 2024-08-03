package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.TPSData;

public class InfoTPS extends InfoLine {

    public InfoTPS(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        if (MicroHUD.INSTANCE.tps.tps == -1) {
            return "Server TPS: §7(no data)";
        }

        TPSData tpsData = MicroHUD.INSTANCE.tps;
        return String.format("Server TPS: %s%s §r(MSPT: %s%s§r)",
                tpsData.getTPSColor(), tpsData.tps, tpsData.getMSPTColor(), tpsData.mspt);
    }
}