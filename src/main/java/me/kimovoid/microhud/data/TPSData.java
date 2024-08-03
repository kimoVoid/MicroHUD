package me.kimovoid.microhud.data;

import me.kimovoid.microhud.MicroHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TPSData {

    public double tps = -1;
    public double mspt = -1;

    public void getClientTPS() {
        if (!MicroHUD.CONFIG.infoTps) {
            return;
        }

        try {
            long[] tickTimes = Minecraft.getMinecraft().getIntegratedServer().tickTimeArray;
            this.mspt = this.round(2, MathHelper.average(tickTimes) * 1.0E-6D);
            this.tps = this.round(2, 1000.0D / this.mspt);
            if (this.tps > 20.0) {
                this.tps = 20.0;
            }
        } catch (NumberFormatException ignored) {}
    }

    public double round(int places, double value) {
        return (new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public String getTPSColor() {
        return (this.tps < 20) ? "§c" : "§a";
    }

    public String getMSPTColor() {
        return (this.mspt < 40) ? "§a" : (this.mspt < 45) ? "§e" : (this.mspt < 50) ? "§6" : "§c";
    }
}
