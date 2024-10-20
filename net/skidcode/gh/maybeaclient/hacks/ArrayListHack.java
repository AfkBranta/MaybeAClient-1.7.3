/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import java.util.Comparator;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class ArrayListHack
extends Hack {
    public SettingMode sortMode = new SettingMode(this, "Sorting", "Descending", "Ascending", "None");
    public SettingMode alignment = new SettingMode(this, "Alignment", "Left", "Right");
    public SettingMode expand = new SettingMode(this, "Expand", "Top", "Bottom");
    public SettingMode staticPositon;
    public static ArrayListHack instance;

    public ArrayListHack() {
        super("ArrayList", "Shows list of enabled modules", 0, Category.UI);
        instance = this;
        this.addSetting(this.sortMode);
        this.staticPositon = new SettingMode(this, "Static Position", new String[]{"Bottom Right", "Top Left", "Top Right", "Bottom Left", "Disabled"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                if (this.currentMode.equalsIgnoreCase("Disabled")) {
                    ArrayListHack.instance.alignment.show();
                    ArrayListHack.instance.expand.show();
                } else {
                    ArrayListHack.instance.alignment.hide();
                    ArrayListHack.instance.expand.hide();
                }
            }
        };
        this.addSetting(this.staticPositon);
        this.addSetting(this.alignment);
        this.addSetting(this.expand);
    }

    public static class SorterAZ
    implements Comparator<String> {
        public static SorterAZ inst = new SorterAZ();

        @Override
        public int compare(String o1, String o2) {
            int name2;
            int name1 = ArrayListHack.mc.fontRenderer.getStringWidth(o1);
            if (name1 > (name2 = ArrayListHack.mc.fontRenderer.getStringWidth(o2))) {
                return -1;
            }
            if (name1 < name2) {
                return 1;
            }
            return 0;
        }
    }

    public static class SorterZA
    implements Comparator<String> {
        public static SorterZA inst = new SorterZA();

        @Override
        public int compare(String o1, String o2) {
            int name2;
            int name1 = ArrayListHack.mc.fontRenderer.getStringWidth(o1);
            if (name1 > (name2 = ArrayListHack.mc.fontRenderer.getStringWidth(o2))) {
                return 1;
            }
            if (name1 < name2) {
                return -1;
            }
            return 0;
        }
    }
}

