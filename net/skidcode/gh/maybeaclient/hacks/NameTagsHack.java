/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;

public class NameTagsHack
extends Hack {
    public SettingFloat scale = new SettingFloat(this, "Scale", 1.0f, 1.0f, 5.0f, 0.1f);
    public static NameTagsHack instance;

    public NameTagsHack() {
        super("NameTags", "Always show nametags", 0, Category.RENDER);
        instance = this;
        this.addSetting(this.scale);
    }
}

