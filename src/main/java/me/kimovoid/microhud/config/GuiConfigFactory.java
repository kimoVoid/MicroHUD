package me.kimovoid.microhud.config;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import me.kimovoid.microhud.MicroHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiConfigFactory implements IModGuiFactory {

    @Override
    public void initialize(final Minecraft instance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return MHGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(final RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class MHGuiConfig extends GuiConfig {
        public MHGuiConfig(final GuiScreen parent) {
            super(parent, getConfigElements(MicroHUD.CONFIG.config), MicroHUD.MODID, false, false, "MicroHUD");
        }

        private static List<IConfigElement> getConfigElements(final Configuration configuration) {
            List<IConfigElement> elements = new ArrayList<>();

            elements.addAll((new ConfigElement(configuration.getCategory("general"))).getChildElements());

            ConfigCategory linesCategory = configuration.getCategory("lines").setLanguageKey("microhud.config.lines");
            linesCategory.setComment("Choose which lines to display");
            elements.add(new ConfigElement(linesCategory));

            ConfigCategory orderCategory = configuration.getCategory("order").setLanguageKey("microhud.config.order");
            orderCategory.setComment("Choose priority per line");
            elements.add(new ConfigElement(orderCategory));

            ConfigCategory renderCategory = configuration.getCategory("render").setLanguageKey("microhud.config.render");
            renderCategory.setComment("Change the look of the HUD");
            elements.add(new ConfigElement(renderCategory));

            return elements;
        }
    }
}
