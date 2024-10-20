
package net.skidcode.gh.maybeaclient.events;

public abstract class Event {
    public static int id = 0;
    public boolean cancelled = false;

    public int getID() {
        return id;
    }

    public boolean equals(Object e) {
        return e instanceof Event && ((Event)e).getID() == this.getID();
    }
}

