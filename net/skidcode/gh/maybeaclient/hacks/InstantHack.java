/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class InstantHack
extends Hack {
    public static InstantHack instance;

    public InstantHack() {
        super("Instant", "Instantmine", 0, Category.MISC);
        instance = this;
    }
}

