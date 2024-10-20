/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Packet10Flying;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketSend;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePre;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class NoFallHack
extends Hack
implements EventListener {
    public SettingMode mode = new SettingMode(this, "Mode", "Normal", "Test");

    public NoFallHack() {
        super("NoFall", "Removes fall damage", 0, Category.MISC);
        this.addSetting(this.mode);
        EventRegistry.registerListener(EventPacketSend.class, this);
        EventRegistry.registerListener(EventPlayerUpdatePre.class, this);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.mode.currentMode;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPacketSend) {
            EventPacketSend ev = (EventPacketSend)event;
            if (this.mode.currentMode.equalsIgnoreCase("Normal")) {
                if (ev.packet instanceof Packet10Flying) {
                    ((Packet10Flying)ev.packet).onGround = true;
                }
            } else if (this.mode.currentMode.equalsIgnoreCase("Test") && ev.packet instanceof Packet10Flying) {
                ((Packet10Flying)ev.packet).onGround = false;
            }
        } else if (event instanceof EventPlayerUpdatePre) {
            if (!mc.isMultiplayerWorld()) {
                NoFallHack.mc.thePlayer.fallDistance = 0.0f;
            }
            if (this.mode.currentMode.equalsIgnoreCase("Test") && mc.isMultiplayerWorld() && NoFallHack.mc.thePlayer.fallDistance > 2.0f) {
                mc.getSendQueue().addToSendQueue(new Packet10Flying(false));
            }
        }
    }
}

