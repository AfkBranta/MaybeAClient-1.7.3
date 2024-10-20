/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.skidcode.gh.maybeaclient.hacks.settings;

import net.minecraft.src.NBTTagCompound;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.settings.Setting;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import net.skidcode.gh.maybeaclient.utils.InputHandler;
import org.lwjgl.input.Keyboard;

public class SettingKeybind
extends Setting
implements InputHandler {
    public int value;
    public int initialValue;
    boolean activated = false;
    boolean listening = false;

    public SettingKeybind(Hack hack, String name, int initialValue) {
        super(hack, name);
        this.setValue(initialValue);
        this.initialValue = initialValue;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int d) {
        this.value = d;
    }

    @Override
    public String valueToString() {
        return Keyboard.getKeyName((int)this.value);
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
    public int getSettingWidth() {
        return Client.mc.fontRenderer.getStringWidth(String.valueOf(this.name) + ": " + (this.listening ? (Object)((Object)ChatColor.LIGHTGRAY) + "Listening..." : this.valueToString())) + 10;
    }

    @Override
    public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
        ClickGUI.inputHandler = this;
        this.listening = true;
    }

    @Override
    public void onKeyPress(int keycode) {
        this.value = keycode == 1 || keycode == 0 ? 0 : keycode;
        this.listening = false;
        ClickGUI.inputHandler = null;
        Client.saveModules();
    }

    @Override
    public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
        tab.renderFrameBackGround(xStart, yStart, xEnd, yEnd, 0.0f, 0.6666667f, 0.6666667f, 1.0f);
    }

    @Override
    public void writeToNBT(NBTTagCompound output) {
        output.setInteger(this.name, this.value);
    }

    @Override
    public void renderText(int x, int y) {
        Client.mc.fontRenderer.drawString(String.valueOf(this.name) + ": " + (this.listening ? (Object)((Object)ChatColor.LIGHTGRAY) + "Listening..." : this.valueToString()), x + 2, y + 2, 0xFFFFFF);
    }

    @Override
    public void readFromNBT(NBTTagCompound input) {
        if (input.hasKey(this.name)) {
            this.setValue(input.getInteger(this.name));
        }
    }
}

