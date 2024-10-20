/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.GuiContainer;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.hacks.AutoTunnelHack;
import net.skidcode.gh.maybeaclient.hacks.AutoWalkHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.InventoryWalkHack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingDouble;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import org.lwjgl.input.Keyboard;

public class FlyHack
extends Hack
implements EventListener {
    public SettingDouble speedMultiplier = new SettingDouble(this, "Speed Multiplier", 0.2, 0.1, 2.0, 0.05);
    public static FlyHack instance;

    public FlyHack() {
        super("Fly", "FlyHack ^-^", 19, Category.MOVEMENT);
        instance = this;
        this.addSetting(this.speedMultiplier);
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.speedMultiplier.value;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    public static void handleFly(double speedMultiplier) {
        FlyHack.mc.thePlayer.onGround = false;
        FlyHack.mc.thePlayer.motionX = 0.0;
        FlyHack.mc.thePlayer.motionY = 0.0;
        FlyHack.mc.thePlayer.motionZ = 0.0;
        if (!(FlyHack.mc.inGameHasFocus || InventoryWalkHack.instance.status && FlyHack.mc.currentScreen instanceof GuiContainer)) {
            return;
        }
        double d = FlyHack.mc.thePlayer.rotationPitch + 90.0f;
        double d1 = FlyHack.mc.thePlayer.rotationYaw + 90.0f;
        boolean flag = Keyboard.isKeyDown((int)FlyHack.mc.gameSettings.keyBindForward.keyCode);
        boolean flag1 = Keyboard.isKeyDown((int)FlyHack.mc.gameSettings.keyBindBack.keyCode);
        boolean flag2 = Keyboard.isKeyDown((int)FlyHack.mc.gameSettings.keyBindLeft.keyCode);
        boolean flag3 = Keyboard.isKeyDown((int)FlyHack.mc.gameSettings.keyBindRight.keyCode);
        if (AutoTunnelHack.instance.status && AutoTunnelHack.instance.autoWalk.value) {
            flag = true;
            d1 = AutoTunnelHack.instance.getDirection().yaw + 90.0f;
        }
        if (AutoWalkHack.instance.status) {
            flag = true;
        }
        if (flag) {
            if (flag2) {
                d1 -= 45.0;
            } else if (flag3) {
                d1 += 45.0;
            }
        } else if (flag1) {
            d1 += 180.0;
            if (flag2) {
                d1 += 45.0;
            } else if (flag3) {
                d1 -= 45.0;
            }
        } else if (flag2) {
            d1 -= 90.0;
        } else if (flag3) {
            d1 += 90.0;
        }
        if (flag || flag2 || flag1 || flag3) {
            FlyHack.mc.thePlayer.motionX = Math.cos(Math.toRadians(d1));
            FlyHack.mc.thePlayer.motionZ = Math.sin(Math.toRadians(d1));
        }
        if (Keyboard.isKeyDown((int)57)) {
            FlyHack.mc.thePlayer.motionY = FlyHack.mc.thePlayer.motionY + 1.0;
        } else if (Keyboard.isKeyDown((int)42)) {
            FlyHack.mc.thePlayer.motionY = FlyHack.mc.thePlayer.motionY - 1.0;
        }
        FlyHack.mc.thePlayer.motionX *= speedMultiplier;
        FlyHack.mc.thePlayer.motionY *= speedMultiplier;
        FlyHack.mc.thePlayer.motionZ *= speedMultiplier;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePost) {
            FlyHack.handleFly(this.speedMultiplier.value);
        }
    }
}

