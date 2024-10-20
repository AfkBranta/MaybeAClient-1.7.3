/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryFurnaceCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntityFurnace;
import org.lwjgl.opengl.GL11;

public class GuiFurnace
extends GuiContainer {
    private TileEntityFurnace furnaceInventory;

    public GuiFurnace(InventoryPlayer var1, TileEntityFurnace var2) {
        super(new CraftingInventoryFurnaceCB(var1, var2));
        this.furnaceInventory = var2;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Furnace", 60, 6, 0x404040);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var5;
        int var2 = this.mc.renderEngine.getTexture("/gui/furnace.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
        if (this.furnaceInventory.isBurning()) {
            var5 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var3 + 56, var4 + 36 + 12 - var5, 176, 12 - var5, 14, var5 + 2);
        }
        var5 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var3 + 79, var4 + 34, 176, 14, var5 + 1, 16);
    }
}

