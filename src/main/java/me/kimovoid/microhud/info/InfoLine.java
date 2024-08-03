package me.kimovoid.microhud.info;

import net.minecraft.client.Minecraft;

public class InfoLine {

    private final int order;
    public final Minecraft mc = Minecraft.getMinecraft();

    public InfoLine(int order) {
        this.order = order;
    }

    public Integer getOrder() {
        return this.order;
    }

    public boolean canRender() {
        return true;
    }

    public String getLineString() {
        return "";
    }
}
