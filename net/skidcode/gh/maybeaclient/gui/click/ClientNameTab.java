/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.gui.click;

import net.skidcode.gh.maybeaclient.Client;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.ClientNameHack;

public class ClientNameTab
extends Tab {
    public static ClientNameTab instance;

    public ClientNameTab() {
        super("ClientName");
        this.xPos = 0;
        this.xDefPos = 0;
        this.yPos = 0;
        this.yDefPos = 0;
        this.height = 12;
        instance = this;
    }

    @Override
    public void renderName() {
        int xStart = this.xPos;
        int yStart = this.yPos;
        this.renderFrame(xStart, yStart, xStart + this.width, yStart + 12);
        Client.mc.fontRenderer.drawString("MaybeAClient 1.7.3", xStart + 2, yStart + 2, 0xFFFFFF);
    }

    @Override
    public void render() {
        this.width = Client.mc.fontRenderer.getStringWidth("MaybeAClient 1.7.3") + 2;
        this.renderName();
    }

    @Override
    public void renderIngame() {
        if (ClientNameHack.instance.status) {
            super.renderIngame();
        }
    }
}

