package me.kimovoid.microhud.mixin;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.info.InfoLine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public abstract class GuiIngameMixin extends GuiIngame {

    @Shadow private FontRenderer fontrenderer;
    @Unique
    protected final ResourceLocation microHud_effectsTexture = new ResourceLocation("textures/gui/container/inventory.png");

    @Unique
    protected final ResourceLocation microHud_effectsPlateTexture = new ResourceLocation( MicroHUD.MODID, "textures/gui/effects.png");

    public GuiIngameMixin(Minecraft p_i1036_1_) {
        super(p_i1036_1_);
    }

    @Inject(method = "renderHUDText",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/settings/GameSettings;showDebugInfo:Z",
                    shift = At.Shift.BEFORE
            )
    )
    private void renderHud(int width, int height, CallbackInfo ci) {
        if (!MicroHUD.CONFIG.hudRendering || this.mc.gameSettings.showDebugInfo) {
            return;
        }

        /* HUD lines rendering */
        int hudY = 0;
        GL11.glPushMatrix();
        GL11.glScalef(MicroHUD.CONFIG.hudScale, MicroHUD.CONFIG.hudScale, 1.0f);
        for (InfoLine line : MicroHUD.INSTANCE.lines) {
            if (!line.canRender()) continue;
            microHud_drawHudInfo(line.getLineString(), 0, hudY);
            hudY += 11;
        }
        GL11.glPopMatrix();

        /* Potion effects rendering */
        if (MicroHUD.CONFIG.effectsHudRendering) {
            int effectsX = 0;
            int effectsY = 0;
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            for (Object obj : this.mc.thePlayer.getActivePotionEffects()) {
                PotionEffect eff = (PotionEffect) obj;
                Potion potion = Potion.potionTypes[eff.getPotionID()];

                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.renderEngine.bindTexture(this.microHud_effectsPlateTexture);
                this.drawTexturedModalRect(width - effectsX - 25, effectsY + 1, 0, 0, 24, 24);

                if (potion.hasStatusIcon()) {
                    int l = potion.getStatusIconIndex();
                    this.mc.renderEngine.bindTexture(this.microHud_effectsTexture);
                    this.drawTexturedModalRect(width - effectsX - 22, effectsY + 4, l % 8 * 18, 198 + l / 8 * 18, 18, 18);

                    if (MicroHUD.CONFIG.effectsHudTime) {
                        String s = Potion.getDurationString(eff);
                        GuiIngame.drawRect(width - effectsX - 23, effectsY + 15, width - effectsX - 3, effectsY + 23, -1155983079);
                        this.fontrenderer.drawStringWithShadow(s, width - effectsX - 23, effectsY + 15,  eff.getDuration() <= 200 ? 13152175 : 11513775);
                    }
                }

                effectsX += 25;
                if (effectsX >= width) {
                    effectsX = 0;
                    effectsY += 25;
                }
            }
            GL11.glPopMatrix();
        }
    }

    @Unique
    private void microHud_drawHudInfo(String str, int x, int y) {
        FontRenderer fr = this.mc.fontRenderer;
        if (MicroHUD.CONFIG.hudBackground) {
            GuiIngame.drawRect(2 + x, 2 + y, fr.getStringWidth(str) + 5, 13 + y, -1873784752);
        }
        if (MicroHUD.CONFIG.hudTextShadow) {
            fr.drawStringWithShadow(str, x + 4, y + 4, 14737632);
        } else {
            fr.drawString(str, x + 4, y + 4, 14737632);
        }
    }
}
