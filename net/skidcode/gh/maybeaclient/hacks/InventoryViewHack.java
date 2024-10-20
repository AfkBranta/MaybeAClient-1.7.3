/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class InventoryViewHack
extends Hack {
    public static InventoryViewHack instance;

    public InventoryViewHack() {
        super("InventoryView", "Shows Inventory tab", 0, Category.UI);
        instance = this;
    }
}

