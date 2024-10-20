/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPlayer;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingColor;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class EntityESPHack
extends Hack {
    public SettingChooser chooser;
    public SettingMode playersMode = new SettingMode(this, "Players Mode", "Box", "Fill", "Outline");
    public SettingMode animalsMode = new SettingMode(this, "Animals Mode", "Box", "Fill", "Outline");
    public SettingMode hostileMode = new SettingMode(this, "Hostiles Mode", "Box", "Fill", "Outline");
    public SettingMode itemsMode = new SettingMode(this, "Items Mode", "Box", "Fill");
    public SettingColor playerColor = new SettingColor(this, "Players Color", 255, 0, 0);
    public SettingColor animalColor = new SettingColor(this, "Animals Color", 255, 255, 0);
    public SettingColor hostileColor = new SettingColor(this, "Hostiles Color", 255, 0, 255);
    public SettingColor itemColor = new SettingColor(this, "Item Color", 0, 0, 255);
    public SettingBoolean renderingOrder = new SettingBoolean(this, "Vanilla rendering order", true);
    public static EntityESPHack instance;
    public static boolean allowRendering;

    static {
        allowRendering = false;
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        if (this.chooser.getValue("Players")) {
            s = String.valueOf(s) + "P";
        }
        if (this.chooser.getValue("Animals")) {
            s = String.valueOf(s) + "A";
        }
        if (this.chooser.getValue("Hostiles")) {
            s = String.valueOf(s) + "H";
        }
        if (this.chooser.getValue("Items")) {
            s = String.valueOf(s) + "I";
        }
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    public EntityESPHack() {
        super("EntityESP", "Highlights entities", 0, Category.RENDER);
        instance = this;
        boolean[] blArray = new boolean[4];
        blArray[0] = true;
        blArray[1] = true;
        blArray[2] = true;
        this.chooser = new SettingChooser(this, "Entity Chooser", new String[]{"Players", "Animals", "Hostiles", "Items"}, blArray){

            @Override
            public void setValue(String name, boolean value) {
                super.setValue(name, value);
                if (name.equalsIgnoreCase("Players")) {
                    boolean bl = EntityESPHack.instance.playerColor.hidden = !value;
                }
                if (name.equalsIgnoreCase("Animals")) {
                    boolean bl = EntityESPHack.instance.animalColor.hidden = !value;
                }
                if (name.equalsIgnoreCase("Hostiles")) {
                    boolean bl = EntityESPHack.instance.hostileColor.hidden = !value;
                }
                if (name.equalsIgnoreCase("Items")) {
                    EntityESPHack.instance.itemColor.hidden = !value;
                }
            }
        };
        this.addSetting(this.chooser);
        this.addSetting(this.playerColor);
        this.addSetting(this.playersMode);
        this.addSetting(this.animalColor);
        this.addSetting(this.animalsMode);
        this.addSetting(this.hostileColor);
        this.addSetting(this.hostileMode);
        this.addSetting(this.itemColor);
        this.addSetting(this.itemsMode);
        this.addSetting(this.renderingOrder);
    }

    public String getRenderingMode(Entity e) {
        if (e instanceof EntityPlayer) {
            return this.playersMode.currentMode;
        }
        if (e instanceof EntityAnimals) {
            return this.animalsMode.currentMode;
        }
        if (e instanceof EntityMobs) {
            return this.hostileMode.currentMode;
        }
        if (e instanceof EntityItem) {
            return this.itemsMode.currentMode;
        }
        return "";
    }

    public boolean shouldRender(Entity e) {
        return allowRendering && (this.chooser.getValue("Players") && e instanceof EntityPlayer || this.chooser.getValue("Animals") && e instanceof EntityAnimals || this.chooser.getValue("Hostiles") && e instanceof EntityMobs || this.chooser.getValue("Items") && e instanceof EntityItem);
    }
}

