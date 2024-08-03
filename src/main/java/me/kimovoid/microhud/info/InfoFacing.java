package me.kimovoid.microhud.info;

import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;

public class InfoFacing extends InfoLine {

    public InfoFacing(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        int heading = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return String.format("Facing: %s (%s)",
                Direction.directions[heading].toLowerCase(),
                heading == 0 ? "Positive Z" : heading == 1 ? "Negative X" : heading == 2 ? "Negative Z" : "Positive X"
        );
    }
}