/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class InventoryWalkHack
extends Hack {
    public static InventoryWalkHack instance;

    public InventoryWalkHack() {
        super("InventoryWalk", "Allows you to walk while being in container gui", 0, Category.MOVEMENT);
        instance = this;
    }
}

