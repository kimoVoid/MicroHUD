package me.kimovoid.microhud.info;

import net.minecraft.world.chunk.Chunk;

public class InfoBiome extends InfoLine {

    public InfoBiome(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        // fixes a crash
        if (this.mc.theWorld == null || !this.mc.theWorld.blockExists(getX(), getY(), getZ())) {
            return "Biome: Unknown";
        }

        Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(getX(), getZ());
        return String.format("Biome: %s", chunk.getBiomeGenForWorldCoords(getX() & 15, getZ() & 15, this.mc.theWorld.getWorldChunkManager()).biomeName);
    }
}