package me.kimovoid.microhud.config;

import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.info.*;
import me.kimovoid.microhud.networking.OSLHandshakePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Comparator;

public class Config {

    public final Configuration config;

    /* General settings */
    public boolean hudRendering = true;
    public boolean effectsHudRendering = true;
    public boolean effectsHudTime = true;
    public boolean tradingHudRendering = true;
    public float hudScale = 1.0f;

    /* Render settings */
    public boolean hudBackground = true;
    public boolean hudTextShadow = false;

    /* Info line toggles */
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
    public boolean infoEntities = false;
    public boolean infoSpeed = false;
    public boolean infoBiome = false;
    public boolean infoMemory = false;
    public boolean infoServerMemory = false;
    public boolean infoBlockBreakSpeed = false;

    /* Info line order */
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
    public int lineOrderEntities = 12;
    public int lineOrderSpeed = 13;
    public int lineOrderBiome = 14;
    public int lineOrderMemory = 15;
    public int lineOrderServerMemory = 16;
    public int lineOrderBlockBreakSpeed = 17;

    /* Player list toggles */
    public boolean playerListTps = false;
    public boolean playerListMobcaps = false;
    public boolean playerListMemory = false;
    public boolean playerListServerMemory = false;

    public Config(File path) {
        config = new Configuration(path);
        this.sync(true);
    }

    public void sync(boolean load) {
        if (load && !config.isChild) {
            config.load();
        }

        this.hudRendering = config.getBoolean("hudRendering", Configuration.CATEGORY_GENERAL, true, "Enables the main HUD");
        this.effectsHudRendering = config.getBoolean("effectsHudRendering", Configuration.CATEGORY_GENERAL, true, "Enables effect status HUD");
        this.effectsHudTime = config.getBoolean("effectsHudTime", Configuration.CATEGORY_GENERAL, true, "Whether to show potion effect times");
        this.hudScale = config.getFloat("hudScale", Configuration.CATEGORY_GENERAL, 1.0f, 0.0f, 10.0f, "HUD scale modifier");
        this.tradingHudRendering = config.getBoolean("tradingHudRendering", Configuration.CATEGORY_GENERAL, true, "Enables enhanced trading GUI\nSimilar to 1.14+");

        this.hudBackground = config.getBoolean("hudBackground", "render", true, "Enables background rendering");
        this.hudTextShadow = config.getBoolean("hudTextShadow", "render", false, "Enables text shadow rendering");

        this.infoFps = config.getBoolean("infoFps", "lines", true, "Display your current FPS");
        this.infoCurrentTime = config.getBoolean("infoCurrentTime", "lines", true, "Display your computer's date and time");
        this.infoBlockPos = config.getBoolean("infoBlockPos", "lines", true, "Display your block position");
        this.infoFacing = config.getBoolean("infoFacing", "lines", true, "Display your direction");
        this.infoPing = config.getBoolean("infoPing", "lines", false, "Display your ping in servers");
        this.infoTps = config.getBoolean("infoTps", "lines", false, "Display TPS and MSPT");
        this.infoSeed = config.getBoolean("infoSeed", "lines", false, "Display the world seed");
        this.infoSlime = config.getBoolean("infoSlime", "lines", false, "Display slime chunks");
        this.infoMobCaps = config.getBoolean("infoMobCaps", "lines", false, "Display mob caps");
        this.infoWorldTime = config.getBoolean("infoWorldTime", "lines", false, "Display the world time and day");
        this.infoLookingAtBlock = config.getBoolean("infoLookingAtBlock", "lines", false, "Display the position of the block you're looking at");
        this.infoChunkPos = config.getBoolean("infoChunkPos", "lines", false, "Display the sub-chunk position\nMerged with \"infoBlockPos\"");
        this.infoRegionFile = config.getBoolean("infoRegionFile", "lines", false, "Display the region file name\nMerged with \"infoBlockPos\"");
        this.infoLightLevel = config.getBoolean("infoLightLevel", "lines", false, "Display the block/sky light level");
        this.infoEntities = config.getBoolean("infoEntities", "lines", false, "Display rendered/total entities");
        this.infoSpeed = config.getBoolean("infoSpeed", "lines", false, "Display current movement speed");
        this.infoBiome = config.getBoolean("infoBiome", "lines", false, "Display biome name");
        this.infoMemory = config.getBoolean("infoMemory", "lines", false, "Display used/allocated and max memory");
        this.infoServerMemory = config.getBoolean("infoServerMemory", "lines", false, "Display server used/allocated and max memory");
        this.infoBlockBreakSpeed = config.getBoolean("infoBlockBreakSpeed", "lines", false, "Display your average mining speed");

        String orderMsg = "Choose this line's priority";
        this.lineOrderFps = config.getInt("lineOrderFps", "order", 0, 0, 100, orderMsg);
        this.lineOrderCurrentTime = config.getInt("lineOrderCurrentTime", "order", 1, 0, 100, orderMsg);
        this.lineOrderBlockPos = config.getInt("lineOrderBlockPos", "order", 2, 0, 100, orderMsg + "\nAlso used for infoChunkPos and infoRegionFile");
        this.lineOrderFacing = config.getInt("lineOrderFacing", "order", 3, 0, 100, orderMsg);
        this.lineOrderPing = config.getInt("lineOrderPing", "order", 4, 0, 100, orderMsg);
        this.lineOrderTps = config.getInt("lineOrderTps", "order", 5, 0, 100, orderMsg);
        this.lineOrderSeed = config.getInt("lineOrderSeed", "order", 6, 0, 100, orderMsg);
        this.lineOrderSlime = config.getInt("lineOrderSlime", "order", 7, 0, 100, orderMsg);
        this.lineOrderMobCaps = config.getInt("lineOrderMobCaps", "order", 8, 0, 100, orderMsg);
        this.lineOrderWorldTime = config.getInt("lineOrderWorldTime", "order", 9, 0, 100, orderMsg);
        this.lineOrderLookingAtBlock = config.getInt("lineOrderLookingAtBlock", "order", 10, 0, 100, orderMsg);
        this.lineOrderLightLevel = config.getInt("lineOrderLightLevel", "order", 11, 0, 100, orderMsg);
        this.lineOrderEntities = config.getInt("lineOrderEntities", "order", 12, 0, 100, orderMsg);
        this.lineOrderSpeed = config.getInt("lineOrderSpeed", "order", 13, 0, 100, orderMsg);
        this.lineOrderBiome = config.getInt("lineOrderBiome", "order", 14, 0, 100, orderMsg);
        this.lineOrderMemory = config.getInt("lineOrderMemory", "order", 15, 0, 100, orderMsg);
        this.lineOrderServerMemory = config.getInt("lineOrderServerMemory", "order", 16, 0, 100, orderMsg);
        this.lineOrderBlockBreakSpeed = config.getInt("lineOrderBlockBreakSpeed", "order", 17, 0, 100, orderMsg);

        this.playerListTps = config.getBoolean("playerListTps", "playerlist", false, "Display TPS and MSPT in tab list");
        this.playerListMobcaps = config.getBoolean("playerListMobcaps", "playerlist", false, "Display mob caps in tab list");
        this.playerListMemory = config.getBoolean("playerListMemory", "playerlist", false, "Display used/allocated and max memory in tab list");
        this.playerListServerMemory = config.getBoolean("playerListServerMemory", "playerlist", false, "Display server used/allocated and max memory in tab list");

        this.loadLines(load);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void loadLines(boolean load) {
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
        if (this.infoEntities) MicroHUD.INSTANCE.lines.add(new InfoEntities(this.lineOrderEntities));
        if (this.infoSpeed) MicroHUD.INSTANCE.lines.add(new InfoSpeed(this.lineOrderSpeed));
        if (this.infoBiome) MicroHUD.INSTANCE.lines.add(new InfoBiome(this.lineOrderBiome));
        if (this.infoMemory) MicroHUD.INSTANCE.lines.add(new InfoMemory(this.lineOrderMemory, false));
        if (this.infoServerMemory) MicroHUD.INSTANCE.lines.add(new InfoMemory(this.lineOrderServerMemory, true));
        if (this.infoBlockBreakSpeed) MicroHUD.INSTANCE.lines.add(new InfoBBS(this.lineOrderBlockBreakSpeed));

        /* Order the list based on custom order */
        MicroHUD.INSTANCE.lines.sort(Comparator.comparing(InfoLine::getOrder));

        /* Register networking listeners */
        if (!load) {
            Minecraft mc = Minecraft.getMinecraft();
            C17PacketCustomPayload packet = OSLHandshakePayload.getHandshake();
            if (packet != null && mc.theWorld != null && mc.theWorld.isRemote) {
                mc.getNetHandler().getNetworkManager().scheduleOutboundPacket(packet);
            }
        }
    }
}