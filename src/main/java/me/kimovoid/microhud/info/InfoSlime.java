package me.kimovoid.microhud.info;

import me.kimovoid.microhud.data.DataStorage;

import java.util.Random;

public class InfoSlime extends InfoLine {

    public InfoSlime(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        boolean slime = this.isSlimeChunk(DataStorage.INSTANCE.seed, this.mc.thePlayer.chunkCoordX, this.mc.thePlayer.chunkCoordZ);
        return String.format("Slime: %s", (slime ? "§a" : "§c") + slime);
    }

    private boolean isSlimeChunk(long seed, int x, int z) {
        Random rnd = new Random(
                seed +
                        (int) (x * x * 0x4c1906) +
                        (int) (x * 0x5ac0db) +
                        (int) (z * z) * 0x4307a7L +
                        (int) (z * 0x5f24f) ^ 0x3ad8025fL
        );

        return rnd.nextInt(10) == 0;
    }
}