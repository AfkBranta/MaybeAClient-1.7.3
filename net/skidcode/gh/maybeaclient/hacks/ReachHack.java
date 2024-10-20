/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class ReachHack
extends Hack {
    public SettingFloat radius = new SettingFloat(this, "Radius", 6.0f, 4.0f, 10.0f, 0.5f);
    public static ReachHack instance;

    public ReachHack() {
        super("Reach", "Increases allowed radius to interact with blocks", 0, Category.MISC);
        this.addSetting(this.radius);
        instance = this;
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.radius.value;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }
}

