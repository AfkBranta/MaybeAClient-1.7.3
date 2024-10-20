/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public strictfp class SettingFloat
extends Setting {
    public float value;
    public float initialValue;
    public float minGUI;
    public float maxGUI;
    public int additionalWidth = 0;
    public boolean fixedStep = false;
    public float step = 0.0f;

    public SettingFloat(Hack hack, String name, float initialValue, float minGUI, float maxGUI) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
        this.minGUI = minGUI;
        this.maxGUI = maxGUI;
    }

    public SettingFloat(Hack hack, String name, float initialValue, float minGUI, float maxGUI, float step) {
        this(hack, name, initialValue, minGUI, maxGUI);
        this.fixedStep = true;
        this.step = step;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return "" + this.value;
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Float.parseFloat(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int sizeX = xMax - xMin;
        int mouseOff = mouseX - xMin;
        float step = (this.maxGUI - this.minGUI) / (float)sizeX;
        float value = (float)Math.round(this.minGUI * 100.0f + (float)mouseOff * step * 100.0f) / 100.0f;
        if (this.fixedStep) {
            int valueI = Math.round(value * 100.0f);
            int stepI = Math.round(this.step * 100.0f);
            int mod = valueI % stepI;
            value = (float)(valueI - mod) / 100.0f;
        }
        if (value > this.maxGUI) {
            value = this.maxGUI;
        }
        if (value < this.minGUI) {
            value = this.minGUI;
        }
        int v = (int)(value * 100.0f);
        float f = (float)((double)v / 100.0);
        this.setValue(value);
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        int diff1 = xEnd - xStart;
        float step = (this.maxGUI - this.minGUI) / (float)diff1;
        float val = this.value;
        if (val > this.maxGUI) {
            val = this.maxGUI;
        }
        if (val < this.minGUI) {
            val = this.minGUI;
        }
        int diff3 = Math.round(val / step - this.minGUI / step);
        tab.renderFrameBackGround(xStart, yStart, xStart + diff3, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        int sizeX = xMax - xMin;
        int mouseOff = mouseX - xMin;
        float step = (this.maxGUI - this.minGUI) / (float)sizeX;
        float value = (float)Math.round(this.minGUI * 100.0f + (float)mouseOff * step * 100.0f) / 100.0f;
        if (this.fixedStep) {
            int valueI = Math.round(value * 100.0f);
            int stepI = Math.round(this.step * 100.0f);
            int mod = valueI % stepI;
            value = (float)(valueI - mod) / 100.0f;
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
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(String.valueOf(this.name) + " - " + String.format("%.2f", Float.valueOf(this.getValue())), x + 2, y + 2, 0xFFFFFF);
    }

    @Override
    public int getSettingWidth() {
        int w1 = Client.mc.fontRenderer.getStringWidth(String.valueOf(this.name) + " - " + String.format("%.2f", Float.valueOf(this.getValue()))) + 5;
        if (this.fixedStep) {
            float w2 = (this.maxGUI - this.minGUI) / this.step;
            if ((float)w1 > w2) {
                return w1;
            }
            return (int)Math.floor(w2);
        }
        return w1;
    }

    @Override
    public void reset() {
        this.setValue(this.initialValue);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setFloat(this.name, this.value);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getFloat(this.name));
        }
    }
}

