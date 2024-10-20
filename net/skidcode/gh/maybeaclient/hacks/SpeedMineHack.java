/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class SpeedMineHack
extends Hack {
    public SettingFloat sendDestroyAfter = new SettingFloat(this, "Destroy After", 0.7f, 0.0f, 1.0f);
    public static SpeedMineHack instance;

    public SpeedMineHack() {
        super("SpeedMine", "Allows to mine faster", 0, Category.MISC);
        instance = this;
        this.addSetting(this.sendDestroyAfter);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.sendDestroyAfter.value;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }
}

