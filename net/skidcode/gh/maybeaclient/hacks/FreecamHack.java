/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventMPMovementUpdate;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketSend;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.hacks.FlyHack;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingDouble;

public class FreecamHack
extends Hack
implements EventListener {
    public SettingDouble speedMultiplier = new SettingDouble(this, "Speed Multiplier", 1.0, 0.1, 2.0);
    public SettingBoolean noclip = new SettingBoolean(this, "NoClip", true);
    public SettingBoolean resetPositonOnDisable = new SettingBoolean(this, "ResetPositionOnDisable", true);
    public double posX;
    public double posY;
    public double posZ;
    public static boolean hasXYZ = false;
    public static FreecamHack instance;

    public FreecamHack() {
        super("Freecam", "Allows players to fly around the world", 34, Category.MISC);
        instance = this;
        this.addSetting(this.speedMultiplier);
        this.addSetting(this.noclip);
        this.addSetting(this.resetPositonOnDisable);
        EventRegistry.registerListener(EventPacketSend.class, this);
        EventRegistry.registerListener(EventMPMovementUpdate.class, this);
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    @Override
    public void onEnable() {
        this.posX = FreecamHack.mc.thePlayer.posX;
        this.posY = FreecamHack.mc.thePlayer.posY;
        this.posZ = FreecamHack.mc.thePlayer.posZ;
    }

    @Override
    public void onDisable() {
        if (this.resetPositonOnDisable.getValue()) {
            FreecamHack.mc.thePlayer.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    public void handleEvent(Event event) {
        if (event instanceof EventMPMovementUpdate) {
            mc.getSendQueue().addToSendQueue(new Packet0KeepAlive());
        } else if (event instanceof EventPacketSend) {
            EventPacketSend ev = (EventPacketSend)event;
            if (ev.packet instanceof Packet10Flying) {
                if (!hasXYZ) {
                    this.posX = FreecamHack.mc.thePlayer.posX;
                    this.posY = FreecamHack.mc.thePlayer.posY;
                    this.posZ = FreecamHack.mc.thePlayer.posZ;
                    hasXYZ = true;
                } else {
                    ev.cancelled = true;
                }
            }
        } else if (event instanceof EventPlayerUpdatePost) {
            FlyHack.handleFly(this.speedMultiplier.value);
        }
    }
}

