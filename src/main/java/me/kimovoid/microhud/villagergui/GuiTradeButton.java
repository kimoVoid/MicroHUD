package me.kimovoid.microhud.villagergui;

import net.minecraft.client.gui.GuiButton;

public class GuiTradeButton extends GuiButton {

    public int ordinal;

    public GuiTradeButton(int id, int x, int y, int ordinal) {
        super(id, x, y, 89, 20, "");
        this.ordinal = ordinal;
        this.visible = true;
    }
}
