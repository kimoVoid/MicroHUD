package me.kimovoid.microhud.info;

import me.kimovoid.microhud.data.DataStorage;

public class InfoBBS extends InfoLine {

    public InfoBBS(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        return String.format("BBS: %.1f b/s", DataStorage.INSTANCE.getBlockBreakingSpeed());
    }
}