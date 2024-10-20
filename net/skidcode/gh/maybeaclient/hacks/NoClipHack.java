/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class NoClipHack
extends Hack {
    public static NoClipHack instance;

    public NoClipHack() {
        super("NoClip", "allows player to move through blocks", 0, Category.MOVEMENT);
        instance = this;
    }
}

