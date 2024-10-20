/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PlayerControllerSP;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld
extends GuiScreen {
    private GuiScreen field_22131_a;
    private GuiTextField textboxWorldName;
    private GuiTextField textboxSeed;
    private String folderName;
    private boolean createClicked;

    public GuiCreateWorld(GuiScreen var1) {
        this.field_22131_a = var1;
    }

    @Override
    public void updateScreen() {
        this.textboxWorldName.updateCursorCounter();
        this.textboxSeed.updateCursorCounter();
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("selectWorld.create")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        this.textboxWorldName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20, var1.translateKey("selectWorld.newWorld"));
        this.textboxWorldName.isFocused = true;
        this.textboxWorldName.setMaxStringLength(32);
        this.textboxSeed = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 116, 200, 20, "");
        this.func_22129_j();
    }

    private void func_22129_j() {
        this.folderName = this.textboxWorldName.getText().trim();
        char[] var1 = FontAllowedCharacters.field_22286_b;
        int var2 = var1.length;
        int var3 = 0;
        while (var3 < var2) {
            char var4 = var1[var3];
            this.folderName = this.folderName.replace(var4, '_');
            ++var3;
        }
        if (MathHelper.stringNullOrLengthZero(this.folderName)) {
            this.folderName = "World";
        }
        this.folderName = GuiCreateWorld.func_25097_a(this.mc.getSaveLoader(), this.folderName);
    }

    public static String func_25097_a(ISaveFormat var0, String var1) {
        while (var0.func_22173_b(var1) != null) {
            var1 = String.valueOf(var1) + "-";
        }
        return var1;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == 1) {
                this.mc.displayGuiScreen(this.field_22131_a);
            } else if (var1.id == 0) {
                this.mc.displayGuiScreen(null);
                if (this.createClicked) {
                    return;
                }
                this.createClicked = true;
                long var2 = new Random().nextLong();
                String var4 = this.textboxSeed.getText();
                if (!MathHelper.stringNullOrLengthZero(var4)) {
                    try {
                        long var5 = Long.parseLong(var4);
                        if (var5 != 0L) {
                            var2 = var5;
                        }
                    }
                    catch (NumberFormatException var7) {
                        var2 = var4.hashCode();
                    }
                }
                this.mc.playerController = new PlayerControllerSP(this.mc);
                this.mc.startWorld(this.folderName, this.textboxWorldName.getText(), var2);
                this.mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        this.textboxWorldName.textboxKeyTyped(var1, var2);
        this.textboxSeed.textboxKeyTyped(var1, var2);
        if (var1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
        ((GuiButton)this.controlList.get((int)0)).enabled = this.textboxWorldName.getText().length() > 0;
        this.func_22129_j();
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        this.textboxWorldName.mouseClicked(var1, var2, var3);
        this.textboxSeed.mouseClicked(var1, var2, var3);
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("selectWorld.create"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.drawString(this.fontRenderer, var4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 0xA0A0A0);
        this.drawString(this.fontRenderer, String.valueOf(var4.translateKey("selectWorld.resultFolder")) + " " + this.folderName, this.width / 2 - 100, 85, 0xA0A0A0);
        this.drawString(this.fontRenderer, var4.translateKey("selectWorld.enterSeed"), this.width / 2 - 100, 104, 0xA0A0A0);
        this.drawString(this.fontRenderer, var4.translateKey("selectWorld.seedInfo"), this.width / 2 - 100, 140, 0xA0A0A0);
        this.textboxWorldName.drawTextBox();
        this.textboxSeed.drawTextBox();
        super.drawScreen(var1, var2, var3);
    }
}

