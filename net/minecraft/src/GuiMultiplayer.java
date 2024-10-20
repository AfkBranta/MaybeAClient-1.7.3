/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer
extends GuiScreen {
    private GuiScreen parentScreen;
    private GuiTextField serverIp;
    public GuiTextField username;

    public GuiMultiplayer(GuiScreen var1) {
        this.parentScreen = var1;
    }

    @Override
    public void updateScreen() {
        this.serverIp.updateCursorCounter();
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("multiplayer.connect")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        String var2 = this.mc.gameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton)this.controlList.get((int)0)).enabled = var2.length() > 0;
        this.serverIp = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18, 200, 20, var2);
        this.serverIp.isFocused = true;
        this.serverIp.setMaxStringLength(32);
        this.username = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18 + 24, 150, 20, this.mc.session.username);
        this.controlList.add(new GuiButton(2, this.width / 2 - 100 + 152, this.height / 4 - 10 + 50 + 18 + 24, 50, 20, "Change"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == 1) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (var1.id == 0) {
                String var2 = this.serverIp.getText();
                this.mc.gameSettings.realLastServer = this.mc.gameSettings.lastServer = var2.replaceAll(":", "_");
                this.mc.gameSettings.saveOptions();
                String[] var3 = var2.split(":");
                this.mc.displayGuiScreen(new GuiConnecting(this.mc, var3[0], var3.length > 1 ? GuiMultiplayer.getPort(var3[1], 25565) : 25565));
            } else if (var1.id == 2) {
                this.mc.session.username = this.username.getText();
            }
        }
    }

    public static int getPort(String var1, int var2) {
        try {
            return Integer.parseInt(var1.trim());
        }
        catch (Exception var4) {
            return var2;
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        this.serverIp.textboxKeyTyped(var1, var2);
        this.username.textboxKeyTyped(var1, var2);
        if (var1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
        ((GuiButton)this.controlList.get((int)0)).enabled = this.serverIp.getText().length() > 0;
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        this.serverIp.mouseClicked(var1, var2, var3);
        this.username.mouseClicked(var1, var2, var3);
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("multiplayer.title"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.drawString(this.fontRenderer, var4.translateKey("multiplayer.info1"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 0xA0A0A0);
        this.drawString(this.fontRenderer, var4.translateKey("multiplayer.info2"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 0xA0A0A0);
        this.drawString(this.fontRenderer, var4.translateKey("multiplayer.ipinfo"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 0xA0A0A0);
        this.serverIp.drawTextBox();
        this.username.drawTextBox();
        super.drawScreen(var1, var2, var3);
    }
}

