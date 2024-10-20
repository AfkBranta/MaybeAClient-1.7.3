/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.gui.click;

import net.minecraft.src.ScaledResolution;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import org.lwjgl.opengl.GL11;

public class SettingsTab
extends Tab {
    public Hack hack;
    public Tab parent;
    public int hackID;
    public Setting selected = null;
    public int selectedMinX = 0;
    public int selectedMinY = 0;
    public int selectedMaxX = 0;
    public int selectedMaxY = 0;

    public SettingsTab(Tab parent, Hack hack, int hackID) {
        super("");
        this.parent = parent;
        this.hack = hack;
        this.hackID = hackID;
    }

    @Override
    public boolean isPointInside(float x, float y) {
        if (this.parent.minimized) {
            return false;
        }
        return super.isPointInside(x, y);
    }

    @Override
    public void onSelect(int click, int x, int y) {
        if (this.parent.minimized) {
            return;
        }
        int sx = this.xPos;
        int sy = this.yPos;
        for (Setting s : this.hack.settingsArr) {
            if (s.hidden) continue;
            int h = s.getSettingHeight();
            if (x >= sx && x <= sx + this.width && y >= sy && y <= sy + h) {
                s.onPressedInside(sx + 1, sy + 1, sx + this.width - 1, sy + h - 1, x, y, click);
                this.selected = s;
                this.selectedMinX = sx + 1;
                this.selectedMinY = sy + 1;
                this.selectedMaxX = sx + this.width - 1;
                this.selectedMaxY = sy + h - 1;
                break;
            }
            sy += h;
        }
    }

    @Override
    public void renderIngame() {
    }

    @Override
    public void mouseMovedSelected(int click, int x, int y) {
        if (this.parent.minimized) {
            return;
        }
        if (this.selected != null) {
            this.selected.onMouseMoved(this.selectedMinX, this.selectedMinY, this.selectedMinX + this.width, this.selectedMaxY, x, y, click);
        }
    }

    @Override
    public void onDeselect(int click, int x, int y) {
        if (this.parent.minimized) {
            return;
        }
        super.onDeselect(click, x, y);
        if (this.selected != null) {
            this.selected.onDeselect(this, this.selectedMinX, this.selectedMinY, this.selectedMinX + this.width, this.selectedMaxY, x, y, click);
            this.selected = null;
        }
        Client.saveModules();
    }

    @Override
    public void render() {
        int sHeight;
        if (this.parent.minimized) {
            return;
        }
        this.xPos = this.parent.xPos + this.parent.width + 3;
        this.yPos = this.parent.yPos + this.hackID * 12 + 14;
        int settingsHeight = 0;
        int settingsWidth = 60;
        for (Setting set : this.hack.settingsArr) {
            if (set.hidden) continue;
            settingsHeight += set.getSettingHeight();
            int w = set.getSettingWidth();
            if (w <= settingsWidth) continue;
            settingsWidth = w;
        }
        ScaledResolution scaledResolution = new ScaledResolution(Client.mc.gameSettings, Client.mc.displayWidth, Client.mc.displayHeight);
        if (this.yPos + settingsHeight > scaledResolution.getScaledHeight()) {
            this.yPos -= settingsHeight - 12;
        }
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        this.renderFrameBackGround(this.xPos, this.yPos, this.xPos + settingsWidth, this.yPos + settingsHeight);
        int height = this.yPos;
        for (Setting set : this.hack.settingsArr) {
            if (set.hidden) continue;
            sHeight = set.getSettingHeight();
            set.renderElement(this, this.xPos + 1, height + 1, this.xPos + settingsWidth - 1, height + sHeight - 1);
            height += sHeight;
        }
        this.width = settingsWidth;
        this.height = settingsHeight;
        this.renderFrameOutlines(this.xPos, this.yPos, this.xPos + settingsWidth, this.yPos + settingsHeight);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        height = this.yPos;
        for (Setting set : this.hack.settingsArr) {
            if (set.hidden) continue;
            sHeight = set.getSettingHeight();
            set.renderText(this.xPos + 1, height + 1);
            height += sHeight;
        }
    }
}

