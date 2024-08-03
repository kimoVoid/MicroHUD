package me.kimovoid.microhud.info;

import me.kimovoid.microhud.MicroHUD;
import net.minecraft.util.MathHelper;

public class InfoPosition extends InfoLine {

    public InfoPosition(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        int x = MathHelper.floor_double(this.mc.thePlayer.posX);
        int y = MathHelper.floor_double(this.mc.thePlayer.posY) - 1;
        int z = MathHelper.floor_double(this.mc.thePlayer.posZ);

        String pre = "";
        StringBuilder str = new StringBuilder();

        if (MicroHUD.CONFIG.infoBlockPos) {
            str.append(String.format("Block: %s %s %s", x, y, z));
            pre = " / ";
        }

        if (MicroHUD.CONFIG.infoChunkPos) {
            str.append(pre).append(String.format("Sub-Chunk: %d, %d, %d", x >> 4, y >> 4, z >> 4));
            pre = " / ";
        }

        if (MicroHUD.CONFIG.infoRegionFile) {
            str.append(pre).append(String.format("Region: r.%d.%d", x >> 9, z >> 9));
        }

        return str.toString();
    }
}