package me.kimovoid.microhud.mixin.playerlist;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.data.DataMobCaps;
import me.kimovoid.microhud.data.DataStorage;
import me.kimovoid.microhud.data.DataTPS;
import me.kimovoid.microhud.info.InfoMemory;
import me.kimovoid.microhud.info.InfoMobCaps;
import me.kimovoid.microhud.info.InfoTPS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = GuiIngameForge.class, priority = 900)
public abstract class GuiIngameMixin extends GuiIngame {

    @Shadow private FontRenderer fontrenderer;

    public GuiIngameMixin(Minecraft p_i1036_1_) {
        super(p_i1036_1_);
    }

    @Inject(method = "renderPlayerList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/client/GuiIngameForge;drawRect(IIIII)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void addPlayerListLines(int width, int height, CallbackInfo ci, ScoreObjective scoreobjective, NetHandlerPlayClient handler, List players, int maxPlayers, int rows, int columns, int columnWidth, int left, byte border) {
        List<String> tabLines = microhud_getPlayerListLines();

        if (tabLines.isEmpty()) {
            return;
        }

        int w = 0;
        for (String str : tabLines) {
            int strWidth = fontrenderer.getStringWidth(str) + 1;
            if (strWidth > w) w = strWidth;
        }

        int tabLeft = (width - w) / 2;
        int tabSize = 12 + rows * 9;
        drawRect(tabLeft - 1, tabSize - 1, tabLeft + w + 1, tabSize + 9 * tabLines.size(), Integer.MIN_VALUE);

        int shift = 0;
        for (String tabLine : tabLines) {
            drawRect(tabLeft, tabSize + shift, tabLeft + w, tabSize + shift + 8, 553648127);
            fontrenderer.drawStringWithShadow(tabLine, width / 2 - fontrenderer.getStringWidth(tabLine) / 2, tabSize + shift, 16777215);
            shift += 9;
        }
    }

    @Unique
    private List<String> microhud_getPlayerListLines() {
        List<String> tabLines = new ArrayList<>();

        if (MicroHUD.CONFIG.playerListTps && DataTPS.INSTANCE.tps != -1) {
            tabLines.add(InfoTPS.getTPS());
        }
        if (MicroHUD.CONFIG.playerListMobcaps && !DataMobCaps.INSTANCE.isEmpty()) {
            tabLines.add(InfoMobCaps.getMobCaps());
        }
        if (MicroHUD.CONFIG.playerListMemory) {
            tabLines.add(String.format("%sClient Mem: %s", EnumChatFormatting.GRAY, InfoMemory.getMemory()));
        }
        if (MicroHUD.CONFIG.playerListServerMemory && DataStorage.INSTANCE.serverMemUsed != -1) {
            tabLines.add(String.format("%sServer Mem: %s", EnumChatFormatting.GRAY, InfoMemory.getServerMemory()));
        }

        return tabLines;
    }
}