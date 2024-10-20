/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;

public class ClickGUIHack
extends Hack {
    public static ClickGUIHack instance;
    public SettingMode guiScale = new SettingMode(this, "Gui Scale", "Game", "Small", "Normal", "Large");

    public ClickGUIHack() {
        super("ClickGUI", "Open ClickGUI", 200, Category.UI);
        instance = this;
        this.addSetting(this.guiScale);
    }

    public int getScale() {
        if (this.guiScale.currentMode.equalsIgnoreCase("Large")) {
            return 3;
        }
        if (this.guiScale.currentMode.equalsIgnoreCase("Normal")) {
            return 2;
        }
        if (this.guiScale.currentMode.equalsIgnoreCase("Small")) {
            return 1;
        }
        return ClickGUIHack.mc.gameSettings.guiScale;
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGUI(ClickGUIHack.mc.currentScreen));
    }

    @Override
    public void onDisable() {
        if (ClickGUIHack.mc.currentScreen instanceof ClickGUI) {
            mc.displayGuiScreen(((ClickGUI)ClickGUIHack.mc.currentScreen).parent);
        }
    }
}

