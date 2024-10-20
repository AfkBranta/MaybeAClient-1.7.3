/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.GuiScreen;

public class GuiUnused
extends GuiScreen {
    private String message1;
    private String message2;

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawGradientRect(0, 0, this.width, this.height, -12574688, -11530224);
        this.drawCenteredString(this.fontRenderer, this.message1, this.width / 2, 90, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, this.message2, this.width / 2, 110, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }
}

