/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;

public class ClientNameHack
extends Hack {
    public static ClientNameHack instance;

    public ClientNameHack() {
        super("ClientName", "Show client name", 0, Category.UI);
        instance = this;
        this.status = true;
    }
}

