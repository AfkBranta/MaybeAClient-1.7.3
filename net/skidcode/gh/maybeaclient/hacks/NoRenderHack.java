/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;

public class NoRenderHack
extends Hack {
    public static NoRenderHack instance;
    public SettingBoolean signText = new SettingBoolean(this, "SignText", false);
    public SettingBoolean itemEntities = new SettingBoolean(this, "ItemEntities", false);
    public SettingBoolean clouds = new SettingBoolean(this, "Clouds", false);
    public SettingBoolean fog = new SettingBoolean(this, "Fog", false);
    public SettingBoolean boats = new SettingBoolean(this, "Boats", false);
    public SettingBoolean waterAnim = new SettingBoolean(this, "Water Animation", false);
    public SettingBoolean lavaAnim = new SettingBoolean(this, "Lava Animation", false);

    public NoRenderHack() {
        super("NoRender", "Disables some rendering", 0, Category.RENDER);
        instance = this;
        this.addSetting(this.signText);
        this.addSetting(this.itemEntities);
        this.addSetting(this.clouds);
        this.addSetting(this.fog);
        this.addSetting(this.boats);
        this.addSetting(this.waterAnim);
        this.addSetting(this.lavaAnim);
    }
}

