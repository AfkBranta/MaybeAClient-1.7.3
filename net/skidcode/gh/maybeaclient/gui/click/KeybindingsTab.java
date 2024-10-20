package net.skidcode.gh.maybeaclient.gui.click;

import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.KeybindingsHack;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class KeybindingsTab
extends Tab {
    public static KeybindingsTab instance;
    boolean first = true;

    public KeybindingsTab() {
        super("Keybindings", 0, 14);
        this.yPos = 15;
        this.yDefPos = 15;
        instance = this;
    }

    @Override
    public void renderIngame() {
        if (KeybindingsHack.instance.status) {
            super.renderIngame();
        }
    }

    public void renderName(boolean alignRight) {
        if (alignRight) {
            int xStart = this.xPos;
            int yStart = this.yPos;
            this.renderFrame(xStart, yStart, xStart + this.width, yStart + 12);
            Client.mc.fontRenderer.drawString(this.name, xStart + this.width - Client.mc.fontRenderer.getStringWidth(this.name), yStart + 2, 0xFFFFFF);
        } else {
            super.renderName();
        }
    }

    @Override
    public void renderMinimized() {
        this.height = 12;
        this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
        this.renderName(KeybindingsHack.instance.alignment.currentMode.equalsIgnoreCase("Right"));
    }

    @Override
    public void render() {
        int height = 14;
        int savdHeight = this.height;
        int savdWidth = this.width;
        int width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
        boolean hasBinds = false;
        for (Hack h : Client.hacksByName.values()) {
            if (h.keybinding.value == 0) continue;
            height += 12;
            int w = Client.mc.fontRenderer.getStringWidth("[" + (Object)((Object)ChatColor.LIGHTCYAN) + h.keybinding.valueToString() + (Object)((Object)ChatColor.WHITE) + "] " + h.name) + 2;
            if (w > width) {
                width = w;
            }
            hasBinds = true;
        }
        boolean alignRight = KeybindingsHack.instance.alignment.currentMode.equalsIgnoreCase("Right");
        boolean expandTop = KeybindingsHack.instance.expand.currentMode.equalsIgnoreCase("Top");
        this.height = height;
        this.width = width;
        if (!this.minimized) {
            if (this.first) {
                this.first = false;
            } else {
                boolean sav = false;
                if (expandTop && savdHeight != this.height) {
                    this.yPos -= this.height - savdHeight;
                    sav = true;
                }
                if (alignRight && savdWidth != this.width) {
                    this.xPos -= this.width - savdWidth;
                    sav = true;
                }
                if (sav) {
                    Client.saveClickGUI();
                }
            }
        }
        if (this.minimized) {
            this.width = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
            if (alignRight && savdWidth != this.width) {
                this.xPos -= this.width - savdWidth;
            }
            this.renderMinimized();
            return;
        }
        this.renderName(alignRight);
        if (!hasBinds) {
            return;
        }
        this.renderFrame(this.xPos, this.yPos + 12 + 3, this.xPos + this.width, this.yPos + this.height);
        int i = 1;
        for (Hack h : Client.hacksByName.values()) {
            if (h.keybinding.value == 0) continue;
            String s = "[" + (Object)((Object)ChatColor.LIGHTCYAN) + h.keybinding.valueToString() + (Object)((Object)ChatColor.WHITE) + "] " + h.name;
            if (alignRight) {
                Client.mc.fontRenderer.drawString(s, this.xPos + this.width - Client.mc.fontRenderer.getStringWidth(s), this.yPos + i * 12 + 3 + 2, 0xFFFFFF);
            } else {
                Client.mc.fontRenderer.drawString(s, this.xPos + 2, this.yPos + i * 12 + 3 + 2, 0xFFFFFF);
            }
            ++i;
        }
    }
}

