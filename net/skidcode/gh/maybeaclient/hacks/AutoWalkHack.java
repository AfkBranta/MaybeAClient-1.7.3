/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class AutoWalkHack
extends Hack {
    public static AutoWalkHack instance;

    public AutoWalkHack() {
        super("AutoWalk", "Makes the player walk automatically", 65, Category.MOVEMENT);
        instance = this;
    }
}

