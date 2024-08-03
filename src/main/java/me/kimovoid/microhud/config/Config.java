package me.kimovoid.microhud.config;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.info.*;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Comparator;

public class Config {

    public final Configuration config;

    public boolean hudRendering = true;
    public boolean effectsHudRendering = false;
    public boolean effectsHudTime = false;
    public float hudScale = 1.0f;

    public boolean hudBackground = true;
    public boolean hudTextShadow = false;

    public boolean infoFps = true;
    public boolean infoCurrentTime = true;
    public boolean infoBlockPos = true;
    public boolean infoFacing = true;
    public boolean infoPing = false;
    public boolean infoTps = false;
    public boolean infoSeed = false;
    public boolean infoSlime = false;
    public boolean infoMobCaps = false;
    public boolean infoWorldTime = false;
    public boolean infoLookingAtBlock = false;
    public boolean infoChunkPos = false;
    public boolean infoRegionFile = false;
    public boolean infoLightLevel = false;

    public int lineOrderFps = 0;
    public int lineOrderCurrentTime = 1;
    public int lineOrderBlockPos = 2;
    public int lineOrderFacing = 3;
    public int lineOrderPing = 4;
    public int lineOrderTps = 5;
    public int lineOrderSeed = 6;
    public int lineOrderSlime = 7;
    public int lineOrderMobCaps = 8;
    public int lineOrderWorldTime = 9;
    public int lineOrderLookingAtBlock = 10;
    public int lineOrderLightLevel = 11;

    public Config(File path) {
        config = new Configuration(path);
        this.sync(true);
    }

    public void sync(boolean load) {
        if (load && !config.isChild) {
            config.load();
        }

        this.hudRendering = config.getBoolean("hudRendering", Configuration.CATEGORY_GENERAL, this.hudRendering, "Enables the main HUD");
        this.effectsHudRendering = config.getBoolean("effectsHudRendering", Configuration.CATEGORY_GENERAL, this.effectsHudRendering, "Enables effect status HUD");
        this.effectsHudTime = config.getBoolean("effectsHudTime", Configuration.CATEGORY_GENERAL, this.effectsHudTime, "Whether to show potion effect times");
        this.hudScale = config.getFloat("hudScale", Configuration.CATEGORY_GENERAL, this.hudScale, 0.0f, 10.0f, "HUD scale modifier");

        this.hudBackground = config.getBoolean("hudBackground", "render", this.hudBackground, "Enables background rendering");
        this.hudTextShadow = config.getBoolean("hudTextShadow", "render", this.hudTextShadow, "Enables text shadow rendering");

        this.infoFps = config.getBoolean("infoFps", "lines", this.infoFps, "Display your current FPS");
        this.infoCurrentTime = config.getBoolean("infoCurrentTime", "lines", this.infoCurrentTime, "Display your computer's date and time");
        this.infoBlockPos = config.getBoolean("infoBlockPos", "lines", this.infoBlockPos, "Display your block position");
        this.infoFacing = config.getBoolean("infoFacing", "lines", this.infoFacing, "Display your direction");
        this.infoPing = config.getBoolean("infoPing", "lines", this.infoPing, "Display your ping in servers");
        this.infoTps = config.getBoolean("infoTps", "lines", this.infoTps, "Display TPS and MSPT");
        this.infoSeed = config.getBoolean("infoSeed", "lines", this.infoSeed, "Display the world seed");
        this.infoSlime = config.getBoolean("infoSlime", "lines", this.infoSlime, "Display slime chunks");
        this.infoMobCaps = config.getBoolean("infoMobCaps", "lines", this.infoMobCaps, "Display mob caps");
        this.infoWorldTime = config.getBoolean("infoWorldTime", "lines", this.infoWorldTime, "Display the world time and day");
        this.infoLookingAtBlock = config.getBoolean("infoLookingAtBlock", "lines", this.infoLookingAtBlock, "Display the position of the block you're looking at");
        this.infoChunkPos = config.getBoolean("infoChunkPos", "lines", this.infoChunkPos, "Display the sub-chunk position\nMerged with \"infoBlockPos\"");
        this.infoRegionFile = config.getBoolean("infoRegionFile", "lines", this.infoRegionFile, "Display the region file name\nMerged with \"infoBlockPos\"");
        this.infoLightLevel = config.getBoolean("infoLightLevel", "lines", this.infoLightLevel, "Display the block/sky light level");

        String orderMsg = "Choose this line's priority";
        this.lineOrderFps = config.getInt("lineOrderFps", "order", this.lineOrderFps, 0, 100, orderMsg);
        this.lineOrderCurrentTime = config.getInt("lineOrderCurrentTime", "order", this.lineOrderCurrentTime, 0, 100, orderMsg);
        this.lineOrderBlockPos = config.getInt("lineOrderBlockPos", "order", this.lineOrderBlockPos, 0, 100, orderMsg + "\nAlso used for infoChunkPos and infoRegionFile");
        this.lineOrderFacing = config.getInt("lineOrderFacing", "order", this.lineOrderFacing, 0, 100, orderMsg);
        this.lineOrderPing = config.getInt("lineOrderPing", "order", this.lineOrderPing, 0, 100, orderMsg);
        this.lineOrderTps = config.getInt("lineOrderTps", "order", this.lineOrderTps, 0, 100, orderMsg);
        this.lineOrderSeed = config.getInt("lineOrderSeed", "order", this.lineOrderSeed, 0, 100, orderMsg);
        this.lineOrderSlime = config.getInt("lineOrderSlime", "order", this.lineOrderSlime, 0, 100, orderMsg);
        this.lineOrderMobCaps = config.getInt("lineOrderMobCaps", "order", this.lineOrderMobCaps, 0, 100, orderMsg);
        this.lineOrderWorldTime = config.getInt("lineOrderWorldTime", "order", this.lineOrderWorldTime, 0, 100, orderMsg);
        this.lineOrderLookingAtBlock = config.getInt("lineOrderLookingAtBlock", "order", this.lineOrderLookingAtBlock, 0, 100, orderMsg);
        this.lineOrderLightLevel = config.getInt("lineOrderLightLevel", "order", this.lineOrderLightLevel, 0, 100, orderMsg);

        this.loadLines();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void loadLines() {
        MicroHUD.INSTANCE.lines.clear();

        if (this.infoFps) MicroHUD.INSTANCE.lines.add(new InfoFps(this.lineOrderFps));
        if (this.infoCurrentTime) MicroHUD.INSTANCE.lines.add(new InfoCurrentTime(this.lineOrderCurrentTime));
        if (this.infoBlockPos || this.infoChunkPos || this.infoRegionFile) MicroHUD.INSTANCE.lines.add(new InfoPosition(this.lineOrderBlockPos));
        if (this.infoFacing) MicroHUD.INSTANCE.lines.add(new InfoFacing(this.lineOrderFacing));
        if (this.infoPing) MicroHUD.INSTANCE.lines.add(new InfoPing(this.lineOrderPing));
        if (this.infoTps) MicroHUD.INSTANCE.lines.add(new InfoTPS(this.lineOrderTps));
        if (this.infoSeed) MicroHUD.INSTANCE.lines.add(new InfoSeed(this.lineOrderSeed));
        if (this.infoSlime) MicroHUD.INSTANCE.lines.add(new InfoSlime(this.lineOrderSlime));
        if (this.infoMobCaps) MicroHUD.INSTANCE.lines.add(new InfoMobCaps(this.lineOrderMobCaps));
        if (this.infoWorldTime) MicroHUD.INSTANCE.lines.add(new InfoWorldTime(this.lineOrderWorldTime));
        if (this.infoLookingAtBlock) MicroHUD.INSTANCE.lines.add(new InfoLookingAtBlock(this.lineOrderLookingAtBlock));
        if (this.infoLightLevel) MicroHUD.INSTANCE.lines.add(new InfoLightLevel(this.lineOrderLightLevel));

        /* Order the list based on order */
        MicroHUD.INSTANCE.lines.sort(Comparator.comparing(InfoLine::getOrder));
    }
}