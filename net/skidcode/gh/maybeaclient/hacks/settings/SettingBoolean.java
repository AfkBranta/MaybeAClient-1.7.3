/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;

public class SettingBoolean
extends Setting {
    public boolean value;
    public boolean initialValue;

    public SettingBoolean(Hack hack, String name, boolean initialValue) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return "" + this.value;
    }

    @Override
    public boolean validateValue(String value) {
        try {
            Boolean.parseBoolean(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        if (this.value) {
            tab.renderFrameBackGround(xStart, yStart, xEnd, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        }
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        this.setValue(!this.value);
    }

    @Override
    public void reset() {
        this.setValue(this.initialValue);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setBoolean(this.name, this.value);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getBoolean(this.name));
        }
    }
}

