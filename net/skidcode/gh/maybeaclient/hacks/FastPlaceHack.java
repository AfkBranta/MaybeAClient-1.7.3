/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class FastPlaceHack
extends Hack {
    public static FastPlaceHack instance;

    public FastPlaceHack() {
        super("FastPlace", "Removes delay between block placement", 0, Category.MISC);
        instance = this;
    }
}

