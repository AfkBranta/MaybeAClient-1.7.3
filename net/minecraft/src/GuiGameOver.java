/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiGameOver
extends GuiScreen {
    @Override
    public void initGui() {
        this.controlList.clear();
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72, "Respawn"));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, "Title menu"));
        if (this.mc.session == null) {
            ((GuiButton)this.controlList.get((int)1)).enabled = false;
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        int cfr_ignored_0 = var1.id;
        if (var1.id == 1) {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
        if (var1.id == 2) {
            this.mc.changeWorld1(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawGradientRect(0, 0, this.width, this.height, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        this.drawCenteredString(this.fontRenderer, "Game over!", this.width / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        this.drawCenteredString(this.fontRenderer, "Score: &e" + this.mc.thePlayer.getScore(), this.width / 2, 100, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

