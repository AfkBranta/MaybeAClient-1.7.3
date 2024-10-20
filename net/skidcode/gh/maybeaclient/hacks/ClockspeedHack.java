/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class ClockspeedHack
extends Hack {
    public SettingFloat speed = new SettingFloat(this, "Speed", 1.5f, 0.1f, 5.0f, 0.05f);
    public static ClockspeedHack instance;

    public ClockspeedHack() {
        super("ClockSpeed", "Increase tick speed", 64, Category.MISC);
        instance = this;
        this.addSetting(this.speed);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.speed.value;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }
}

