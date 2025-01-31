package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiControls;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiVideoSettings;
import net.minecraft.src.StringTranslate;

public class GuiOptions
extends GuiScreen {
    private GuiScreen parentScreen;
    protected String screenTitle = "Options";
    private GameSettings options;
    private static EnumOptions[] field_22135_k = new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.DIFFICULTY};

    public GuiOptions(GuiScreen var1, GameSettings var2) {
        this.parentScreen = var1;
        this.options = var2;
    }

    @Override
    public void initGui() {
        StringTranslate var1 = StringTranslate.getInstance();
        this.screenTitle = var1.translateKey("options.title");
        int var2 = 0;
        EnumOptions[] var3 = field_22135_k;
        int var4 = var3.length;
        int var5 = 0;
        while (var5 < var4) {
            EnumOptions var6 = var3[var5];
            if (!var6.getEnumFloat()) {
                this.controlList.add(new GuiSmallButton(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6)));
            } else {
                this.controlList.add(new GuiSlider(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6), this.options.getOptionFloatValue(var6)));
            }
            ++var2;
            ++var5;
        }
        this.controlList.add(new GuiButton(101, this.width / 2 - 100, this.height / 6 + 96 + 12, var1.translateKey("options.video")));
        this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 120 + 12, var1.translateKey("options.controls")));
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, var1.translateKey("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id < 100 && var1 instanceof GuiSmallButton) {
                this.options.setOptionValue(((GuiSmallButton)var1).returnEnumOptions(), 1);
                var1.displayString = this.options.getKeyBinding(EnumOptions.func_20137_a(var1.id));
            }
            if (var1.id == 101) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiVideoSettings(this, this.options));
            }
            if (var1.id == 100) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiControls(this, this.options));
            }
            if (var1.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(var1, var2, var3);
    }
}

