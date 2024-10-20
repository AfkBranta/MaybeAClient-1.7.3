/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.altman;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.altman.AccountInfo;
import net.skidcode.gh.maybeaclient.gui.altman.GuiEditPassword;
import net.skidcode.gh.maybeaclient.gui.altman.GuiPassSlot;

public class GuiPwdManager
extends GuiScreen {
    public GuiScreen prev;
    public GuiButton edit;
    public GuiButton add;
    public GuiButton remove;
    public GuiButton done;
    public GuiPassSlot slot;
    public int currentlySelected = -1;
    public AccountInfo info;

    public GuiPwdManager(GuiScreen prev, AccountInfo accountInfo) {
        this.prev = prev;
        this.info = accountInfo;
    }

    @Override
    public void keyTyped(char var1, int key) {
        if (key == 1) {
            this.mc.displayGuiScreen(this.prev);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.controlList.clear();
        this.edit = new GuiSmallButton(3, this.width / 2 - 100 - 5, this.height - 47, 100, 20, "Edit");
        this.add = new GuiSmallButton(1, this.width / 2 + 5, this.height - 47, 100, 20, "Add");
        this.remove = new GuiSmallButton(2, this.width / 2 - 100 - 5, this.height - 47 + 20 + 2, 100, 20, "Remove");
        this.done = new GuiSmallButton(4, this.width / 2 + 5, this.height - 47 + 20 + 2, 100, 20, "Done");
        if (this.currentlySelected == -1) {
            this.select(-1);
        }
        this.controlList.add(this.edit);
        this.controlList.add(this.remove);
        this.controlList.add(this.add);
        this.controlList.add(this.done);
        this.slot = new GuiPassSlot(this);
        this.slot.registerScrollButtons(this.controlList, 8, 9);
    }

    public void select(int i) {
        this.currentlySelected = i;
        this.remove.enabled = i >= 0;
        this.edit.enabled = this.remove.enabled;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case 1: {
                    this.mc.displayGuiScreen(new GuiEditPassword(this, this.info, null));
                    break;
                }
                case 2: {
                    this.info.removePassword(this.currentlySelected);
                    this.select(-1);
                    Client.writeCurrentAccounts();
                    break;
                }
                case 3: {
                    this.mc.displayGuiScreen(new GuiEditPassword(this, this.info, this.info.pwds.get(this.currentlySelected)));
                    break;
                }
                case 4: {
                    this.mc.displayGuiScreen(this.prev);
                }
            }
        }
    }

    @Override
    public void drawScreen(int mX, int mY, float rendTicks) {
        this.slot.drawScreen(mX, mY, rendTicks);
        this.drawCenteredString(this.fontRenderer, "Manage Passwords", this.width / 2, 12, 0xFFFFFF);
        super.drawScreen(mX, mY, rendTicks);
    }
}

