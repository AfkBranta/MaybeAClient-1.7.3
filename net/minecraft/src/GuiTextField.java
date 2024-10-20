package net.minecraft.src;

import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

public class GuiTextField
extends Gui {
    private final FontRenderer fontRenderer;
    public final int xPos;
    public final int yPos;
    public final int width;
    public final int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    public boolean isFocused = false;
    public boolean isEnabled = true;

    public GuiTextField(FontRenderer var1, int var2, int var3, int var4, int var5, String var6) {
        this.fontRenderer = var1;
        this.xPos = var2;
        this.yPos = var3;
        this.width = var4;
        this.height = var5;
        this.setText(var6);
    }

    public void setText(String var1) {
        this.text = var1;
    }

    public String getText() {
        return this.text;
    }

    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    public void textboxKeyTyped(char var1, int var2) {
        if (this.isEnabled && this.isFocused) {
            if (var1 == '\u0016') {
                int var4;
                String var3 = GuiScreen.getClipboardString();
                if (var3 == null) {
                    var3 = "";
                }
                if ((var4 = 32 - this.text.length()) > var3.length()) {
                    var4 = var3.length();
                }
                if (var4 > 0) {
                    this.text = String.valueOf(this.text) + var3.substring(0, var4);
                }
            }
            if (var2 == 14 && this.text.length() > 0) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
            if (FontAllowedCharacters.allowedCharacters.indexOf(var1) >= 0 && (this.text.length() < this.maxStringLength || this.maxStringLength == 0)) {
                this.text = String.valueOf(this.text) + var1;
            }
        }
    }

    public void mouseClicked(int var1, int var2, int var3) {
        boolean var4;
        boolean bl = var4 = this.isEnabled && var1 >= this.xPos && var1 < this.xPos + this.width && var2 >= this.yPos && var2 < this.yPos + this.height;
        if (var4 && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = var4;
    }

    public void drawTextBox() {
        this.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
        this.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
        if (this.isEnabled) {
            boolean var1 = this.isFocused && this.cursorCounter / 6 % 2 == 0;
            this.drawString(this.fontRenderer, String.valueOf(this.text) + (var1 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 0xE0E0E0);
        } else {
            this.drawString(this.fontRenderer, this.text, this.xPos + 4, this.yPos + (this.height - 8) / 2, 0x707070);
        }
    }

    public void drawRedTextBox() {
        this.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -43691);
        this.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
        if (this.isEnabled) {
            boolean var1 = this.isFocused && this.cursorCounter / 6 % 2 == 0;
            this.drawString(this.fontRenderer, String.valueOf(this.text) + (var1 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 0xFF5555);
        } else {
            this.drawString(this.fontRenderer, this.text, this.xPos + 4, this.yPos + (this.height - 8) / 2, 0xFF5555);
        }
    }

    public void setMaxStringLength(int var1) {
        this.maxStringLength = var1;
    }
}

