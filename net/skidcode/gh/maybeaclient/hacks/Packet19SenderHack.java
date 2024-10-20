/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Packet19;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPlayerUpdatePost;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;

public class Packet19SenderHack
extends Hack
implements EventListener {
    public SettingFloat delayS = new SettingFloat(this, "DelaySeconds", 0.0f, 0.0f, 2.0f, 0.05f);
    public long nextSend = 0L;

    public Packet19SenderHack() {
        super("Packet19Sender", "Sends packet19", 0, Category.MISC);
        this.addSetting(this.delayS);
        EventRegistry.registerListener(EventPlayerUpdatePost.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPlayerUpdatePost && mc.isMultiplayerWorld()) {
            long l = System.currentTimeMillis();
            long addr = (long)(this.delayS.value * 1000.0f);
            if (l > this.nextSend) {
                mc.getSendQueue().addToSendQueue(new Packet19(Packet19SenderHack.mc.thePlayer, 3));
                this.nextSend = l + addr;
            }
        }
    }
}

