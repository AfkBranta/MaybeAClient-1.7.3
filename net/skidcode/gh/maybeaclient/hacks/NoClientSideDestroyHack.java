/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;

public class NoClientSideDestroyHack
extends Hack {
    public static NoClientSideDestroyHack instance;
    public SettingBoolean noDestroy = new SettingBoolean(this, "No Destroy", true);
    public SettingBoolean noTNT = new SettingBoolean(this, "No TNT", false);

    public NoClientSideDestroyHack() {
        super("NoClientSideActions", "Disables cerain actions on client side", 0, Category.MISC);
        instance = this;
        this.addSetting(this.noDestroy);
        this.addSetting(this.noTNT);
    }
}

