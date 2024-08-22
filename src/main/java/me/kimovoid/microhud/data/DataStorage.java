package me.kimovoid.microhud.data;

import me.kimovoid.microhud.util.MathUtils;
import net.minecraft.client.Minecraft;

public class DataStorage {

    /**
     * This is where the client stores simple data,
     * mostly used for client-server compatibility.
     */
    public static final DataStorage INSTANCE = new DataStorage();

    public int ping = 0;
    public long seed = 0;

    public int serverMemUsed = -1;
    public int serverMemAllocated = -1;
    public int serverMemMax = -1;

    public final int[] blockBreakCounter = new int[100];

    public void resetStorage() {
        this.ping = 0;
        this.seed = 0;

        this.serverMemUsed = -1;
        this.serverMemAllocated = -1;
        this.serverMemMax = -1;
    }

    public void onClientTickPre(Minecraft mc) {
        int tick = (int) (mc.theWorld.getTotalWorldTime() % this.blockBreakCounter.length);
        this.blockBreakCounter[tick] = 0;
    }

    public void onPlayerBlockBreak(Minecraft mc) {
        int tick = (int) (mc.theWorld.getTotalWorldTime() % this.blockBreakCounter.length);
        ++this.blockBreakCounter[tick];
    }

    public double getBlockBreakingSpeed() {
        return MathUtils.averageInt(this.blockBreakCounter) * 20;
    }
}