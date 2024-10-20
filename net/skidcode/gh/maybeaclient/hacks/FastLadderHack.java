package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;

public class FastLadderHack
extends Hack {
    public static FastLadderHack instance;
    public SettingFloat upwardSpeed = new SettingFloat(this, "Upward speed", 1.0f, 0.2f, 2.0f, 0.05f);
    public SettingFloat downwardSpeed = new SettingFloat(this, "Downward speed", 1.0f, 0.15f, 2.0f, 0.05f);

    public FastLadderHack() {
        super("FastLadder", "Allows to quiclky climb ladder", 0, Category.MOVEMENT);
        instance = this;
        this.addSetting(this.upwardSpeed);
        this.addSetting(this.downwardSpeed);
    }
}

