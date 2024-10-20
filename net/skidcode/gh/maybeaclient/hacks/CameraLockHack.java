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
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class CameraLockHack
extends Hack
implements EventListener {
    public static CameraLockHack instance;
    public SettingBoolean lockYaw = new SettingBoolean(this, "LockYaw", true);
    public SettingBoolean lockPitch = new SettingBoolean(this, "LockPitch", false);
    public SettingFloat yaw = new SettingFloat(this, "Yaw", 0.0f, 0.0f, 359.0f, 5.0f);
    public SettingFloat pitch = new SettingFloat(this, "Pitch", 0.0f, -90.0f, 90.0f, 5.0f);

    public CameraLockHack() {
        super("CameraLock", "Allows players to lock yaw and pitch of the camera", 0, Category.MISC);
        instance = this;
        this.addSetting(this.lockYaw);
        this.addSetting(this.lockPitch);
        this.addSetting(this.yaw);
        this.addSetting(this.pitch);
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        if (this.lockYaw.value) {
            s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
            s = String.valueOf(s) + this.yaw.value;
            s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
            if (this.lockPitch.value) {
                s = String.valueOf(s) + ";";
            }
        }
        if (this.lockPitch.value) {
            s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
            s = String.valueOf(s) + this.pitch.value;
            s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        }
        if ((s = String.valueOf(s) + "]").equals("[]")) {
            return this.name;
        }
        return String.valueOf(this.name) + s;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePost) {
            if (this.lockYaw.value) {
                CameraLockHack.mc.thePlayer.rotationYaw = CameraLockHack.mc.thePlayer.prevRotationYaw = this.yaw.value % 360.0f;
            }
            if (this.lockPitch.value) {
                CameraLockHack.mc.thePlayer.rotationPitch = CameraLockHack.mc.thePlayer.prevRotationPitch = this.pitch.value % 91.0f;
            }
        }
    }
}

