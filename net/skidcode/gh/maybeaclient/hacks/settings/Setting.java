/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBlockChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingColor;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingDouble;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingIgnoreList;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingInteger;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingKeybind;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingLong;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public abstract class Setting {
    public String name;
    public String noWhitespacesName;
    public boolean hidden = false;
    public Hack hack;

    public Setting(Hack hack, String name) {
        this.name = name;
        this.noWhitespacesName = name.replace(" ", "");
        this.hack = hack;
    }

    public abstract String valueToString();

    public abstract void reset();

    public abstract boolean validateValue(String var1);

    public String valueToStringConsole() {
        return this.valueToString();
    }

    public void setValue_(String value) {
        if (this instanceof SettingDouble) {
            ((SettingDouble)this).setValue(Double.parseDouble(value));
        } else if (this instanceof SettingMode) {
            ((SettingMode)this).setValue(value);
        } else if (this instanceof SettingFloat) {
            ((SettingFloat)this).setValue(Float.parseFloat(value));
        } else if (this instanceof SettingLong) {
            ((SettingLong)this).setValue(Long.parseLong(value));
        } else if (this instanceof SettingBoolean) {
            ((SettingBoolean)this).setValue(Boolean.parseBoolean(value));
        } else if (this instanceof SettingChooser) {
            String s;
            SettingChooser sc = (SettingChooser)this;
            String[] splitted = value.split(";");
            String[] stringArray = sc.choices;
            int n = sc.choices.length;
            int n2 = 0;
            while (n2 < n) {
                s = stringArray[n2];
                sc.setValue(s, false);
                ++n2;
            }
            stringArray = splitted;
            n = splitted.length;
            n2 = 0;
            while (n2 < n) {
                s = stringArray[n2];
                sc.setValue(s, true);
                ++n2;
            }
        } else if (this instanceof SettingColor) {
            String[] splitted = value.split(";");
            int r = Integer.parseInt(splitted[0]);
            int g = Integer.parseInt(splitted[1]);
            int b = Integer.parseInt(splitted[2]);
            ((SettingColor)this).setValue(r, g, b);
        } else if (this instanceof SettingKeybind) {
            ((SettingKeybind)this).setValue(Integer.parseInt(value));
        } else if (this instanceof SettingBlockChooser) {
            int i = Integer.parseInt(value);
            ((SettingBlockChooser)this).blocks[i] = !((SettingBlockChooser)this).blocks[i];
            ((SettingBlockChooser)this).blockChanged(i);
        } else if (this instanceof SettingIgnoreList) {
            ((SettingIgnoreList)this).setValue(value);
        } else if (this instanceof SettingInteger) {
            int i = Integer.parseInt(value);
            ((SettingInteger)this).setValue(i);
        } else {
            throw new RuntimeException("Tried setting value " + value + " for " + this);
        }
    }

    public void hide() {
        this.hidden = true;
        ++this.hack.hiddens;
    }

    public void show() {
        this.hidden = false;
        --this.hack.hiddens;
    }

    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(this.name, x + 2, y + 2, 0xFFFFFF);
    }

    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
    }

    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
    }

    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
    }

    public void onMouseMoved(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
    }

    public int getSettingWidth() {
        return Client.mc.fontRenderer.getStringWidth(this.name) + 10;
    }

    public int getSettingHeight() {
        return 12;
    }

    public abstract void writeToNBT(NBTTagCompound var1);

    public abstract void readFromNBT(NBTTagCompound var1);
}

