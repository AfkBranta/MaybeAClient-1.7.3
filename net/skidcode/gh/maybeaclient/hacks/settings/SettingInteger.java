/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public class SettingInteger
extends Setting {
    public int minGUI;
    public int maxGUI;
    public int value;
    public int initialValue;

    public SettingInteger(Hack hack, String name, int initialValue, int minGUI, int maxGUI) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
        this.minGUI = minGUI;
        this.maxGUI = maxGUI;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return "" + this.value;
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Integer.parseInt(value);
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
        output.setInteger(this.name, this.value);
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
        double step = (this.maxGUI - this.minGUI) / sizeX;
        this.setValue((int)Math.round((double)(this.minGUI * 100) + (double)mouseOff * step * 100.0) / 100);
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        double diff1 = xEnd - xStart;
        double diff2 = (double)(this.maxGUI - this.minGUI) / diff1;
        int val = this.value;
        if (val > this.maxGUI) {
            val = this.maxGUI;
        }
        if (val < this.minGUI) {
            val = this.minGUI;
        }
        int diff3 = (int)Math.round((double)val / diff2 - (double)this.minGUI / diff2);
        tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int mouseOff = mouseX - xMin;
        double sizeX = xMax - xMin;
        double step = (double)(this.maxGUI - this.minGUI) / sizeX;
        int value = (int)Math.round((double)(this.minGUI * 100) + (double)mouseOff * step * 100.0) / 100;
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
            this.setValue(input.getInteger(this.name));
        }
    }
}

