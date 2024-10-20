/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryChestCB;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import org.lwjgl.opengl.GL11;

public class GuiChest
extends GuiContainer {
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows = 0;

    public GuiChest(IInventory var1, IInventory var2) {
        super(new CraftingInventoryChestCB(var1, var2));
        this.upperChestInventory = var1;
        this.lowerChestInventory = var2;
        this.field_948_f = false;
        int var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = var2.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        this.fontRenderer.drawString(this.lowerChestInventory.getInvName(), 8, 6, 0x404040);
        this.fontRenderer.drawString(this.upperChestInventory.getInvName(), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1) {
        int var2 = this.mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.renderEngine.bindTexture(var2);
        int var3 = (this.width - this.xSize) / 2;
        int var4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var3, var4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var3, var4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}

