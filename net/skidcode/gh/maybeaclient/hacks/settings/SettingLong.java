/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public class SettingLong
extends Setting {
    public long minGUI;
    public long maxGUI;
    public long value;
    public long initialValue;

    public SettingLong(Hack hack, String name, long initialValue, long minGUI, long maxGUI) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
        this.minGUI = minGUI;
        this.maxGUI = maxGUI;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return "" + this.value;
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Long.parseLong(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void reset() {
        this.setValue(this.initialValue);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setLong(this.name, this.value);
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(String.valueOf(this.name) + " - " + this.getValue(), x + 2, y + 2, 0xFFFFFF);
    }

    @Override
    public int getSettingWidth() {
        return Client.mc.fontRenderer.getStringWidth(String.valueOf(this.name) + " - " + this.getValue()) + 5;
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int sizeX = xMax - xMin;
        int mouseOff = mouseX - xMin;
        double step = (this.maxGUI - this.minGUI) / (long)sizeX;
        this.setValue(Math.round((double)(this.minGUI * 100L) + (double)mouseOff * step * 100.0) / 100L);
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        int diff1 = xEnd - xStart;
        double diff2 = (this.maxGUI - this.minGUI) / (long)diff1;
        long val = this.value;
        if (val > this.maxGUI) {
            val = this.maxGUI;
        }
        if (val < this.minGUI) {
            val = this.minGUI;
        }
        int diff3 = (int)((double)val / diff2 - (double)this.minGUI / diff2);
        tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int mouseOff = mouseX - xMin;
        int sizeX = xMax - xMin;
        double step = (this.maxGUI - this.minGUI) / (long)sizeX;
        long value = Math.round((double)(this.minGUI * 100L) + (double)mouseOff * step * 100.0) / 100L;
        if (value > this.maxGUI) {
            value = this.maxGUI;
        }
        if (value < this.minGUI) {
            value = this.minGUI;
        }
        this.setValue(value);
    }

    public void onDeselect(Tab tab, int mouseX, int mouseY, int mouseClick) {
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getLong(this.name));
        }
    }
}

