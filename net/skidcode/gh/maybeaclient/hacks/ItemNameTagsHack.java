/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.EntityItem;
import net.minecraft.src.StringTranslate;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;

public class ItemNameTagsHack
extends Hack {
    public static ItemNameTagsHack instance;
    public static SettingBoolean enableCountDisplay;

    public ItemNameTagsHack() {
        super("ItemNameTags", "show item nametags", 0, Category.RENDER);
        instance = this;
        enableCountDisplay = new SettingBoolean(this, "Display count", true);
        this.addSetting(enableCountDisplay);
    }

    public static String getName(EntityItem entity) {
        String s = StringTranslate.getInstance().translateNamedKey(entity.item.func_20109_f()).trim();
        if (ItemNameTagsHack.enableCountDisplay.value) {
            return String.valueOf(entity.item.stackSize) + " " + s;
        }
        return s;
    }
}

