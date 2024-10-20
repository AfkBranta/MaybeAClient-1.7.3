/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class SafeWalkHack
extends Hack {
    public static SafeWalkHack instance;

    public SafeWalkHack() {
        super("SafeWalk", "Players will not fall from blocks", 0, Category.MOVEMENT);
        instance = this;
    }
}

