/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import java.util.ArrayList;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiChat
extends GuiScreen {
    protected String message = "";
    private int updateCounter = 0;
    private static final String field_20082_i;
    public static ArrayList<String> history;
    public int lastHistoryIndex = history.size();

    static {
        history = new ArrayList();
        field_20082_i = FontAllowedCharacters.allowedCharacters;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        if (var2 == 200) {
            if (this.lastHistoryIndex > 0 && this.lastHistoryIndex <= history.size()) {
                this.message = history.get(--this.lastHistoryIndex);
            }
        } else if (var2 == 208) {
            if (this.lastHistoryIndex + 1 >= history.size()) {
                this.message = "";
                if (++this.lastHistoryIndex > history.size()) {
                    --this.lastHistoryIndex;
                }
            } else if (this.lastHistoryIndex >= 0 && this.lastHistoryIndex < history.size()) {
                this.message = history.get(++this.lastHistoryIndex);
            }
        } else if (var2 == 1) {
            this.mc.displayGuiScreen(null);
        } else if (var2 == 28) {
            String var3 = this.message.trim();
            if (var3.length() > 0) {
                String var4 = this.message.trim();
                history.add(var4);
                while (history.size() > 100) {
                    history.remove(0);
                }
                if (!this.mc.lineIsCommand(var4)) {
                    this.mc.thePlayer.sendChatMessage(var4);
                }
            }
            if (this.mc.currentScreen instanceof GuiChat) {
                this.mc.displayGuiScreen(null);
            }
        } else {
            if (var2 == 14 && this.message.length() > 0) {
                this.message = this.message.substring(0, this.message.length() - 1);
            }
            if (field_20082_i.indexOf(var1) >= 0 && this.message.length() < 100) {
                this.message = String.valueOf(this.message) + var1;
            }
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.drawString(this.fontRenderer, "> " + this.message + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 0xE0E0E0);
        super.drawScreen(var1, var2, var3);
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        if (var3 == 0) {
            if (this.mc.ingameGUI.field_933_a != null) {
                if (this.message.length() > 0 && !this.message.endsWith(" ")) {
                    this.message = String.valueOf(this.message) + " ";
                }
                this.message = String.valueOf(this.message) + this.mc.ingameGUI.field_933_a;
                int var4 = 100;
                if (this.message.length() > var4) {
                    this.message = this.message.substring(0, var4);
                }
            } else {
                super.mouseClicked(var1, var2, var3);
            }
        }
    }
}

