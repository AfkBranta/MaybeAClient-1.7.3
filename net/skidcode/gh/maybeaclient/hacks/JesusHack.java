/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class JesusHack
extends Hack {
    public SettingMode mode;
    public static JesusHack INSTANCE;

    public JesusHack() {
        super("Jesus", "Allows to walk on water", 36, Category.MOVEMENT);
        INSTANCE = this;
        this.mode = new SettingMode(this, "Mode", new String[]{"Normal+", "Normal", "Jump"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
            }
        };
        this.addSetting(this.mode);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.mode.currentMode;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }
}

