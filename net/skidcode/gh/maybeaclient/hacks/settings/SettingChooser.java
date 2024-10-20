/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import java.util.HashMap;
import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class SettingChooser
extends Setting {
    public HashMap<String, Boolean> value = new HashMap();
    public String[] choices;
    public boolean[] initial;
    public boolean minimized = false;
    public int lastPressed = -1;

    public SettingChooser(Hack hack, String name, String[] choices, boolean[] initial) {
        super(hack, name);
        if (choices.length != initial.length) {
            throw new RuntimeException("Lengths of choices and initial are different!");
        }
        this.initial = initial;
        this.choices = choices;
        this.setValue(initial);
    }

    public void setValue(boolean[] values) {
        int i = 0;
        while (i < this.choices.length) {
            this.setValue(this.choices[i], values[i]);
            ++i;
        }
    }

    public boolean getValue(String key) {
        return this.value.get(key.toLowerCase());
    }

    public void setValue(String name, boolean value) {
        this.value.put(name.toLowerCase(), value);
    }

    @Override
    public String valueToString() {
        String s = "";
        int i = 0;
        while (i < this.choices.length) {
            String m = this.choices[i];
            s = String.valueOf(s) + (Object)((Object)(this.getValue(m) ? ChatColor.LIGHTGREEN : ChatColor.LIGHTRED));
            s = String.valueOf(s) + m;
            s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
            s = String.valueOf(s) + ";";
            ++i;
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public String valueToStringConsole() {
        String s = "" + (Object)((Object)ChatColor.WHITE);
        int i = 0;
        while (i < this.choices.length) {
            String m = this.choices[i];
            s = String.valueOf(s) + (Object)((Object)(this.getValue(m) ? ChatColor.LIGHTGREEN : ChatColor.LIGHTRED));
            s = String.valueOf(s) + m;
            s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
            s = String.valueOf(s) + ";";
            ++i;
        }
        return s.substring(0, s.length() - 1);
    }

    @Override
    public void reset() {
        this.setValue(this.initial);
    }

    @Override
    public boolean validateValue(String value) {
        String[] splitted;
        String[] stringArray = splitted = value.split(";");
        int n = splitted.length;
        int n2 = 0;
        while (n2 < n) {
            String s = stringArray[n2];
            if (!this.value.containsKey(s.toLowerCase())) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        NBTTagCompound val = new NBTTagCompound();
        int i = 0;
        while (i < this.choices.length) {
            val.setBoolean(this.choices[i], this.value.get(this.choices[i].toLowerCase()));
            ++i;
        }
        val.setBoolean("Minimized", this.minimized);
        output.setCompoundTag(this.name, val);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        NBTTagCompound val = input.getCompoundTag(this.name);
        if (!val.tagMap.isEmpty()) {
            this.minimized = val.getBoolean("Minimized");
            int i = 0;
            while (i < this.choices.length) {
                this.setValue(this.choices[i], val.getBoolean(this.choices[i]));
                ++i;
            }
        }
    }

    @Override
    public int getSettingWidth() {
        int wid = super.getSettingWidth();
        int i = 0;
        while (i < this.choices.length) {
            int wid2 = Client.mc.fontRenderer.getStringWidth(this.choices[i]) + 4;
            if (wid2 > wid) {
                wid = wid2;
            }
            ++i;
        }
        return wid;
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        if (this.minimized) {
            return;
        }
        tab.renderFrameBackGround(xStart, yStart, xEnd, yStart + 10, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
        yStart += 11;
        int i = 0;
        while (i < this.choices.length) {
            if (this.getValue(this.choices[i])) {
                tab.renderFrameBackGround(xStart, yStart + 1, xEnd, yStart + 12 - 1, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
            }
            yStart += 12;
            ++i;
        }
    }

    @Override
    public void onPressedInside(int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        if (this.lastPressed != -1) {
            return;
        }
        int diff = mouseY - yMin;
        int md = diff / 12;
        if (md > 0) {
            this.setValue(this.choices[--md], !this.getValue(this.choices[md]));
        } else if (md == 0) {
            this.minimized = !this.minimized;
        } else {
            return;
        }
        this.lastPressed = md;
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        this.lastPressed = -1;
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(this.name, x + 2, y + 2, 0xFFFFFF);
        if (this.minimized) {
            return;
        }
        int rx = x + 2 + 4;
        int ry = y + 12;
        int i = 0;
        while (i < this.choices.length) {
            Client.mc.fontRenderer.drawString(this.choices[i], rx + 2, ry + i * 12 + 2, 0xFFFFFF);
            ++i;
        }
    }

    @Override
    public int getSettingHeight() {
        if (this.minimized) {
            return 12;
        }
        return 12 * this.choices.length + 12;
    }
}

