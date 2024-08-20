package me.kimovoid.microhud.info;

import me.kimovoid.microhud.data.DataStorage;

public class InfoPing extends InfoLine {

    public InfoPing(int order) {
        super(order);
    }

    @Override
    public boolean canRender() {
        return !this.mc.isSingleplayer();
    }

    @Override
    public String getLineString() {
        return String.format("Ping: %s ms", DataStorage.INSTANCE.ping);
    }
}