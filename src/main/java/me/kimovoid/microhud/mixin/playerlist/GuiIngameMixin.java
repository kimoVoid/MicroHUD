package me.kimovoid.microhud.mixin.playerlist;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.DataMobCaps;
import me.kimovoid.microhud.data.DataTPS;
import me.kimovoid.microhud.info.InfoMobCaps;
import me.kimovoid.microhud.info.InfoTPS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiIngameForge.class)
public abstract class GuiIngameMixin extends GuiIngame {

    @Shadow private FontRenderer fontrenderer;

    public GuiIngameMixin(Minecraft p_i1036_1_) {
        super(p_i1036_1_);
    }

    @Inject(method = "renderPlayerList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/client/GuiIngameForge;post(Lnet/minecraftforge/client/event/RenderGameOverlayEvent$ElementType;)V",
                    shift = At.Shift.BEFORE
            ),
            remap = false
    )
    private void addPlayerListLines(int width, int height, CallbackInfo ci) {
        NetHandlerPlayClient handler = mc.thePlayer.sendQueue;
        List<String> tabLines = new ArrayList<>();

        if (MicroHUD.CONFIG.playerListTps && DataTPS.INSTANCE.tps != -1) {
            tabLines.add(InfoTPS.getTPS());
        }
        if (MicroHUD.CONFIG.playerListMobcaps && !DataMobCaps.INSTANCE.isEmpty()) {
            tabLines.add(InfoMobCaps.getMobCaps());
        }

        if (tabLines.isEmpty()) {
            return;
        }

        int w = 0;
        for (String str : tabLines) {
            int strWidth = fontrenderer.getStringWidth(str) + 1;
            if (strWidth > w) w = strWidth;
        }

        int left = (width - w) / 2;
        int tabSize = 12 + (Math.min(handler.currentServerMaxPlayers, 20) * 9);
        drawRect(left - 1, tabSize - 1, left + w + 1, tabSize + 9 * tabLines.size(), Integer.MIN_VALUE);

        int shift = 0;
        for (String tabLine : tabLines) {
            drawRect(left, tabSize + shift, left + w, tabSize + shift + 8, 553648127);
            fontrenderer.drawStringWithShadow(tabLine, width / 2 - fontrenderer.getStringWidth(tabLine) / 2, tabSize + shift, 16777215);
            shift += 9;
        }
    }
}