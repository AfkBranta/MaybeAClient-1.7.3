/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.skidcode.gh.maybeaclient.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.skidcode.gh.maybeaclient.hacks.Hack;
import net.skidcode.gh.maybeaclient.utils.ChatColor;
import org.lwjgl.input.Keyboard;

public class GuiBindModule
extends GuiScreen {
    public GuiScreen parent;
    public Hack hack;
    public int currentKey = 0;
    public boolean pressed = false;

    public GuiBindModule(GuiScreen parent, Hack hack) {
        this.parent = parent;
        this.hack = hack;
    }

    @Override
    public void keyTyped(char var1, int var2) {
        this.currentKey = var2 == 1 ? 0 : var2;
        this.pressed = true;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1: {
                if (this.pressed) {
                    this.hack.bind(this.currentKey);
                }
                this.mc.displayGuiScreen(this.parent);
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(this.parent);
            }
        }
    }

    @Override
    public void initGui() {
        int midX = this.width / 2;
        int midY = this.height / 2;
        this.controlList.add(new GuiButton(1, midX - 48, midY + 16, 48, 20, "Done"));
        this.controlList.add(new GuiButton(2, midX, midY + 16, 48, 20, "Cancel"));
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        int midX = this.width / 2;
        int midY = this.height / 2;
        String s = (Object)((Object)ChatColor.LIGHTCYAN) + "Module: " + (Object)((Object)ChatColor.GOLD) + this.hack.name;
        this.fontRenderer.drawString(s, midX - this.fontRenderer.getStringWidth(s) / 2, midY - 24, -559038737);
        s = (Object)((Object)ChatColor.LIGHTCYAN) + "Current bind: " + (Object)((Object)ChatColor.GOLD) + this.hack.keybinding.valueToString();
        this.fontRenderer.drawString(s, midX - this.fontRenderer.getStringWidth(s) / 2, midY - 12, -559038737);
        s = (Object)((Object)ChatColor.LIGHTCYAN) + "New bind: " + (Object)((Object)ChatColor.GOLD) + (this.pressed ? Keyboard.getKeyName((int)this.currentKey) : "Press Any Key(ESC = None)");
        this.fontRenderer.drawString(s, midX - this.fontRenderer.getStringWidth(s) / 2, midY - 0, -559038737);
        super.drawScreen(var1, var2, var3);
    }
}

