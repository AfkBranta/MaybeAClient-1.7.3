/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public class SettingDouble
extends Setting {
    public double value;
    public double initialValue;
    public double minGUI;
    public double maxGUI;
    public boolean fixedStep = false;
    public double step = 0.0;

    public SettingDouble(Hack hack, String name, double initialValue, double minGUI, double maxGUI) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
        this.minGUI = minGUI;
        this.maxGUI = maxGUI;
    }

    public SettingDouble(Hack hack, String name, double initialValue, double minGUI, double maxGUI, double step) {
        this(hack, name, initialValue, minGUI, maxGUI);
        this.fixedStep = true;
        this.step = step;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return "" + this.value;
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(String.valueOf(this.name) + " - " + String.format("%.2f", this.getValue()), x + 2, y + 2, 0xFFFFFF);
    }

    @Override
    public int getSettingWidth() {
        int w1 = Client.mc.fontRenderer.getStringWidth(String.valueOf(this.name) + " - " + String.format("%.2f", this.getValue())) + 5;
        if (this.fixedStep) {
            double w2 = (this.maxGUI - this.minGUI) / this.step;
            if ((double)w1 > w2) {
                return w1;
            }
            return (int)Math.floor(w2);
        }
        return w1;
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int sizeX = xMax - xMin;
        int mouseOff = mouseX - xMin;
        double step = (this.maxGUI - this.minGUI) / (double)sizeX;
        double value = (double)Math.round(this.minGUI * 100.0 + (double)mouseOff * step * 100.0) / 100.0;
        if (this.fixedStep) {
            long valueI = Math.round(value * 100.0);
            long stepI = Math.round(this.step * 100.0);
            long mod = valueI % stepI;
            value = (double)(valueI - mod) / 100.0;
        }
        if (value > this.maxGUI) {
            value = this.maxGUI;
        }
        if (value < this.minGUI) {
            value = this.minGUI;
        }
        this.setValue(value);
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        int diff1 = xEnd - xStart;
        double diff2 = (this.maxGUI - this.minGUI) / (double)diff1;
        double val = this.value;
        if (val > this.maxGUI) {
            val = this.maxGUI;
        }
        if (val < this.minGUI) {
            val = this.minGUI;
        }
        int diff3 = (int)Math.round(val / diff2 - this.minGUI / diff2);
        tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int sizeX = xMax - xMin;
        int mouseOff = mouseX - xMin;
        double step = (this.maxGUI - this.minGUI) / (double)sizeX;
        double value = (double)Math.round(this.minGUI * 100.0 + (double)mouseOff * step * 100.0) / 100.0;
        if (this.fixedStep) {
            long valueI = Math.round(value * 100.0);
            long stepI = Math.round(this.step * 100.0);
            long mod = valueI % stepI;
            value = (double)(valueI - mod) / 100.0;
        }
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
    public void reset() {
        this.setValue(this.initialValue);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setDouble(this.name, this.value);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getDouble(this.name));
        }
    }
}

