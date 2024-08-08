package me.kimovoid.microhud.mixin.villagergui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.kimovoid.microhud.MicroHUD;
import me.kimovoid.microhud.villagergui.GuiTradeButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMerchant.class)
public abstract class GuiMerchantMixin extends GuiContainer {

    @Shadow private IMerchant field_147037_w;
    @Shadow @Final private static Logger logger;
    @Shadow private int field_147041_z;

    @Unique private boolean microhud_hasRecipes = false;
    @Unique private final ResourceLocation microhud_villagerTexture = new ResourceLocation(MicroHUD.MODID, "textures/gui/container/villager2.png");
    @Unique private GuiTradeButton[] microhud_tradeButtons = new GuiTradeButton[7];

    @Unique private boolean microhud_wasClicking = false;
    @Unique private boolean microhud_canScroll = false;
    @Unique private float microhud_scrollFactor = 0.0f;
    @Unique private int microhud_scrollIndex = 0;
    @Unique private boolean microhud_isScrolling = false;

    public GuiMerchantMixin(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    /* Init buttons */
    @Inject(method = "initGui", at = @At("TAIL"))
    private void microhud_initButtons(CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        this.microhud_addTradeButtons();
    }

    /* Init buttons and scroll bar */
    @Inject(method = "updateScreen", at = @At("TAIL"))
    private void microhud_initButtonsAndScroll(CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        MerchantRecipeList tradeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        if (tradeList == null) {
            return;
        }

        if (!this.microhud_hasRecipes) {
            this.microhud_addTradeButtons();
            this.microhud_hasRecipes = true;
        }

        if (tradeList.size() > 7 && !this.microhud_canScroll) {
            this.microhud_canScroll = true;
        }
    }

    /* Handle button click */
    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void microhud_onButtonClick(GuiButton btn, CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        if (btn instanceof GuiTradeButton) {
            int index = ((GuiTradeButton) btn).ordinal + this.microhud_scrollIndex;
            if (this.field_147041_z != index) {
                this.microhud_syncGuiIndex(index);
            }

            /* Swap/fill slots */
            MerchantRecipe trade = (MerchantRecipe) this.field_147037_w.getRecipes(this.mc.thePlayer).get(index);
            ItemStack item1 = trade.getItemToBuy();
            ItemStack item2 = trade.getSecondItemToBuy();

            this.microhud_emptySlot(0, item1);
            this.microhud_emptySlot(1, item2);
            this.microhud_fillSlotWith(0, item1);
            if (item2 != null) {
                this.microhud_fillSlotWith(1, item2);
            }
        }
    }

    /* Scroll bar and arrows */
    @Inject(method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawScreen(IIF)V",
                    shift = At.Shift.AFTER
            )
    )
    private void microhud_renderScrollBar(CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(GL11.GL_LIGHTING);
        int min = this.guiTop + 18;
        int max = min + 130;
        this.mc.getTextureManager().bindTexture(this.microhud_villagerTexture);
        this.drawTexturedModalRect(94 + (this.width - 387) / 2, min + (int) ((float) (max - min - 17) * this.microhud_scrollFactor), this.microhud_canScroll ? 0 : 6, 199, 6, 27);

        /* Trade arrows */
        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        int x = (this.width - 383) / 2;
        int height = (this.height - this.ySize) / 2;
        int y = height + 19;

        for (GuiTradeButton tradeButton : this.microhud_tradeButtons) {
            if (tradeButton == null) return;
            int index = tradeButton.ordinal + this.microhud_scrollIndex;
            MerchantRecipe recipe = (MerchantRecipe) recipeList.get(index);
            this.drawTexturedModalRect(x + 56, y + 4, recipe.isRecipeDisabled() ? 25 : 15, 171, 10, 9);
            y += 20;
        }
    }

    /* Recipe items */
    @Inject(method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemAndEffectIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void microhud_renderRecipeItems(int width, int height, float delta, CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        int x = 3 + (this.width - 383) / 2;
        int y = 19 + (this.height - this.ySize) / 2;

        for (GuiTradeButton tradeButton : this.microhud_tradeButtons) {
            if (tradeButton == null) return;
            int index = tradeButton.ordinal + this.microhud_scrollIndex;
            MerchantRecipe recipe = (MerchantRecipe) recipeList.get(index);
            ItemStack itemstack = recipe.getItemToBuy();
            ItemStack itemstack1 = recipe.getSecondItemToBuy();
            ItemStack itemstack2 = recipe.getItemToSell();

            RenderHelper.enableGUIStandardItemLighting();
            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, x + 5, y);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, x + 5, y);

            if (itemstack1 != null) {
                itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack1, x + 32, y);
                itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack1, x + 32, y);
            }

            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack2, x + 66, y);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack2, x + 66, y);
            y += 20;
        }
    }

    /* Tooltips */
    @Inject(method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiMerchant;func_146978_c(IIIIII)Z",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void microhud_renderRecipeItemTooltips(int width, int height, float delta, CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        int y = 21;
        for (GuiTradeButton tradeButton : this.microhud_tradeButtons) {
            if (tradeButton == null) return;
            int index = tradeButton.ordinal + this.microhud_scrollIndex;
            MerchantRecipe recipe = (MerchantRecipe) recipeList.get(index);
            ItemStack itemstack = recipe.getItemToBuy();
            ItemStack itemstack1 = recipe.getSecondItemToBuy();
            ItemStack itemstack2 = recipe.getItemToSell();

            /* Tooltips */
            if (this.func_146978_c(16, y, 16, 16, width + 110, height)) {
                this.renderToolTip(itemstack, width, height);
            } else if (itemstack1 != null && this.func_146978_c(40, y, 16, 16, width + 110, height)) {
                this.renderToolTip(itemstack1, width, height);
            } else if (this.func_146978_c(74, y, 16, 16, width + 110, height)) {
                this.renderToolTip(itemstack2, width, height);
            }

            y += 20;
        }
    }

    /* Handle mouse drag on scroll bar */
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void microhud_handleScrollBar(int x, int y, float delta, CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        boolean flag = Mouse.isButtonDown(0);
        int minX = this.guiLeft - 12;
        int minY = this.guiTop + 18;
        int maxX = minX + 6;
        int maxY = minY + 140;

        if (!this.microhud_wasClicking && flag && x >= minX && y >= minY && x < maxX && y < maxY) {
            this.microhud_isScrolling = this.microhud_canScroll;
        }

        if (!flag) {
            this.microhud_isScrolling = false;
        }

        this.microhud_wasClicking = flag;

        if (this.microhud_isScrolling) {
            this.microhud_scrollFactor = ((float) (y - minY) - 7.5F) / ((float) (maxY - minY) - 15.0F);

            if (this.microhud_scrollFactor < 0.0F) {
                this.microhud_scrollFactor = 0.0F;
            }

            if (this.microhud_scrollFactor > 1.0F) {
                this.microhud_scrollFactor = 1.0F;
            }

            this.microhud_scrollIndex = Math.round(this.microhud_scrollFactor * (recipeList.size() - 7));
        }
    }

    /* Handle scroll wheel */
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (!MicroHUD.CONFIG.tradingHudRendering) return;

        final ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int scaledWidth = scaledresolution.getScaledWidth();
        int scaledHeight = scaledresolution.getScaledHeight();
        final int x = Mouse.getX() * scaledWidth / this.mc.displayWidth;
        final int y = scaledHeight - Mouse.getY() * scaledHeight / this.mc.displayHeight - 1;
        if (!this.func_146978_c(0, 0, 105, this.ySize, x + 107, y)) {
            return;
        }

        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        int i = Mouse.getEventDWheel();

        if (i != 0 && this.microhud_canScroll && recipeList != null) {
            int tradesSize = recipeList.size() - 7;

            if (i > 0) {
                i = 1;
                this.microhud_scrollIndex--;
            }

            if (i < 0) {
                i = -1;
                this.microhud_scrollIndex++;
            }

            this.microhud_scrollFactor = (float) ((double) this.microhud_scrollFactor - (double) i / (double) tradesSize);

            if (this.microhud_scrollFactor < 0.0F) {
                this.microhud_scrollFactor = 0.0F;
                this.microhud_scrollIndex = 0;
            }

            if (this.microhud_scrollFactor > 1.0F) {
                this.microhud_scrollFactor = 1.0F;
                this.microhud_scrollIndex = tradesSize;
            }
        }
    }

    /* Render trades texture */
    @Inject(method = "drawGuiContainerBackgroundLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void microhud_addTexture(CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        this.mc.getTextureManager().bindTexture(this.microhud_villagerTexture);
        this.drawTexturedModalRect((this.width - 387) / 2, (this.height - this.ySize) / 2, 0, 0, 105, this.ySize);
    }

    /* Render trades title */
    @Inject(method = "drawGuiContainerForegroundLayer", at = @At("TAIL"))
    private void microhud_addTitle(CallbackInfo ci) {
        if (!MicroHUD.CONFIG.tradingHudRendering) return;
        MerchantRecipeList recipeList = this.field_147037_w.getRecipes(this.mc.thePlayer);
        String trades = String.format("Trades (%s)", recipeList != null ? recipeList.size() : 0);
        this.fontRendererObj.drawString(trades, -107 / 2 - this.fontRendererObj.getStringWidth(trades) / 2, 6, 4210752);
    }

    @Unique
    public void microhud_syncGuiIndex(int index) {
        this.field_147041_z = index;
        ((ContainerMerchant) this.inventorySlots).setCurrentRecipeIndex(index);
        ByteBuf bytebuf = Unpooled.buffer();

        try {
            bytebuf.writeInt(index);
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", bytebuf));
        } catch (Exception exception) {
            logger.error("Couldn't send trade info", exception);
        } finally {
            bytebuf.release();
        }
    }

    @Unique
    public void microhud_addTradeButtons() {
        int x = 3 + (this.width - 383) / 2;
        int height = (this.height - this.ySize) / 2;
        int y = height + 16 + 2;

        MerchantRecipeList merchantrecipelist = this.field_147037_w.getRecipes(this.mc.thePlayer);
        if (merchantrecipelist == null) {
            return;
        }

        for (int l = 0; l < 7; l++) {
            if (merchantrecipelist.size() < l + 1) {
                break;
            }
            this.buttonList.add(microhud_tradeButtons[l] = new GuiTradeButton(3 + l, x, y, l));
            y += 20;
        }
    }

    @Unique
    public void microhud_fillSlotWith(int slot, ItemStack item) {
        ItemStack currentItem = this.inventorySlots.getSlot(slot).getStack();
        int stackSize = item.getMaxStackSize();
        int currentSize = currentItem != null ? currentItem.stackSize : 0;
        for (int i = 3; i < 39; i++) {
            if (currentSize >= stackSize) return;
            ItemStack is = this.inventorySlots.getSlot(i).getStack();
            if (is == null || !is.getItem().equals(item.getItem())) continue;
            currentSize += is.stackSize;

            /* Swap item, simulating left click instead of key swap */
            this.mc.playerController.windowClick(this.inventorySlots.windowId, i, 0, 0, this.mc.thePlayer);
            this.mc.playerController.windowClick(this.inventorySlots.windowId, slot, 0, 0, this.mc.thePlayer);
            this.mc.playerController.windowClick(this.inventorySlots.windowId, i, 0, 0, this.mc.thePlayer);
        }
    }

    @Unique
    public void microhud_emptySlot(int slot, ItemStack item) {
        ItemStack previousItem = this.inventorySlots.getSlot(slot).getStack();
        if (item == null || (previousItem != null && !previousItem.getItem().equals(item.getItem()))) {
            this.mc.playerController.windowClick(this.inventorySlots.windowId, slot, 0, 1, this.mc.thePlayer);
        }
    }
}