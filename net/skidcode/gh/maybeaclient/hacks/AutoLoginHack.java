/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Packet3Chat;
import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.events.Event;
import net.skidcode.gh.maybeaclient.events.EventListener;
import net.skidcode.gh.maybeaclient.events.EventRegistry;
import net.skidcode.gh.maybeaclient.events.impl.EventPacketReceive;
import net.skidcode.gh.maybeaclient.gui.altman.AccountInfo;
import net.skidcode.gh.maybeaclient.gui.altman.GuiAccManager;
import net.skidcode.gh.maybeaclient.gui.altman.PasswordInfo;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class AutoLoginHack
extends Hack
implements EventListener {
    public AutoLoginHack() {
        super("AutoLogin", "Automatically enters password", 0, Category.MISC);
        EventRegistry.registerListener(EventPacketReceive.class, this);
    }

    public void handleEvent(Event event) {
        if (event instanceof EventPacketReceive && ((EventPacketReceive)event).packet instanceof Packet3Chat) {
            Packet3Chat pk = (Packet3Chat)((EventPacketReceive)event).packet;
            AccountInfo accinfo = GuiAccManager.usernames.get(Client.mc.session.username);
            if (accinfo != null) {
                PasswordInfo serv = accinfo.passwords.get(String.valueOf(AutoLoginHack.mc.getSendQueue().ip) + ":" + AutoLoginHack.mc.getSendQueue().port);
                if (serv == null && AutoLoginHack.mc.getSendQueue().port == 25565) {
                    serv = accinfo.passwords.get(AutoLoginHack.mc.getSendQueue().ip);
                }
                if (serv != null) {
                    boolean enterPassword = false;
                    switch (serv.mode) {
                        case EXACT: {
                            enterPassword = pk.message.equals(serv.loginPrompt.replace("&", "\u00a7"));
                            break;
                        }
                        case STARTSWITH: {
                            enterPassword = pk.message.startsWith(serv.loginPrompt.replace("&", "\u00a7"));
                            break;
                        }
                    }
                    if (enterPassword) {
                        Client.addMessage("Detected password request.");
                        AutoLoginHack.mc.thePlayer.sendChatMessage(serv.loginCommand.replace("%password%", serv.password));
                    }
                }
            }
        }
    }
}

