package me.kimovoid.microhud.info;

import me.kimovoid.microhud.data.DataMobCaps;

public class InfoMobCaps extends InfoLine {

    public InfoMobCaps(int order) {
        super(order);
    }

    @Override
    public boolean canRender() {
        return !DataMobCaps.INSTANCE.isEmpty();
    }

    @Override
    public String getLineString() {
        return String.format("Mob Caps: %s", getMobCaps());
    }

    /* Will be used for tab list soon(tm) */
    public static String getMobCaps() {
        StringBuilder str = new StringBuilder();
        DataMobCaps mobCaps = DataMobCaps.INSTANCE;
        str.append(String.format("H: §c%s/%s", mobCaps.hostile, mobCaps.hostileMax));

        if (mobCaps.passive != -1) {
            str.append(String.format(" §rP: §a%s/%s", mobCaps.passive, mobCaps.passiveMax));
        }

        if (mobCaps.water != -1) {
            str.append(String.format(" §rW: §b%s/%s", mobCaps.water, mobCaps.waterMax));
        }

        if (mobCaps.ambient != -1) {
            str.append(String.format(" §rA: §7%s/%s", mobCaps.ambient, mobCaps.ambientMax));
        }

        return str.toString();
    }
}