/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class KeybindingsHack
extends Hack {
    public SettingMode alignment = new SettingMode(this, "Alignment", "Left", "Right");
    public SettingMode expand = new SettingMode(this, "Expand", "Bottom", "Top");
    public static KeybindingsHack instance;

    public KeybindingsHack() {
        super("Keybindings", "Show binds in hud", 0, Category.UI);
        this.addSetting(this.alignment);
        this.addSetting(this.expand);
        instance = this;
    }
}

