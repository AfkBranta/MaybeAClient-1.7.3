/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.StringTranslate;

public class GuiDownloadTerrain
extends GuiScreen {
    private NetClientHandler netHandler;
    private int updateCounter = 0;

    public GuiDownloadTerrain(NetClientHandler var1) {
        this.netHandler = var1;
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    @Override
    public void initGui() {
        this.controlList.clear();
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
        if (this.updateCounter % 20 == 0) {
            this.netHandler.addToSendQueue(new Packet0KeepAlive());
        }
        if (this.netHandler != null) {
            this.netHandler.processReadPackets();
        }
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawBackground(0);
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }
}

