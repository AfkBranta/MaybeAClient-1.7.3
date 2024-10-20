/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;

public class PlayerViewHack
extends Hack {
    public static PlayerViewHack instance;
    public SettingBoolean dontRotate = new SettingBoolean(this, "Disable Roatation", true);

    public PlayerViewHack() {
        super("PlayerView", "Enables player view ingame", 0, Category.UI);
        instance = this;
        this.addSetting(this.dontRotate);
    }
}

