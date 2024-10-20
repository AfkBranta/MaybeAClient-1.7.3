/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Tessellator;
import net.minecraft.src.XGuiUmmm;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen
extends Gui {
    protected Minecraft mc;
    public int width;
    public int height;
    protected List<GuiButton> controlList = new ArrayList<GuiButton>();
    public boolean field_948_f = false;
    protected FontRenderer fontRenderer;
    public XGuiUmmm field_25091_h;
    private GuiButton selectedButton = null;

    public void drawScreen(int var1, int var2, float var3) {
        int var4 = 0;
        while (var4 < this.controlList.size()) {
            GuiButton var5 = this.controlList.get(var4);
            var5.drawButton(this.mc, var1, var2);
            ++var4;
        }
    }

    public GuiScreen() {
        this.mc = Minecraft.theMinecraft;
    }

    protected void keyTyped(char var1, int var2) {
        if (var2 == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    public static String getClipboardString() {
        try {
            Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String var1 = (String)var0.getTransferData(DataFlavor.stringFlavor);
                return var1;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    protected void mouseClicked(int var1, int var2, int var3) {
        if (var3 == 0) {
            int var4 = 0;
            while (var4 < this.controlList.size()) {
                GuiButton var5 = this.controlList.get(var4);
                if (var5.mousePressed(this.mc, var1, var2)) {
                    this.selectedButton = var5;
                    this.mc.sndManager.func_337_a("random.click", 1.0f, 1.0f);
                    this.actionPerformed(var5);
                }
                ++var4;
            }
        }
    }

    protected void mouseMovedOrUp(int var1, int var2, int click) {
        if (this.selectedButton != null && click == 0) {
            this.selectedButton.mouseReleased(var1, var2);
            this.selectedButton = null;
        }
    }

    protected void actionPerformed(GuiButton var1) {
    }

    public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
        this.field_25091_h = new XGuiUmmm(var1);
        this.mc = var1;
        this.fontRenderer = var1.fontRenderer;
        this.width = var2;
        this.height = var3;
        this.controlList.clear();
        this.initGui();
    }

    public void initGui() {
    }

    public void handleInput() {
        while (Mouse.next()) {
            this.handleMouseInput();
        }
        while (Keyboard.next()) {
            this.handleKeyboardInput();
        }
    }

    public void handleMouseInput() {
        if (Mouse.getEventButtonState()) {
            int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            this.mouseClicked(var1, var2, Mouse.getEventButton());
        } else {
            int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            this.mouseMovedOrUp(var1, var2, Mouse.getEventButton());
        }
    }

    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.mc.toggleFullscreen();
                return;
            }
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }

    public void drawWorldBackground(int var1) {
        if (this.mc.theWorld != null) {
            this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.drawBackground(var1);
        }
    }

    public void drawBackground(int var1) {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        Tessellator var2 = Tessellator.instance;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var3 = 32.0f;
        var2.startDrawingQuads();
        var2.setColorOpaque_I(0x404040);
        var2.addVertexWithUV(0.0, this.height, 0.0, 0.0, (float)this.height / var3 + (float)var1);
        var2.addVertexWithUV(this.width, this.height, 0.0, (float)this.width / var3, (float)this.height / var3 + (float)var1);
        var2.addVertexWithUV(this.width, 0.0, 0.0, (float)this.width / var3, 0 + var1);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0 + var1);
        var2.draw();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void deleteWorld(boolean var1, int var2) {
    }
}

