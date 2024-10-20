/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.click;

import java.util.ArrayList;
import net.minecraft.src.ScaledResolution;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.ArrayListHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;

public class ArrayListTab
extends Tab {
    public static ArrayListTab instance;
    public int yOffset = 0;
    public int oldEnabledCnt = 0;
    boolean first = true;

    public ArrayListTab() {
        super("Enabled Modules");
        this.xPos = 10;
        this.xDefPos = 10;
        this.yPos = 100;
        this.yDefPos = 100;
        this.height = 12;
        instance = this;
    }

    @Override
    public void renderIngame() {
        if (ArrayListHack.instance.status) {
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
        this.renderName(ArrayListHack.instance.alignment.currentMode.equalsIgnoreCase("Right"));
    }

    @Override
    public void render() {
        ArrayList<String> enabled = new ArrayList<String>();
        int savdHeight = this.height;
        int savdWidth = this.width;
        int totalHeight = 0;
        int totalWidth = Client.mc.fontRenderer.getStringWidth(this.name) + 2;
        for (Hack h : Client.hacksByName.values()) {
            if (!h.status) continue;
            totalHeight += 12;
            String name = h.getNameForArrayList();
            int size = Client.mc.fontRenderer.getStringWidth(name) + 2;
            if (totalWidth < size) {
                totalWidth = size;
            }
            enabled.add(name);
        }
        this.width = totalWidth;
        this.height = totalHeight + 14;
        if (this.minimized) {
            this.height = 12;
        }
        boolean alignRight = ArrayListHack.instance.alignment.currentMode.equalsIgnoreCase("Right");
        boolean expandTop = ArrayListHack.instance.expand.currentMode.equalsIgnoreCase("Top");
        ScaledResolution scaledResolution = new ScaledResolution(Client.mc.gameSettings, Client.mc.displayWidth, Client.mc.displayHeight);
        if (ArrayListHack.instance.staticPositon.currentMode.equalsIgnoreCase("Bottom Right")) {
            alignRight = true;
            expandTop = true;
            this.xPos = scaledResolution.getScaledWidth() - this.width;
            this.yPos = scaledResolution.getScaledHeight() - this.height;
        } else if (ArrayListHack.instance.staticPositon.currentMode.equalsIgnoreCase("Bottom Left")) {
            alignRight = false;
            expandTop = true;
            this.xPos = 0;
            this.yPos = scaledResolution.getScaledHeight() - this.height;
        } else if (ArrayListHack.instance.staticPositon.currentMode.equalsIgnoreCase("Top Right")) {
            alignRight = true;
            expandTop = false;
            this.xPos = scaledResolution.getScaledWidth() - this.width;
            this.yPos = 0;
        } else if (ArrayListHack.instance.staticPositon.currentMode.equalsIgnoreCase("Top Left")) {
            alignRight = false;
            expandTop = false;
            this.xPos = 0;
            this.yPos = 0;
        }
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
            this.renderMinimized();
            return;
        }
        this.renderName(alignRight);
        this.renderFrame(this.xPos, this.yPos + 15, this.xPos + totalWidth, this.yPos + 15 + totalHeight + this.yOffset);
        if (ArrayListHack.instance.sortMode.currentMode.equalsIgnoreCase("Ascending")) {
            enabled.sort(ArrayListHack.SorterAZ.inst);
        } else if (ArrayListHack.instance.sortMode.currentMode.equalsIgnoreCase("Descending")) {
            enabled.sort(ArrayListHack.SorterZA.inst);
        }
        int i = 0;
        while (i < enabled.size()) {
            String s = (String)enabled.get(i);
            int rendX = alignRight ? this.xPos + this.width - Client.mc.fontRenderer.getStringWidth(s) : this.xPos + 2;
            int rendY = this.yPos + i * 12 + 15 + 2;
            Client.mc.fontRenderer.drawString(s, rendX, rendY, 0xFFFFFF);
            ++i;
        }
    }

    @Override
    public void wheelMoved(int wheel, int x, int y) {
    }
}

