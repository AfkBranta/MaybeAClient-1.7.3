/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class AntiSlowdownHack
extends Hack {
    public static AntiSlowdownHack instance;

    public AntiSlowdownHack() {
        super("AntiSlowdown", "Removes slowdown", 0, Category.MOVEMENT);
        instance = this;
    }
}

