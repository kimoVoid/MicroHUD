package me.kimovoid.microhud.mixin.networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.DataMobCaps;
import me.kimovoid.microhud.data.DataStorage;
import me.kimovoid.microhud.data.DataTPS;
import me.kimovoid.microhud.networking.OSLHandshakePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {

    @Shadow @Final private NetworkManager netManager;

    /* Handle custom packets */
    @Inject(method = "handleCustomPayload", at = @At("HEAD"))
    private void handleTPSPacket(S3FPacketCustomPayload packet, CallbackInfo ci) {
        if (packet == null || packet.func_149169_c() == null) {
            return;
        }

        if (packet.func_149169_c().equals(MicroHUD.CHANNEL + "|TPS")) {
            if (!MicroHUD.CONFIG.infoTps && !MicroHUD.CONFIG.playerListTps) {
                return;
            }

            ByteBuf bytebuf = Unpooled.wrappedBuffer(packet.func_149168_d());
            try {
                double tps = bytebuf.readDouble();
                double mspt = bytebuf.readDouble();
                DataTPS.INSTANCE.tps = tps;
                DataTPS.INSTANCE.mspt = mspt;
            } catch (Exception ignored) {}
        }

        if (packet.func_149169_c().equals(MicroHUD.CHANNEL + "|MobCaps")) {
            if (!MicroHUD.CONFIG.infoMobCaps && !MicroHUD.CONFIG.playerListMobcaps) {
                return;
            }

            ByteBuf bytebuf = Unpooled.wrappedBuffer(packet.func_149168_d());
            try {
                DataMobCaps.INSTANCE.hostile = bytebuf.readInt();
                DataMobCaps.INSTANCE.hostileMax = bytebuf.readInt();
                DataMobCaps.INSTANCE.passive = bytebuf.readInt();
                DataMobCaps.INSTANCE.passiveMax = bytebuf.readInt();
                DataMobCaps.INSTANCE.water = bytebuf.readInt();
                DataMobCaps.INSTANCE.waterMax = bytebuf.readInt();
                DataMobCaps.INSTANCE.ambient = bytebuf.readInt();
                DataMobCaps.INSTANCE.ambientMax = bytebuf.readInt();
            } catch (Exception ignored) {}
        }
    }

    /* Grab own player ping on remote servers */
    @Inject(method = "handlePlayerListItem", at = @At("HEAD"))
    private void getPing(S38PacketPlayerListItem packet, CallbackInfo ci) {
        if (packet == null || packet.func_149122_c() == null) {
            return;
        }
        if (packet.func_149122_c().equals(Minecraft.getMinecraft().thePlayer.getDisplayName())) {
            DataStorage.INSTANCE.ping = packet.func_149120_e();
        }
    }

    /* Register OSL networking channels */
    @Inject(method = "handleJoinGame", at = @At("TAIL"))
    private void sendHandshake(CallbackInfo ci) {
        C17PacketCustomPayload packet = OSLHandshakePayload.getHandshake();
        if (packet != null) {
            this.netManager.scheduleOutboundPacket(packet);
        }
    }
}
