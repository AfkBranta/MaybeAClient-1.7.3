/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryDispenserCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntityDispenser;
import org.lwjgl.opengl.GL11;

public class GuiDispenser
extends GuiContainer {
    public GuiDispenser(InventoryPlayer var1, TileEntityDispenser var2) {
        super(new CraftingInventoryDispenserCB(var1, var2));
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString("Dispenser", 60, 6, 0x404040);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.ySize);
    }
}

