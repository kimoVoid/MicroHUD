package me.kimovoid.microhud.info;

import net.minecraft.util.MovingObjectPosition;

public class InfoLookingAtBlock extends InfoLine {

    public InfoLookingAtBlock(int order) {
        super(order);
    }

    @Override
    public boolean canRender() {
        return (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK));
    }

    @Override
    public String getLineString() {
        return String.format("Looking at block: %s, %s, %s", this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
    }
}