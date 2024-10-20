/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks.category;

import java.util.ArrayList;
import net.skidcode.gh.maybeaclient.hacks.Hack;

public enum Category {
    MOVEMENT("Movement"),
    RENDER("Render"),
    COMBAT("Combat"),
    MISC("Misc"),
    UI("UI");

    public String name;
    public ArrayList<Hack> hacks = new ArrayList();

    private Category(String name) {
        this.name = name;
    }
}

