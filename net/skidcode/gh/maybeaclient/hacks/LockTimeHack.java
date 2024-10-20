/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingLong;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class LockTimeHack
extends Hack {
    public SettingLong lockedTime = new SettingLong(this, "Time", 0L, 0L, 24000L);
    public static LockTimeHack INSTANCE;

    public LockTimeHack() {
        super("LockTime", "Lock time on specific value.", 0, Category.MISC);
        INSTANCE = this;
        this.addSetting(this.lockedTime);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.lockedTime.value;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }
}

