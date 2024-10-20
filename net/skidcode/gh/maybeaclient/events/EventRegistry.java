/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.src.MCHashTable;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import sun.misc.Unsafe;

public class EventRegistry {
    public static MCHashTable listeners = new MCHashTable();
    public static Unsafe SAFE;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            SAFE = (Unsafe)f.get(null);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get Unsafe", e);
        }
    }

    public static void registerListener(Class<? extends Event> class1, EventListener<? extends Event> listener) {
        Event e;
        try {
            e = (Event)SAFE.allocateInstance(class1);
        }
        catch (Exception ee) {
            throw new RuntimeException(ee);
        }
        if (listeners.lookup(e.getID()) == null) {
            listeners.addKey(e.getID(), new ArrayList());
        }
        ((ArrayList)listeners.lookup(e.getID())).add(listener);
    }

    public static void unregisterListener(Class<? extends Event> class1, EventListener<? extends Event> listener) {
        Event e;
        try {
            e = (Event)SAFE.allocateInstance(class1);
        }
        catch (Exception ee) {
            throw new RuntimeException(ee);
        }
        if (listeners.lookup(e.getID()) != null) {
            ((ArrayList)listeners.lookup(e.getID())).remove(listener);
        }
    }

    public static void handleEvent(Event event) {
        ArrayList arr = (ArrayList)listeners.lookup(event.getID());
        if (arr != null) {
            int index = 0;
            do {
                EventListener el;
                if ((el = (EventListener)arr.get(index++)) instanceof Hack && !((Hack)((Object)el)).status) continue;
                el.handleEvent(event);
            } while (index < arr.size());
        }
    }
}

