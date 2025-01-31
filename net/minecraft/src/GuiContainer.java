/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Slot;
import net.minecraft.src.StringTranslate;
import net.skidcode.gh.maybeaclient.hacks.InventoryWalkHack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public abstract class GuiContainer
extends GuiScreen {
    private static RenderItem itemRenderer = new RenderItem();
    protected int xSize = 176;
    protected int ySize = 166;
    public CraftingInventoryCB inventorySlots;

    public GuiContainer(CraftingInventoryCB var1) {
        this.inventorySlots = var1;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.craftingInventory = this.inventorySlots;
    }

    @Override
    public void handleKeyboardInput() {
        super.handleKeyboardInput();
        if (InventoryWalkHack.instance.status) {
            this.mc.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        String var13;
        int var10;
        int var9;
        this.drawDefaultBackground();
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        this.drawGuiContainerBackgroundLayer(var3);
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)32826);
        Slot var6 = null;
        int var7 = 0;
        while (var7 < this.inventorySlots.slots.size()) {
            Slot var8 = (Slot)this.inventorySlots.slots.get(var7);
            this.drawSlotInventory(var8);
            if (this.getIsMouseOverSlot(var8, var1, var2)) {
                var6 = var8;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                var9 = var8.xDisplayPosition;
                var10 = var8.yDisplayPosition;
                this.drawGradientRect(var9, var10, var9 + 16, var10 + 16, -2130706433, -2130706433);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            ++var7;
        }
        InventoryPlayer var12 = this.mc.thePlayer.inventory;
        if (var12.getItemStack() != null) {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)32.0f);
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), var1 - var4 - 8, var2 - var5 - 8);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var12.getItemStack(), var1 - var4 - 8, var2 - var5 - 8);
        }
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        this.drawGuiContainerForegroundLayer();
        if (var12.getItemStack() == null && var6 != null && var6.getHasStack() && (var13 = StringTranslate.getInstance().translateNamedKey(var6.getStack().func_20109_f()).trim()).length() > 0) {
            var9 = var1 - var4 + 12;
            var10 = var2 - var5 - 12;
            int var11 = this.fontRenderer.getStringWidth(var13);
            this.drawGradientRect(var9 - 3, var10 - 3, var9 + var11 + 3, var10 + 8 + 3, -1073741824, -1073741824);
            this.fontRenderer.drawStringWithShadow(var13, var9, var10, -1);
        }
        GL11.glPopMatrix();
        super.drawScreen(var1, var2, var3);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
    }

    protected void drawGuiContainerForegroundLayer() {
    }

    protected abstract void drawGuiContainerBackgroundLayer(float var1);

    private void drawSlotInventory(Slot var1) {
        int var5;
        int var2 = var1.xDisplayPosition;
        int var3 = var1.yDisplayPosition;
        ItemStack var4 = var1.getStack();
        if (var4 == null && (var5 = var1.getBackgroundIconIndex()) >= 0) {
            GL11.glDisable((int)2896);
            this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
            this.drawTexturedModalRect(var2, var3, var5 % 16 * 16, var5 / 16 * 16, 16, 16);
            GL11.glEnable((int)2896);
            return;
        }
        itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
    }

    private Slot getSlotAtPosition(int var1, int var2) {
        int var3 = 0;
        while (var3 < this.inventorySlots.slots.size()) {
            Slot var4 = (Slot)this.inventorySlots.slots.get(var3);
            if (this.getIsMouseOverSlot(var4, var1, var2)) {
                return var4;
            }
            ++var3;
        }
        return null;
    }

    private boolean getIsMouseOverSlot(Slot var1, int var2, int var3) {
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        return (var2 -= var4) >= var1.xDisplayPosition - 1 && var2 < var1.xDisplayPosition + 16 + 1 && (var3 -= var5) >= var1.yDisplayPosition - 1 && var3 < var1.yDisplayPosition + 16 + 1;
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        if (var3 == 0 || var3 == 1) {
            Slot var4 = this.getSlotAtPosition(var1, var2);
            int var5 = (this.width - this.xSize) / 2;
            int var6 = (this.height - this.ySize) / 2;
            boolean var7 = var1 < var5 || var2 < var6 || var1 >= var5 + this.xSize || var2 >= var6 + this.ySize;
            int var8 = -1;
            if (var4 != null) {
                var8 = var4.slotNumber;
            }
            if (var7) {
                var8 = -999;
            }
            if (var8 != -1) {
                this.mc.playerController.func_20085_a(this.inventorySlots.windowId, var8, var3, this.mc.thePlayer);
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int var1, int var2, int var3) {
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        if (var2 == 1 || var2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            this.mc.thePlayer.func_20059_m();
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.mc.playerController.func_20086_a(this.inventorySlots.windowId, this.mc.thePlayer);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

