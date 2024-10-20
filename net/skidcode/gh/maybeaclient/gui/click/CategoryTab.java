/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.gui.click;

import net.minecraft.src.Tessellator;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.gui.click.SettingsTab;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import org.lwjgl.opengl.GL11;

public class CategoryTab
extends Tab {
    public Category category;
    public boolean resize = true;
    int hoveringOver = -1;
    long hoverStart = -1L;
    int hoverX;
    int hoverY;
    boolean onHoverRender = false;
    boolean canToggle = true;

    public CategoryTab(Category category) {
        super(category.name);
        this.category = category;
    }

    public CategoryTab(Category category, int x, int y, int width) {
        this(category);
        this.xPos = x;
        this.yPos = y;
        this.xDefPos = x;
        this.yDefPos = y;
        this.width = width;
    }

    @Override
    public void stopHovering() {
        this.hoveringOver = -1;
        this.hoverStart = -1L;
        this.onHoverRender = false;
    }

    @Override
    public void renderIngame() {
    }

    @Override
    public void mouseHovered(int x, int y, int click) {
        if (!this.minimized && y > this.yPos + 12 + 2) {
            int haxcnt = this.category.hacks.size() - 1;
            int sel = haxcnt - (this.yPos + this.height - y) / 12;
            if (this.hoveringOver == sel) {
                if (System.currentTimeMillis() - this.hoverStart > 4000L && !this.onHoverRender) {
                    this.onHoverRender = true;
                    this.hoverX = x;
                    this.hoverY = y;
                }
            } else {
                this.hoveringOver = sel;
                this.hoverStart = System.currentTimeMillis();
                this.onHoverRender = false;
            }
        }
    }

    @Override
    public void onSelect(int click, int x, int y) {
        if (!this.minimized && y > this.yPos + 12 + 2 && this.canToggle) {
            int haxcnt = this.category.hacks.size() - 1;
            int sel = haxcnt - (this.yPos + this.height - y) / 12;
            Hack hacc = this.category.hacks.get(sel);
            if (click == 0) {
                hacc.toggle();
            } else if (click == 1) {
                boolean bl = hacc.expanded = !hacc.expanded;
                if (hacc.expanded) {
                    hacc.tab = new SettingsTab(this, hacc, sel);
                    ClickGUI.tabs.add(0, hacc.tab);
                } else {
                    ClickGUI.tabs.remove(hacc.tab);
                    hacc.tab = null;
                }
            }
            this.canToggle = false;
        }
        super.onSelect(click, x, y);
    }

    @Override
    public void onDeselect(int click, int x, int y) {
        this.canToggle = true;
        super.onDeselect(click, x, y);
    }

    public void renderModules() {
        Tessellator tess = Tessellator.instance;
        int xStart = this.xPos;
        int yStart = this.yPos + 12 + 3;
        int xEnd = this.xPos + this.width;
        int yEnd = this.yPos + this.height;
        this.height = this.category.hacks.size() * 12 + 14;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        this.renderFrameBackGround(xStart, yStart, xEnd, yEnd);
        int i = 0;
        while (i < this.category.hacks.size()) {
            Hack h = this.category.hacks.get(i);
            if (h.status) {
                this.renderFrameBackGround(xStart, yStart + 12 * i, xEnd, yStart + 12 * i + 12, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
            }
            ++i;
        }
        this.renderFrameOutlines(xStart, yStart, xEnd, yEnd);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        i = 0;
        while (i < this.category.hacks.size()) {
            Client.mc.fontRenderer.drawString(this.category.hacks.get((int)i).name, xStart + 2, yStart + 2 + 12 * i, 0xFFFFFF);
            ++i;
        }
    }

    @Override
    public void render() {
        if (this.minimized) {
            this.renderMinimized();
            return;
        }
        if (this.resize) {
            int i = 0;
            while (i < this.category.hacks.size()) {
                int width = Client.mc.fontRenderer.getStringWidth(this.category.hacks.get((int)i).name) + 2;
                if (width > this.width) {
                    this.width = width;
                }
                ++i;
            }
            this.resize = false;
        }
        super.render();
        this.renderModules();
    }
}

