/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventRenderIngameNoDebug;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class ServerStatusHack
extends Hack
implements EventListener {
    public ServerStatusHack() {
        super("ServerStatus", "Shows status of the server", 0, Category.UI);
        EventRegistry.registerListener(EventRenderIngameNoDebug.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventRenderIngameNoDebug) {
            EventRenderIngameNoDebug ev = (EventRenderIngameNoDebug)event;
            if (mc.getSendQueue() != null && ServerStatusHack.mc.getSendQueue().netManager.timeSinceLastRead >= 20) {
                double time = (double)ServerStatusHack.mc.getSendQueue().netManager.timeSinceLastRead / 20.0;
                String s = (Object)((Object)ChatColor.WHITE) + "Server is frozen for " + (Object)((Object)ChatColor.LIGHTCYAN) + String.format("%.2f", time) + (Object)((Object)ChatColor.WHITE) + " seconds";
                ServerStatusHack.mc.fontRenderer.drawStringWithShadow(s, ev.resolution.getScaledWidth() / 2 - ServerStatusHack.mc.fontRenderer.getStringWidth(s) / 2, ev.resolution.getScaledHeight() / 2 - 12, -559030611);
            }
        }
    }
}

