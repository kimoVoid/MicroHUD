package me.kimovoid.microhud.info;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

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

    /* Helpers */
    int getX() {
        return MathHelper.floor_double(this.mc.thePlayer.posX);
    }

    int getY() {
        return MathHelper.floor_double(this.mc.thePlayer.posY);
    }

    int getZ() {
        return MathHelper.floor_double(this.mc.thePlayer.posZ);
    }
}
