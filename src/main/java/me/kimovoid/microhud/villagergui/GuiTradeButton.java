package me.kimovoid.microhud.villagergui;

import net.minecraft.client.gui.GuiButton;

public class GuiTradeButton extends GuiButton {

    public int ordinal;

    public GuiTradeButton(int id, int x, int y, int ordinal) {
        super(id, x, y, 89, 20, "");
        this.ordinal = ordinal;
        this.visible = true;
    }

    /*
    @Override
    public void renderToolTip(int mouseX, int mouseY) {
        if (this.isHovered && MerchantScreen.this.container.getRecipes().size() > this.field_19165 + MerchantScreen.this.field_19163) {
            if (mouseX < this.x + 20) {
                ItemStack itemStack = ((TradeOffer)MerchantScreen.this.container.getRecipes().get(this.field_19165 + MerchantScreen.this.field_19163))
                        .getAdjustedFirstBuyItem();
                MerchantScreen.this.renderTooltip(itemStack, mouseX, mouseY);
            } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                ItemStack itemStack = ((TradeOffer)MerchantScreen.this.container.getRecipes().get(this.field_19165 + MerchantScreen.this.field_19163)).getSecondBuyItem();
                if (!itemStack.isEmpty()) {
                    MerchantScreen.this.renderTooltip(itemStack, mouseX, mouseY);
                }
            } else if (mouseX > this.x + 65) {
                ItemStack itemStack = ((TradeOffer)MerchantScreen.this.container.getRecipes().get(this.field_19165 + MerchantScreen.this.field_19163))
                        .getMutableSellItem();
                MerchantScreen.this.renderTooltip(itemStack, mouseX, mouseY);
            }
        }
    }
     */
}
