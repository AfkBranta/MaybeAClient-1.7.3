/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class FullBrightHack
extends Hack {
    public static FullBrightHack INSTANCE;

    public FullBrightHack() {
        super("FullBright", "Makes the world brighter", 46, Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public void toggle() {
        super.toggle();
        FullBrightHack.mc.entityRenderer.updateRenderer();
        FullBrightHack.mc.theWorld.markBlocksDirty((int)FullBrightHack.mc.thePlayer.posX - 256, 0, (int)FullBrightHack.mc.thePlayer.posZ - 256, (int)FullBrightHack.mc.thePlayer.posX + 256, 127, (int)FullBrightHack.mc.thePlayer.posZ + 256);
    }
}

