package me.kimovoid.microhud.info;

import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;

public class InfoLightLevel extends InfoLine {

    public InfoLightLevel(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        int x = MathHelper.floor_double(this.mc.thePlayer.posX);
        int y = MathHelper.floor_double(this.mc.thePlayer.posY);
        int z = MathHelper.floor_double(this.mc.thePlayer.posZ);

        // fixes a crash
        if (this.mc.theWorld == null || !this.mc.theWorld.blockExists(x, y, z)) {
            return "Light: 0 (block: 0, sky: 0)";
        }

        Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(x, z);
        return String.format("Light: %d (block: %d, sky: %d)",
                chunk.getBlockLightValue(x & 15, y, z & 15, 0),
                chunk.getSavedLightValue(EnumSkyBlock.Block, x & 15, y, z & 15),
                chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15));
    }
}