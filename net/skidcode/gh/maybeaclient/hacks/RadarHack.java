/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class RadarHack
extends Hack {
    public SettingBoolean showXYZ = new SettingBoolean(this, "Show XYZ", false);
    public SettingMode alignment = new SettingMode(this, "Alignment", "Left", "Right");
    public SettingMode expand = new SettingMode(this, "Expand", "Bottom", "Top");
    public SettingMode staticPositon;
    public static RadarHack instance;

    public RadarHack() {
        super("Radar", "Shows players nearby", 15, Category.UI);
        instance = this;
        this.addSetting(this.showXYZ);
        this.staticPositon = new SettingMode(this, "Static Position", new String[]{"Top Right", "Bottom Right", "Top Left", "Bottom Left", "Disabled"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                if (this.currentMode.equalsIgnoreCase("Disabled")) {
                    RadarHack.instance.alignment.show();
                    RadarHack.instance.expand.show();
                } else {
                    RadarHack.instance.alignment.hide();
                    RadarHack.instance.expand.hide();
                }
            }
        };
        this.addSetting(this.staticPositon);
        this.addSetting(this.alignment);
        this.addSetting(this.expand);
    }
}

