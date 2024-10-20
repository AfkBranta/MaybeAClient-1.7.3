package net.minecraft.src;

import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.StringTranslate;

public class GuiControls
extends GuiScreen {
    private GuiScreen parentScreen;
    protected String screenTitle = "Controls";
    private GameSettings options;
    private int buttonId = -1;

    public GuiControls(GuiScreen var1, GameSettings var2) {
        this.parentScreen = var1;
        this.options = var2;
    }

    private int func_20080_j() {
        return this.width / 2 - 155;
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        int var2 = this.func_20080_j();
        int var3 = 0;
        while (var3 < this.options.keyBindings.length) {
            this.controlList.add(new GuiSmallButton(var3, var2 + var3 % 2 * 160, this.height / 6 + 24 * (var3 >> 1), 70, 20, this.options.getOptionDisplayString(var3)));
            ++var3;
        }
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, var1.translateKey("gui.done")));
        this.screenTitle = var1.translateKey("controls.title");
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        int var2 = 0;
        while (var2 < this.options.keyBindings.length) {
            ((GuiButton)this.controlList.get((int)var2)).displayString = this.options.getOptionDisplayString(var2);
            ++var2;
        }
        if (var1.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        } else {
            this.buttonId = var1.id;
            var1.displayString = "> " + this.options.getOptionDisplayString(var1.id) + " <";
        }
    }

    @Override
    protected void keyTyped(char var1, int var2) {
        if (this.buttonId >= 0) {
            this.options.setKeyBinding(this.buttonId, var2);
            ((GuiButton)this.controlList.get((int)this.buttonId)).displayString = this.options.getOptionDisplayString(this.buttonId);
            this.buttonId = -1;
        } else {
            super.keyTyped(var1, var2);
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        int var4 = this.func_20080_j();
        int var5 = 0;
        while (var5 < this.options.keyBindings.length) {
            this.drawString(this.fontRenderer, this.options.getKeyBindingDescription(var5), var4 + var5 % 2 * 160 + 70 + 6, this.height / 6 + 24 * (var5 >> 1) + 7, -1);
            ++var5;
        }
        super.drawScreen(var1, var2, var3);
    }
}

