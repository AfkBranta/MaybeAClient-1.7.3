/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.events.impl;

import net.skidcode.gh.maybeaclient.events.Event;

public class EventPlayerUpdatePost
extends Event {
    protected static int id = ++Event.id;

    @Override
    public int getID() {
        return id;
    }
}

