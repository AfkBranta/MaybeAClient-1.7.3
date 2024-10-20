/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingColor;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;

public class TracersHack
extends Hack {
    public SettingColor color = new SettingColor(this, "Line Color", 255, 0, 0);
    public SettingFloat width = new SettingFloat(this, "Line Width", 1.5f, 0.1f, 5.0f, 0.1f);
    public static TracersHack instance;

    public TracersHack() {
        super("Tracers", "Shows lines to players", 0, Category.RENDER);
        instance = this;
        this.addSetting(this.color);
        this.addSetting(this.width);
    }
}

