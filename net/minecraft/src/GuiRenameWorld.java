/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.WorldInfo;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld
extends GuiScreen {
    private GuiScreen field_22112_a;
    private GuiTextField field_22114_h;
    private final String field_22113_i;

    public GuiRenameWorld(GuiScreen var1, String var2) {
        this.field_22112_a = var1;
        this.field_22113_i = var2;
    }

    @Override
    public void updateScreen() {
        this.field_22114_h.updateCursorCounter();
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("selectWorld.renameButton")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        ISaveFormat var2 = this.mc.getSaveLoader();
        WorldInfo var3 = var2.func_22173_b(this.field_22113_i);
        String var4 = var3.getWorldName();
        this.field_22114_h = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20, var4);
        this.field_22114_h.isFocused = true;
        this.field_22114_h.setMaxStringLength(32);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == 1) {
                this.mc.displayGuiScreen(this.field_22112_a);
            } else if (var1.id == 0) {
                ISaveFormat var2 = this.mc.getSaveLoader();
                var2.func_22170_a(this.field_22113_i, this.field_22114_h.getText().trim());
                this.mc.displayGuiScreen(this.field_22112_a);
            }
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        this.field_22114_h.textboxKeyTyped(var1, var2);
        boolean bl = ((GuiButton)this.controlList.get((int)0)).enabled = this.field_22114_h.getText().trim().length() > 0;
        if (var1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        this.field_22114_h.mouseClicked(var1, var2, var3);
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("selectWorld.renameTitle"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.drawString(this.fontRenderer, var4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 0xA0A0A0);
        this.field_22114_h.drawTextBox();
        super.drawScreen(var1, var2, var3);
    }
}

