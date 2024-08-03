package me.kimovoid.microhud;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import me.kimovoid.microhud.config.Config;
import me.kimovoid.microhud.data.MobCapData;
import me.kimovoid.microhud.data.TPSData;
import me.kimovoid.microhud.info.InfoLine;
import me.kimovoid.microhud.listener.EventListener;
import me.kimovoid.microhud.listener.TickListener;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = MicroHUD.MODID, version = MicroHUD.VERSION, guiFactory = "me.kimovoid.microhud.config.GuiConfigFactory")
public class MicroHUD {

    public static Logger LOGGER;
    public static final String MODID = "microhud";
    public static final String VERSION = "@VERSION@";
    public static MicroHUD INSTANCE;
    public static Config CONFIG;

    public int ping = 0;
    public long seed = 0;
    public TPSData tps;
    public MobCapData mobCaps;
    public List<InfoLine> lines = new ArrayList<>();

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        INSTANCE = this;
        CONFIG = new Config(event.getSuggestedConfigurationFile());
        LOGGER = event.getModLog();

        tps = new TPSData();
        mobCaps = new MobCapData();

        FMLCommonHandler.instance().bus().register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventListener());
        FMLCommonHandler.instance().bus().register(new TickListener());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (MODID.equals(event.modID)) {
            CONFIG.sync(false);
        }
    }

    @SubscribeEvent
    public void onJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent ev) {
        if (ev.isLocal) return;
        this.seed = 0;
    }
}