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

public class SettingMode
extends Setting {
    public HashMap<String, String> modes = new HashMap();
    public HashMap<String, Integer> mode2pos = new HashMap();
    public HashMap<Integer, String> pos2mode = new HashMap();
    public String defaultMode;
    public String currentMode;

    public SettingMode(Hack hack, String name, String ... modes) {
        super(hack, name);
        int i = 0;
        while (i < modes.length) {
            this.mode2pos.put(modes[i].toLowerCase(), i);
            this.pos2mode.put(i, modes[i].toLowerCase());
            this.modes.put(modes[i].toLowerCase(), modes[i]);
            ++i;
        }
        this.setValue(modes[0]);
        this.defaultMode = modes[0];
    }

    @Override
    public String valueToString() {
        return this.currentMode;
    }

    @Override
    public void reset() {
        this.setValue(this.defaultMode);
    }

    @Override
    public String valueToStringConsole() {
        String s = "";
        for (String mod : this.modes.values()) {
            s = String.valueOf(s) + (Object)((Object)(this.currentMode.equalsIgnoreCase(mod) ? ChatColor.LIGHTGREEN : ChatColor.LIGHTRED)) + mod + ", ";
        }
        return s.substring(0, s.length() - 2);
    }

    @Override
    public boolean validateValue(String value) {
        return this.modes.containsKey(value.toLowerCase());
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setString(this.name, this.currentMode == null ? this.defaultMode : this.currentMode);
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(String.valueOf(this.name) + " - " + this.currentMode, x + 2, y + 2, 0xFFFFFF);
    }

    @Override
    public int getSettingWidth() {
        return Client.mc.fontRenderer.getStringWidth(String.valueOf(this.name) + " - " + this.currentMode) + 5;
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        boolean set = false;
        int i = this.mode2pos.get(this.currentMode.toLowerCase()) + 1;
        if (this.pos2mode.get(i) == null) {
            i = 0;
        }
        this.setValue(this.modes.get(this.pos2mode.get(i)));
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        tab.renderFrameBackGround(xStart, yStart, xEnd, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getString(this.name));
        }
    }

    public void setValue(String value) {
        this.currentMode = this.modes.get(value.toLowerCase());
    }
}

