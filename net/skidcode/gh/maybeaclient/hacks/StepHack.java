/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class StepHack
extends Hack
implements EventListener {
    public static StepHack instance;
    public SettingFloat stepHeight = new SettingFloat(this, "StepHeight", 1.0f, 0.0f, 5.0f, 0.5f);

    public StepHack() {
        super("Step", "Automatically step up to multiple blocks", 0, Category.MOVEMENT);
        this.addSetting(this.stepHeight);
        instance = this;
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    @Override
    public void onDisable() {
        StepHack.mc.thePlayer.stepHeight = 0.5f;
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + String.format("%.2f", Float.valueOf(this.stepHeight.value));
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePost) {
            StepHack.mc.thePlayer.stepHeight = this.stepHeight.value;
        }
    }
}

