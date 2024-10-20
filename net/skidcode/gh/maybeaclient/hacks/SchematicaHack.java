/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import lunatrius.schematica.GuiSchematicControl;
import lunatrius.schematica.GuiSchematicLoad;
import lunatrius.schematica.GuiSchematicSave;
import lunatrius.schematica.Render;
import lunatrius.schematica.Settings;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBoolean;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingInteger;

public class SchematicaHack
extends Hack {
    public SettingBoolean enableAlpha = new SettingBoolean(this, "Enable Transparency", false){

        @Override
        public void setValue(boolean b) {
            super.setValue(b);
            Settings.instance().needsUpdate = true;
        }
    };
    public SettingInteger alpha = new SettingInteger(this, "Transparency", 0, 0, 255){

        @Override
        public void setValue(int i) {
            int prev = this.value;
            super.setValue(i);
            if (this.value != prev) {
                Settings.instance().needsUpdate = true;
            }
        }
    };
    public SettingBoolean highlight = new SettingBoolean(this, "Highlight", true);
    public SettingBoolean openLoad = new SettingBoolean(this, "Load", false){

        @Override
        public void setValue(boolean d) {
        }

        @Override
        public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
            mc.displayGuiScreen(new GuiSchematicLoad(SchematicaHack.mc.currentScreen));
        }
    };
    public SettingBoolean openSave = new SettingBoolean(this, "Save", false){

        @Override
        public void setValue(boolean d) {
        }

        @Override
        public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
            mc.displayGuiScreen(new GuiSchematicSave(SchematicaHack.mc.currentScreen));
        }
    };
    public SettingBoolean openControl = new SettingBoolean(this, "Control", false){

        @Override
        public void setValue(boolean d) {
        }

        @Override
        public void onDeselect(Tab tab, int xMin, int yMin, int xMax, int yMax, int mouseX, int mouseY, int mouseClick) {
            mc.displayGuiScreen(new GuiSchematicControl(SchematicaHack.mc.currentScreen));
        }
    };
    public final Render render = new Render(this);
    public static SchematicaHack instance;

    public SchematicaHack() {
        super("Schematica", "Port of Schematica", 0, Category.MISC);
        instance = this;
        this.addSetting(this.enableAlpha);
        this.addSetting(this.alpha);
        this.addSetting(this.highlight);
        this.addSetting(this.openLoad);
        this.addSetting(this.openSave);
        this.addSetting(this.openControl);
        Settings.schematicDirectory.mkdirs();
        Settings.textureDirectory.mkdirs();
    }
}

