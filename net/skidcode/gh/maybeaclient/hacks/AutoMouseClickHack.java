/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingInteger;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class AutoMouseClickHack
extends Hack {
    public SettingMode left;
    public SettingMode right;
    public SettingInteger lDelayTicks;
    public SettingInteger rDelayTicks;
    public int ldelay;
    public int rdelay;
    public static AutoMouseClickHack instance;

    public AutoMouseClickHack() {
        super("AutoMouseClick", "Automatically clicks mouse button", 0, Category.MISC);
        instance = this;
        this.lDelayTicks = new SettingInteger(this, "Left Click Delay", 0, 2, 20);
        this.rDelayTicks = new SettingInteger(this, "Right Click Delay", 0, 2, 20);
        this.left = new SettingMode(this, "Left", new String[]{"Disabled", "Hold", "Click"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                AutoMouseClickHack.instance.lDelayTicks.hidden = !this.currentMode.equalsIgnoreCase("Click");
            }

            @Override
            public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
                if (!this.currentMode.equalsIgnoreCase("Disabled")) {
                    super.renderElement(tab, xStart, yStart, xEnd, yEnd);
                }
            }
        };
        this.right = new SettingMode(this, "Right", new String[]{"Disabled", "Hold", "Click"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                AutoMouseClickHack.instance.rDelayTicks.hidden = !this.currentMode.equalsIgnoreCase("Click");
            }

            @Override
            public void renderElement(Tab tab, int xStart, int yStart, int xEnd, int yEnd) {
                if (!this.currentMode.equalsIgnoreCase("Disabled")) {
                    super.renderElement(tab, xStart, yStart, xEnd, yEnd);
                }
            }
        };
        this.addSetting(this.left);
        this.addSetting(this.lDelayTicks);
        this.addSetting(this.right);
        this.addSetting(this.rDelayTicks);
    }
}

