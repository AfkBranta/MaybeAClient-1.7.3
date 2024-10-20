/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class ClientInfoHack
extends Hack {
    public SettingBoolean coords;
    public SettingBoolean facing = new SettingBoolean(this, "Show facing", true);
    public SettingBoolean fps = new SettingBoolean(this, "Show fps", false);
    public SettingBoolean username = new SettingBoolean(this, "Show username", false);
    public SettingBoolean showNetherCoords;
    public SettingMode isInNether = new SettingMode(this, "IsInNether", "Detect", "Nether", "Overworld");
    public SettingBoolean walkingSpeed;
    public SettingBoolean useHorizontal = new SettingBoolean(this, "Use horizontal speed", true);
    public SettingMode alignment = new SettingMode(this, "Alignment", "Left", "Right");
    public SettingMode staticPositon;
    public static ClientInfoHack instance;

    public ClientInfoHack() {
        super("PlayerInfo", "Displays player info on screen", 27, Category.UI);
        instance = this;
        this.showNetherCoords = new SettingBoolean(this, "Show nether coords", false){

            @Override
            public void setValue(boolean value) {
                super.setValue(value);
                ClientInfoHack.instance.isInNether.hidden = !this.value;
            }
        };
        this.coords = new SettingBoolean(this, "Show coords", true){

            @Override
            public void setValue(boolean value) {
                super.setValue(value);
                ClientInfoHack.instance.showNetherCoords.hidden = !this.value;
            }
        };
        this.walkingSpeed = new SettingBoolean(this, "Show walking speed", false){

            @Override
            public void setValue(boolean value) {
                super.setValue(value);
                ClientInfoHack.instance.useHorizontal.hidden = !this.value;
            }
        };
        this.addSetting(this.coords);
        this.addSetting(this.showNetherCoords);
        this.addSetting(this.isInNether);
        this.addSetting(this.facing);
        this.addSetting(this.fps);
        this.addSetting(this.username);
        this.addSetting(this.walkingSpeed);
        this.addSetting(this.useHorizontal);
        this.staticPositon = new SettingMode(this, "Static Position", new String[]{"Disabled", "Top Right", "Bottom Left", "Top Left", "Bottom Right"}){

            @Override
            public void setValue(String value) {
                super.setValue(value);
                if (this.currentMode.equalsIgnoreCase("Disabled")) {
                    ClientInfoHack.instance.alignment.show();
                } else {
                    ClientInfoHack.instance.alignment.hide();
                }
            }
        };
        this.addSetting(this.staticPositon);
        this.addSetting(this.alignment);
    }
}

