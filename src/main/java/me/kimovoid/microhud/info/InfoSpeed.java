package me.kimovoid.microhud.info;

public class InfoSpeed extends InfoLine {

    public InfoSpeed(int order) {
        super(order);
    }

    @Override
    public String getLineString() {
        double dx = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
        double dy = mc.thePlayer.posY - mc.thePlayer.lastTickPosY;
        double dz = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        return String.format("Speed: %.3f m/s", distance * 20);
    }
}