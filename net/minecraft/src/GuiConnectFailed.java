/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;
import net.skidcode.gh.maybeaclient.hacks.AutoReconnectHack;

public class GuiConnectFailed
extends GuiScreen {
    private String errorMessage;
    private String errorDetail;
    public GuiButton reconnectButton;
    public boolean beganReconnect = false;
    public long timeMS = 0L;
    public long counter = 0L;

    public GuiConnectFailed(String var1, String var2, Object ... var3) {
        StringTranslate var4 = StringTranslate.getInstance();
        this.errorMessage = var4.translateKey(var1);
        this.errorDetail = var3 != null ? var4.translateKeyFormat(var2, var3) : var4.translateKey(var2);
    }

    @Override
    public void updateScreen() {
    }

    @Override
    protected void keyTyped(char var1, int var2) {
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.toMenu")));
        this.reconnectButton = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12 + 20, "Reconnect");
        this.controlList.add(this.reconnectButton);
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.id == 0) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        if (var1.id == this.reconnectButton.id) {
            String ipport = this.mc.gameSettings.realLastServer.replaceAll("_", ":");
            String[] var3 = ipport.split(":");
            this.mc.displayGuiScreen(new GuiConnecting(this.mc, var3[0], var3.length > 1 ? GuiMultiplayer.getPort(var3[1], 25565) : 25565));
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, this.errorDetail, this.width / 2, this.height / 2 - 10, 0xFFFFFF);
        if (AutoReconnectHack.instance.status && this.reconnectButton != null) {
            if (!this.beganReconnect) {
                this.beganReconnect = true;
                this.timeMS = System.currentTimeMillis();
                this.counter = AutoReconnectHack.instance.delaySeconds.value * 1000;
                this.reconnectButton.displayString = "Reconnect (" + AutoReconnectHack.instance.delaySeconds.value + ")";
            } else {
                long time = System.currentTimeMillis();
                this.counter -= time - this.timeMS;
                this.timeMS = time;
                this.reconnectButton.displayString = "Reconnect (" + (int)Math.ceil((double)this.counter / 1000.0) + ")";
                if (this.counter <= 0L) {
                    this.actionPerformed(this.reconnectButton);
                }
            }
        }
        super.drawScreen(var1, var2, var3);
    }
}

