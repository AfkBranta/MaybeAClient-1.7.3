/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Packet28;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketReceive;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class AntiKnockbackHack
extends Hack
implements EventListener {
    public AntiKnockbackHack() {
        super("AntiKnockback", "Prevents player from being knocked back", 0, Category.COMBAT);
        EventRegistry.registerListener(EventPacketReceive.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPacketReceive) {
            EventPacketReceive ev = (EventPacketReceive)event;
            if (ev.packet instanceof Packet28) {
                event.cancelled = true;
            }
        }
    }
}

