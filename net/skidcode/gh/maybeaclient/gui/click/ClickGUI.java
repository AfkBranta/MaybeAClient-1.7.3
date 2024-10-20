/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.skidcode.gh.maybeaclient.gui.click;

import java.util.ArrayList;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;
import net.skidcode.gh.maybeaclient.gui.click.ArrayListTab;
import net.skidcode.gh.maybeaclient.gui.click.CategoryTab;
import net.skidcode.gh.maybeaclient.gui.click.ClientInfoTab;
import net.skidcode.gh.maybeaclient.gui.click.ClientNameTab;
import net.skidcode.gh.maybeaclient.gui.click.InventoryTab;
import net.skidcode.gh.maybeaclient.gui.click.KeybindingsTab;
import net.skidcode.gh.maybeaclient.gui.click.PlayerViewTab;
import net.skidcode.gh.maybeaclient.gui.click.RadarTab;
import net.skidcode.gh.maybeaclient.gui.click.Tab;
import net.skidcode.gh.maybeaclient.hacks.ClickGUIHack;
import net.skidcode.gh.maybeaclient.hacks.category.Category;
import net.skidcode.gh.maybeaclient.utils.InputHandler;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGUI
extends GuiScreen {
    public GuiScreen parent;
    public static boolean initialized = false;
    public static ArrayList<Tab> tabs = new ArrayList();
    public static float mouseX;
    public static float mouseY;
    public static float mouseClicked;
    public Tab selectedTab = null;
    public Tab hoveringOver = null;
    public static InputHandler inputHandler;

    static {
        inputHandler = null;
    }

    public ClickGUI(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void keyTyped(char var1, int var2) {
        if (var2 == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            ClickGUIHack.instance.toggle();
        }
    }

    @Override
    public void handleMouseInput() {
    }

    @Override
    public void handleKeyboardInput() {
        if (inputHandler != null) {
            char keyname = Keyboard.getEventCharacter();
            int keycode = Keyboard.getEventKey();
            inputHandler.onKeyPress(keycode);
        } else {
            super.handleKeyboardInput();
        }
    }

    @Override
    public void handleInput() {
        while (Keyboard.next()) {
            this.handleKeyboardInput();
        }
    }

    @Override
    public void mouseClicked(int x, int y, int click) {
        for (Tab tab : tabs) {
            if (!tab.isPointInside(x, y)) continue;
            this.selectedTab = tab;
            tab.onSelect(click, x, y);
            tabs.remove(this.selectedTab);
            tabs.add(0, this.selectedTab);
            break;
        }
    }

    private void mouseWheelMoved(int x, int y, int wheel) {
        for (Tab tab : tabs) {
            if (!tab.isPointInside(x, y)) continue;
            this.selectedTab = tab;
            tab.wheelMoved(wheel, x, y);
            tabs.remove(this.selectedTab);
            tabs.add(0, this.selectedTab);
            break;
        }
    }

    @Override
    public void mouseMovedOrUp(int x, int y, int click) {
        block5: {
            block4: {
                if (this.selectedTab == null) break block4;
                this.selectedTab.mouseMovedSelected(click, x, y);
                if (click == -1) break block5;
                this.selectedTab.onDeselect(click, x, y);
                this.selectedTab = null;
                break block5;
            }
            if (this.hoveringOver != null && this.hoveringOver.isPointInside(x, y)) {
                this.hoveringOver.mouseHovered(x, y, click);
            } else {
                if (this.hoveringOver != null) {
                    this.hoveringOver.stopHovering();
                }
                for (Tab tab : tabs) {
                    if (!tab.isPointInside(x, y)) continue;
                    this.hoveringOver = tab;
                    this.hoveringOver.mouseHovered(x, y, click);
                    break;
                }
            }
        }
    }

    public static void registerTabs() {
        CategoryTab t = new CategoryTab(Category.MOVEMENT, 160, 10, 90);
        t.minimized = true;
        tabs.add(t);
        t = new CategoryTab(Category.RENDER, t.xPos, 24, 90);
        t.minimized = true;
        tabs.add(t);
        t = new CategoryTab(Category.COMBAT, t.xPos, 38, 90);
        t.minimized = true;
        tabs.add(t);
        t = new CategoryTab(Category.MISC, t.xPos, 52, 90);
        t.minimized = true;
        tabs.add(t);
        t = new CategoryTab(Category.UI, t.xPos, 66, 90);
        t.minimized = true;
        tabs.add(t);
        initialized = true;
        tabs.add(new ArrayListTab());
        tabs.add(new ClientNameTab());
        tabs.add(new ClientInfoTab());
        tabs.add(new KeybindingsTab());
        tabs.add(new RadarTab());
        tabs.add(new PlayerViewTab());
        tabs.add(new InventoryTab());
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        int prev = this.mc.gameSettings.guiScale;
        int newScale = ClickGUIHack.instance.getScale();
        if (prev != newScale) {
            this.mc.gameSettings.guiScale = newScale;
            ScaledResolution sc = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            this.setWorldAndResolution(this.mc, sc.getScaledWidth(), sc.getScaledHeight());
            this.mc.entityRenderer.setupScaledResolution();
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() != -1) break;
        }
        int i = tabs.size() - 1;
        while (i >= 0) {
            Tab t = tabs.get(i);
            GL11.glDisable((int)2929);
            t.render();
            --i;
        }
        GL11.glEnable((int)2929);
        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        int wheel = Mouse.getDWheel();
        if (wheel != 0) {
            this.mouseWheelMoved(x, y, wheel);
        }
        if (Mouse.getEventButtonState()) {
            this.mouseClicked(x, y, Mouse.getEventButton());
        } else {
            this.mouseMovedOrUp(x, y, Mouse.getEventButton());
        }
        super.drawScreen(var1, var2, var3);
        if (prev != newScale) {
            this.mc.gameSettings.guiScale = prev;
            ScaledResolution sc = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            this.setWorldAndResolution(this.mc, sc.getScaledWidth(), sc.getScaledHeight());
            this.mc.entityRenderer.setupScaledResolution();
        }
    }
}

