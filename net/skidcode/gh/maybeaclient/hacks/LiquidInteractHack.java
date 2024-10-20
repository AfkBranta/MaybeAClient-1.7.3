/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class LiquidInteractHack
extends Hack {
    public static LiquidInteractHack instance;

    public LiquidInteractHack() {
        super("LiquidInteract", "Allows you to interact with liquids", 0, Category.MISC);
        instance = this;
    }
}

