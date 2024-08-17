package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;

public class InfoPosition extends InfoLine {

    public InfoPosition(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        String pre = "";
        StringBuilder str = new StringBuilder();

        if (MicroHUD.CONFIG.infoBlockPos) {
            str.append(String.format("Block: %s, %s, %s", getX(), getY(), getZ()));
            pre = " / ";
        }

        if (MicroHUD.CONFIG.infoChunkPos) {
            str.append(pre).append(String.format("Sub-Chunk: %d, %d, %d", getX() >> 4, getY() >> 4, getZ() >> 4));
            pre = " / ";
        }

        if (MicroHUD.CONFIG.infoRegionFile) {
            str.append(pre).append(String.format("Region: r.%d.%d", getX() >> 9, getZ() >> 9));
        }

        return str.toString();
    }
}