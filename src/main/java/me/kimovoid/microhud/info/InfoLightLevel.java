package me.kimovoid.microhud.info;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

public class InfoLightLevel extends InfoLine {

    public InfoLightLevel(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        // fixes a crash
        if (this.mc.theWorld == null || !this.mc.theWorld.blockExists(getX(), getY(), getZ())) {
            return "Light: 0 (block: 0, sky: 0)";
        }

        Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(getX(), getZ());
        return String.format("Light: %d (block: %d, sky: %d)",
                chunk.getBlockLightValue(getX() & 15, getY(), getZ() & 15, 0),
                chunk.getSavedLightValue(EnumSkyBlock.Block, getX() & 15, getY(), getZ() & 15),
                chunk.getSavedLightValue(EnumSkyBlock.Sky, getX() & 15, getY(), getZ() & 15));
    }
}