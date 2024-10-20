/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.gui.click;

import net.minecraft.src.GuiIngame;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.InventoryViewHack;
import org.lwjgl.opengl.GL11;

public class InventoryTab
extends Tab {
    public static InventoryTab instance;

    public InventoryTab() {
        super("Inventory");
        this.xPos = 160;
        this.xDefPos = 160;
        this.yPos = 94;
        this.yDefPos = 94;
        this.minimized = true;
        instance = this;
    }

    @Override
    public void renderIngame() {
        if (InventoryViewHack.instance.status && RenderManager.instance.livingPlayer != null) {
            super.renderIngame();
        }
    }

    @Override
    public void render() {
        if (this.minimized) {
            this.height = 12;
            this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
            super.render();
            return;
        }
        super.render();
        this.width = 164;
        this.height = 69;
        this.renderFrame(this.xPos, this.yPos + 12 + 3, this.xPos + this.width, this.yPos + this.height);
        GL11.glPushMatrix();
        RenderHelper.enableStandardItemLighting();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int slot = 9;
        int yo = 0;
        while (yo < 3) {
            int xo = 0;
            while (xo < 9) {
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GuiIngame.itemRenderer.renderItemIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, Client.mc.thePlayer.inventory.mainInventory[slot], this.xPos + xo * 18, this.yPos + 14 + 2 + 18 * yo);
                GuiIngame.itemRenderer.renderItemOverlayIntoGUI(Client.mc.fontRenderer, Client.mc.renderEngine, Client.mc.thePlayer.inventory.mainInventory[slot], this.xPos + xo * 18, this.yPos + 14 + 2 + 18 * yo);
                ++slot;
                ++xo;
            }
            ++yo;
        }
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
}

