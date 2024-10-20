/*
 * Decompiled with CFR 0.152.
 */
package net.skidcode.gh.maybeaclient.hacks;

import net.minecraft.src.Block;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingBlockChooser;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingFloat;
import net.skidcode.gh.maybeaclient.hacks.settings.SettingMode;
import net.skidcode.gh.maybeaclient.utils.ChatColor;

public class XRayHack
extends Hack {
    public static XRayHack INSTANCE;
    public SettingFloat opacity;
    public SettingMode mode;
    public SettingBlockChooser blockChooser;

    public XRayHack() {
        super("xRay", "Force disables certain block rendering", 45, Category.RENDER);
        this.blockChooser = new SettingBlockChooser(this, "Blocks", new int[]{Block.oreCoal.blockID, Block.oreDiamond.blockID, Block.oreGold.blockID, Block.oreIron.blockID, Block.oreLapis.blockID, Block.oreRedstone.blockID, Block.oreRedstoneGlowing.blockID, Block.blockDiamond.blockID, Block.blockSteel.blockID, Block.blockGold.blockID, Block.blockLapis.blockID}){

            @Override
            public void blockChanged(int id) {
                XRayHack.mc.entityRenderer.updateRenderer();
                XRayHack.mc.theWorld.markBlocksDirty((int)XRayHack.mc.thePlayer.posX - 256, 0, (int)XRayHack.mc.thePlayer.posZ - 256, (int)XRayHack.mc.thePlayer.posX + 256, 127, (int)XRayHack.mc.thePlayer.posZ + 256);
            }
        };
        INSTANCE = this;
        this.opacity = new SettingFloat(this, "Opacity", 0.5f, 0.0f, 1.0f){

            @Override
            public void setValue(float value) {
                super.setValue(value);
                if (this.hack.status && XRayHack.mc.theWorld != null) {
                    XRayHack.mc.entityRenderer.updateRenderer();
                    XRayHack.mc.theWorld.markBlocksDirty((int)XRayHack.mc.thePlayer.posX - 256, 0, (int)XRayHack.mc.thePlayer.posZ - 256, (int)XRayHack.mc.thePlayer.posX + 256, 127, (int)XRayHack.mc.thePlayer.posZ + 256);
                }
            }
        };
        this.mode = new SettingMode(this, "Mode", new String[]{"Normal", "Opacity"}){

            @Override
            public void setValue(String value) {
                boolean toggle = false;
                if (this.currentMode != null && !this.currentMode.equalsIgnoreCase(value) && XRayHack.INSTANCE.status) {
                    toggle = true;
                }
                super.setValue(value);
                if (toggle && XRayHack.mc.theWorld != null) {
                    XRayHack.mc.entityRenderer.updateRenderer();
                    XRayHack.mc.theWorld.markBlocksDirty((int)XRayHack.mc.thePlayer.posX - 256, 0, (int)XRayHack.mc.thePlayer.posZ - 256, (int)XRayHack.mc.thePlayer.posX + 256, 127, (int)XRayHack.mc.thePlayer.posZ + 256);
                }
                if (this.currentMode.equalsIgnoreCase("Opacity")) {
                    XRayHack.INSTANCE.opacity.show();
                } else {
                    XRayHack.INSTANCE.opacity.hide();
                }
            }
        };
        this.addSetting(this.opacity);
        this.addSetting(this.mode);
        this.addSetting(this.blockChooser);
    }

    @Override
    public String getNameForArrayList() {
        String s = "[";
        s = String.valueOf(s) + (Object)((Object)ChatColor.LIGHTCYAN);
        s = String.valueOf(s) + this.mode.currentMode;
        s = String.valueOf(s) + (Object)((Object)ChatColor.WHITE);
        s = String.valueOf(s) + "]";
        return String.valueOf(this.name) + s;
    }

    @Override
    public void toggle() {
        super.toggle();
        XRayHack.mc.entityRenderer.updateRenderer();
        XRayHack.mc.theWorld.markBlocksDirty((int)XRayHack.mc.thePlayer.posX - 256, 0, (int)XRayHack.mc.thePlayer.posZ - 256, (int)XRayHack.mc.thePlayer.posX + 256, 127, (int)XRayHack.mc.thePlayer.posZ + 256);
    }
}

