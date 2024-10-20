/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryWorkbenchCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class GuiCrafting
extends GuiContainer {
    public GuiCrafting(InventoryPlayer var1, World var2, int var3, int var4, int var5) {
        super(new CraftingInventoryWorkbenchCB(var1, var2, var3, var4, var5));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        this.inventorySlots.onCraftGuiClosed(this.mc.thePlayer);
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Crafting", 28, 6, 0x404040);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/crafting.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
    }
}

