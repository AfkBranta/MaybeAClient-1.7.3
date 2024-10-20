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
import net.skidcode.gh.maybeaclient.hacks.FlyHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.InventoryWalkHack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import org.lwjgl.input.Keyboard;

public class StrafeHack
extends Hack
implements EventListener {
    public static StrafeHack instance;

    public StrafeHack() {
        super("Strafe", "Allows to instantly switch directions", 0, Category.MOVEMENT);
        instance = this;
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePost && !FlyHack.instance.status) {
            float yaw = StrafeHack.mc.thePlayer.rotationYaw;
            StrafeHack.mc.thePlayer.motionZ = 0.0;
            StrafeHack.mc.thePlayer.motionX = 0.0;
            if (!(StrafeHack.mc.inGameHasFocus || InventoryWalkHack.instance.status && StrafeHack.mc.currentScreen instanceof GuiContainer)) {
                return;
            }
            boolean fw = Keyboard.isKeyDown((int)StrafeHack.mc.gameSettings.keyBindForward.keyCode);
            boolean bw = Keyboard.isKeyDown((int)StrafeHack.mc.gameSettings.keyBindBack.keyCode);
            boolean lw = Keyboard.isKeyDown((int)StrafeHack.mc.gameSettings.keyBindLeft.keyCode);
            boolean rw = Keyboard.isKeyDown((int)StrafeHack.mc.gameSettings.keyBindRight.keyCode);
            if (AutoTunnelHack.instance.status && AutoTunnelHack.instance.autoWalk.value) {
                fw = true;
                yaw = AutoTunnelHack.instance.getDirection().yaw;
            }
            float d1 = yaw + 90.0f;
            if (AutoWalkHack.instance.status) {
                fw = true;
            }
            if (fw) {
                if (lw) {
                    d1 -= 45.0f;
                } else if (rw) {
                    d1 += 45.0f;
                }
            } else if (bw) {
                d1 = (float)((double)d1 + 180.0);
                if (lw) {
                    d1 = (float)((double)d1 + 45.0);
                } else if (rw) {
                    d1 = (float)((double)d1 - 45.0);
                }
            } else if (lw) {
                d1 = (float)((double)d1 - 90.0);
            } else if (rw) {
                d1 = (float)((double)d1 + 90.0);
            }
            if (fw || bw || lw || rw) {
                StrafeHack.mc.thePlayer.motionX = Math.cos(Math.toRadians(d1)) * 0.2;
                StrafeHack.mc.thePlayer.motionZ = Math.sin(Math.toRadians(d1)) * 0.2;
            }
        }
    }
}

