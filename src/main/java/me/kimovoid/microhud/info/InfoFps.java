package me.kimovoid.microhud.info;

import me.kimovoid.microhud.mixin.access.MinecraftAccessor;

public class InfoFps extends InfoLine {

    public InfoFps(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        return String.format("%s fps", ((MinecraftAccessor) this.mc).getFps());
    }
}