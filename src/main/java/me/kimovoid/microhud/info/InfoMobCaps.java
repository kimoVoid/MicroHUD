package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.MobCapData;

public class InfoMobCaps extends InfoLine {

    public InfoMobCaps(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        if (MicroHUD.INSTANCE.mobCaps.isEmpty()) {
            return "Mob Caps: §7(no data)";
        }

        StringBuilder str = new StringBuilder();
        MobCapData mobCaps = MicroHUD.INSTANCE.mobCaps;
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