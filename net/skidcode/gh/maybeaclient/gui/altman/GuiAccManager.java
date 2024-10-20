/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.altman;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.altman.AccountInfo;
import net.skidcode.gh.maybeaclient.gui.altman.GuiAltSlot;
import net.skidcode.gh.maybeaclient.gui.altman.GuiEditAccount;
import net.skidcode.gh.maybeaclient.gui.altman.GuiPwdManager;

public class GuiAccManager
extends GuiScreen {
    public GuiScreen prev;
    public GuiButton edit;
    public GuiButton remove;
    public GuiButton add;
    public GuiButton autologin;
    public GuiButton select;
    public GuiButton cancel;
    public int server;
    public GuiAltSlot slot;
    public static ArrayList<AccountInfo> accounts = new ArrayList();
    public static HashMap<String, AccountInfo> usernames = new HashMap();
    public int currentlySelected = -1;

    public GuiAccManager(GuiScreen prev, int server) {
        this.prev = prev;
        this.server = server;
    }

    @Override
    public void keyTyped(char var1, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(this.prev);
        }
    }

    public static void removeAccount(AccountInfo info) {
        accounts.remove(info);
        usernames.remove(info.name);
    }

    public static void removeAccount(int index) {
        AccountInfo info = accounts.remove(index);
        usernames.remove(info.name);
    }

    public static void addAccount(AccountInfo info) {
        accounts.add(info);
        usernames.put(info.name, info);
    }

    public static void changeUsername(AccountInfo info, String text) {
        usernames.put(text, usernames.remove(info.name));
        info.name = text;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.controlList.clear();
        this.edit = new GuiSmallButton(3, this.width / 2 - 50 - 100 - 5, this.height - 47, 100, 20, "Edit");
        this.remove = new GuiSmallButton(2, this.width / 2 - 50, this.height - 47, 100, 20, "Remove");
        this.add = new GuiSmallButton(1, this.width / 2 - 50 + 100 + 5, this.height - 47, 100, 20, "New");
        this.autologin = new GuiSmallButton(4, this.width / 2 - 50 - 100 - 5, this.height - 47 + 20 + 2, 100, 20, "AutoLogin");
        this.select = new GuiSmallButton(5, this.width / 2 - 50, this.height - 47 + 20 + 2, 100, 20, "Select");
        this.cancel = new GuiSmallButton(6, this.width / 2 - 50 + 100 + 5, this.height - 47 + 20 + 2, 100, 20, "Cancel");
        if (this.currentlySelected == -1) {
            this.selectAccount(-1);
        }
        this.controlList.add(this.edit);
        this.controlList.add(this.remove);
        this.controlList.add(this.add);
        this.controlList.add(this.autologin);
        this.controlList.add(this.select);
        this.controlList.add(this.cancel);
        this.slot = new GuiAltSlot(this);
        this.slot.registerScrollButtons(this.controlList, 8, 9);
    }

    public void selectAccount(int i) {
        this.currentlySelected = i;
        this.select.enabled = this.currentlySelected >= 0;
        this.autologin.enabled = this.select.enabled;
        this.remove.enabled = this.select.enabled;
        this.edit.enabled = this.select.enabled;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 1: {
                    this.mc.displayGuiScreen(new GuiEditAccount(this));
                    break;
                }
                case 2: {
                    GuiAccManager.removeAccount(this.currentlySelected);
                    this.selectAccount(-1);
                    Client.writeCurrentAccounts();
                    break;
                }
                case 3: {
                    this.mc.displayGuiScreen(new GuiEditAccount(this, accounts.get(this.currentlySelected)));
                    break;
                }
                case 4: {
                    this.mc.displayGuiScreen(new GuiPwdManager(this, accounts.get(this.currentlySelected)));
                    break;
                }
                case 5: {
                    this.mc.session.username = GuiAccManager.accounts.get((int)this.currentlySelected).name;
                    this.mc.displayGuiScreen(this.prev);
                    break;
                }
                case 6: {
                    this.mc.displayGuiScreen(this.prev);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mX, int mY, float rendTicks) {
        this.slot.drawScreen(mX, mY, rendTicks);
        this.drawCenteredString(this.fontRenderer, "Manage Accounts", this.width / 2, 12, 0xFFFFFF);
        super.drawScreen(mX, mY, rendTicks);
    }
}

