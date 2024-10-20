/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.events;

import net.skidcode.gh.maybeaclient.events.Event;

public interface EventListener<T extends Event> {
    public void handleEvent(T var1);
}

