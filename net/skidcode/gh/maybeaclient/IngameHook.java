package net.skidcode.gh.maybeaclient;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ScaledResolution;
import net.skidcode.gh.maybeaclient.gui.click.ClickGUI;
import net.skidcode.gh.maybeaclient.hacks.ClickGUIHack;

public class IngameHook {
    public static Minecraft mc;

    public static void handleIngame(ScaledResolution res) {
        if (!(IngameHook.mc.currentScreen instanceof ClickGUI)) {
            int prev = IngameHook.mc.gameSettings.guiScale;
            int newScale = ClickGUIHack.instance.getScale();
            if (prev != newScale) {
                IngameHook.mc.gameSettings.guiScale = newScale;
                IngameHook.mc.entityRenderer.setupScaledResolution();
            }
            int i = ClickGUI.tabs.size() - 1;
            while (i >= 0) {
                ClickGUI.tabs.get(i).renderIngame();
                --i;
            }
            if (prev != newScale) {
                IngameHook.mc.gameSettings.guiScale = prev;
                IngameHook.mc.entityRenderer.setupScaledResolution();
            }
        }
    }
}

