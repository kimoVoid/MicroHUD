package me.kimovoid.microhud.networking;

import io.netty.buffer.Unpooled;
import me.kimovoid.microhud.MicroHUD;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OSLHandshakePayload {

    public static C17PacketCustomPayload getHandshake() {
        PacketBuffer bytebuf = new PacketBuffer(Unpooled.buffer());

        List<String> channels = new ArrayList<>();
        if (MicroHUD.CONFIG.infoTps) channels.add("MicroHUD|TPS");
        if (MicroHUD.CONFIG.infoMobCaps) channels.add("MicroHUD|MobCaps");
        if (channels.isEmpty()) return null;

        bytebuf.writeInt(channels.size());
        for (String channel : channels) {
            try {
                bytebuf.writeStringToBuffer(channel);
            } catch (IOException ignored) {}
        }
        return new C17PacketCustomPayload("OSL|Handshake", bytebuf);
    }
}
