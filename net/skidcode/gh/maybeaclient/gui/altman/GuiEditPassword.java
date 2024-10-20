/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.altman;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiTextField;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.altman.AccountInfo;
import net.skidcode.gh.maybeaclient.gui.altman.PasswordInfo;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class GuiEditPassword
extends GuiScreen {
    public GuiScreen parent;
    public AccountInfo info;
    public PasswordInfo passInfo;
    public GuiTextField serverIPField;
    public GuiTextField password;
    public GuiTextField prompt;
    public GuiTextField command;
    public GuiButton done;
    public GuiButton showPassword;
    public GuiButton promtMatchMode;
    public boolean needsAdding = false;
    public boolean showingPasswd = false;
    public PasswordInfo.MatchMode orig;

    public GuiEditPassword(GuiScreen parent, AccountInfo info, PasswordInfo pinf) {
        this.parent = parent;
        this.info = info;
        this.passInfo = pinf;
        if (pinf == null) {
            this.needsAdding = true;
            this.passInfo = new PasswordInfo("", "");
        } else {
            this.orig = pinf.mode;
        }
    }

    @Override
    protected void actionPerformed(GuiButton b) {
        if (b.enabled) {
            switch (b.id) {
                case 1: {
                    this.info.removePassword(this.passInfo);
                    this.passInfo.loginCommand = this.command.getText();
                    this.passInfo.loginPrompt = this.prompt.getText();
                    this.passInfo.password = this.password.getText();
                    this.passInfo.serverIP = this.serverIPField.getText();
                    this.info.addPassword(this.passInfo);
                    Client.writeCurrentAccounts();
                    this.mc.displayGuiScreen(this.parent);
                    break;
                }
                case 2: {
                    this.passInfo.mode = this.orig;
                    this.mc.displayGuiScreen(this.parent);
                    break;
                }
                case 3: {
                    boolean bl = this.showingPasswd = !this.showingPasswd;
                    if (this.showingPasswd) {
                        this.showPassword.displayString = "Show";
                        break;
                    }
                    this.showPassword.displayString = "Hide";
                    break;
                }
                case 4: {
                    int n = this.passInfo.mode.ordinal() + 1;
                    if (n >= PasswordInfo.MatchMode.values().length) {
                        n = 0;
                    }
                    this.passInfo.mode = PasswordInfo.MatchMode.values()[n];
                }
            }
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        this.serverIPField.textboxKeyTyped(var1, var2);
        this.password.textboxKeyTyped(var1, var2);
        this.prompt.textboxKeyTyped(var1, var2);
        this.command.textboxKeyTyped(var1, var2);
        if (var1 == '\r') {
            this.actionPerformed(this.done);
        }
        this.checkButtons();
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        this.serverIPField.mouseClicked(var1, var2, var3);
        this.password.mouseClicked(var1, var2, var3);
        this.prompt.mouseClicked(var1, var2, var3);
        this.command.mouseClicked(var1, var2, var3);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.controlList.clear();
        this.serverIPField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 - 72, 200, 20, "");
        this.serverIPField.setMaxStringLength(32);
        this.serverIPField.isFocused = true;
        this.password = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 - 72 + 36, 200, 20, "");
        this.prompt = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 - 72 + 72, 200, 20, "");
        this.command = new GuiTextField(this.fontRenderer, this.width / 2 - 100, this.height / 2 - 72 + 108, 200, 20, "");
        if (this.passInfo != null) {
            this.serverIPField.setText(this.passInfo.serverIP);
            this.password.setText(this.passInfo.password);
            this.prompt.setText(this.passInfo.loginPrompt);
            this.command.setText(this.passInfo.loginCommand);
        }
        this.done = new GuiSmallButton(1, this.width / 2 - 50 + 52, this.height / 2 + 72, 100, 20, "Done");
        this.controlList.add(new GuiSmallButton(2, this.width / 2 - 50 - 52, this.height / 2 + 72, 100, 20, "Cancel"));
        this.controlList.add(this.done);
        this.showPassword = new GuiSmallButton(3, this.width / 2 + 102, this.height / 2 - 72 + 36, 30, 20, "Hide");
        this.controlList.add(this.showPassword);
        this.promtMatchMode = new GuiSmallButton(4, this.width / 2 + 102, this.height / 2 - 72 + 72, 75, 20, "");
        this.controlList.add(this.promtMatchMode);
        this.checkButtons();
    }

    @Override
    public void drawScreen(int mX, int mY, float rendTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Edit password", this.width / 2, 12, 0xFFFFFF);
        this.promtMatchMode.displayString = this.passInfo.mode.name;
        Client.mc.fontRenderer.drawString("Password", this.password.xPos, this.password.yPos - 10, 0xFFFFFF);
        Client.mc.fontRenderer.drawString("Prompt (" + this.prompt.getText().replace("&", "\u00a7") + (Object)((Object)ChatColor.WHITE) + ")", this.prompt.xPos, this.prompt.yPos - 10, 0xFFFFFF);
        Client.mc.fontRenderer.drawString("Command", this.command.xPos, this.command.yPos - 10, 0xFFFFFF);
        String prevPass = this.password.getText();
        if (!this.showingPasswd) {
            String s = "";
            int i = 0;
            while (i < this.password.getText().length()) {
                s = String.valueOf(s) + "*";
                ++i;
            }
            this.password.setText(s);
        }
        if (this.info.passwords.get(this.serverIPField.getText()) != null && (this.needsAdding || this.info.passwords.get(this.serverIPField.getText()) != this.passInfo)) {
            Client.mc.fontRenderer.drawString("Server IP", this.serverIPField.xPos, this.serverIPField.yPos - 10, -43691);
            this.serverIPField.drawRedTextBox();
            this.done.enabled = false;
        } else {
            Client.mc.fontRenderer.drawString("Server IP", this.serverIPField.xPos, this.serverIPField.yPos - 10, 0xFFFFFF);
            this.serverIPField.drawTextBox();
            this.checkButtons();
        }
        this.password.drawTextBox();
        this.prompt.drawTextBox();
        this.command.drawTextBox();
        if (!this.showingPasswd) {
            this.password.setText(prevPass);
        }
        super.drawScreen(mX, mY, rendTicks);
    }

    public void checkButtons() {
        this.done.enabled = this.serverIPField.getText().length() > 0 && this.password.getText().length() > 0 && this.prompt.getText().length() > 0 && this.command.getText().length() > 0;
    }
}

