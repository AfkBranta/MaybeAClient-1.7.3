/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiSlot {
    private final Minecraft mc;
    protected final int width;
    private final int height;
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;
    protected final int posZ;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    private float initialClickY = -2.0f;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement = -1;
    private long lastClicked = 0L;
    private boolean field_25123_p = true;

    public GuiSlot(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
        this.mc = var1;
        this.width = var2;
        this.height = var3;
        this.top = var4;
        this.bottom = var5;
        this.posZ = var6;
        this.left = 0;
        this.right = var2;
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2);

    protected abstract boolean isSelected(int var1);

    protected abstract int getContentHeight();

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5);

    public void registerScrollButtons(List var1, int scrollUp, int scrollDown) {
        this.scrollUpButtonID = scrollUp;
        this.scrollDownButtonID = scrollDown;
    }

    private void bindAmountScrolled() {
        int var1 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (var1 < 0) {
            var1 /= 2;
        }
        if (this.amountScrolled < 0.0f) {
            this.amountScrolled = 0.0f;
        }
        if (this.amountScrolled > (float)var1) {
            this.amountScrolled = var1;
        }
    }

    public void actionPerformed(GuiButton var1) {
        if (var1.enabled) {
            if (var1.id == this.scrollUpButtonID) {
                this.amountScrolled -= (float)(this.posZ * 2 / 3);
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            } else if (var1.id == this.scrollDownButtonID) {
                this.amountScrolled += (float)(this.posZ * 2 / 3);
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
        }
    }

    public void drawScreen(int var1, int var2, float var3) {
        int var12;
        int var11;
        int var18;
        int var9;
        this.drawBackground();
        int var4 = this.getSize();
        int var5 = this.width / 2 + 124;
        int var6 = var5 + 6;
        if (Mouse.isButtonDown((int)0)) {
            if (this.initialClickY == -1.0f) {
                if (var2 >= this.top && var2 <= this.bottom) {
                    int var7 = this.width / 2 - 110;
                    int var8 = this.width / 2 + 110;
                    var9 = (var2 - this.top + (int)this.amountScrolled - 2) / this.posZ;
                    if (var1 >= var7 && var1 <= var8 && var9 >= 0 && var9 < var4) {
                        boolean var10 = var9 == this.selectedElement && System.currentTimeMillis() - this.lastClicked < 250L;
                        this.elementClicked(var9, var10);
                        this.selectedElement = var9;
                        this.lastClicked = System.currentTimeMillis();
                    }
                    if (var1 >= var5 && var1 <= var6 && this.getContentHeight() > 0) {
                        this.scrollMultiplier = -1.0f;
                        var18 = this.getContentHeight() - (this.bottom - this.top - 4);
                        if (var18 < 1) {
                            var18 = 1;
                        }
                        if ((var11 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight()) < 32) {
                            var11 = 32;
                        }
                        if (var11 > this.bottom - this.top - 8) {
                            var11 = this.bottom - this.top - 8;
                        }
                        this.scrollMultiplier /= (float)(this.bottom - this.top - var11) / (float)var18;
                    } else {
                        this.scrollMultiplier = 1.0f;
                    }
                    this.initialClickY = var2;
                } else {
                    this.initialClickY = -2.0f;
                }
            } else if (this.initialClickY >= 0.0f) {
                this.amountScrolled -= ((float)var2 - this.initialClickY) * this.scrollMultiplier;
                this.initialClickY = var2;
            }
        } else {
            this.initialClickY = -1.0f;
        }
        this.bindAmountScrolled();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        Tessellator var15 = Tessellator.instance;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var16 = 32.0f;
        var15.startDrawingQuads();
        var15.setColorOpaque_I(0x202020);
        var15.addVertexWithUV(this.left, this.bottom, 0.0, (float)this.left / var16, (float)(this.bottom + (int)this.amountScrolled) / var16);
        var15.addVertexWithUV(this.right, this.bottom, 0.0, (float)this.right / var16, (float)(this.bottom + (int)this.amountScrolled) / var16);
        var15.addVertexWithUV(this.right, this.top, 0.0, (float)this.right / var16, (float)(this.top + (int)this.amountScrolled) / var16);
        var15.addVertexWithUV(this.left, this.top, 0.0, (float)this.left / var16, (float)(this.top + (int)this.amountScrolled) / var16);
        var15.draw();
        var9 = 0;
        while (var9 < var4) {
            var18 = this.width / 2 - 92 - 16;
            var11 = this.top + 4 + var9 * this.posZ - (int)this.amountScrolled;
            var12 = this.posZ - 4;
            if (this.field_25123_p && this.isSelected(var9)) {
                int var13 = this.width / 2 - 110;
                int var14 = this.width / 2 + 110;
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3553);
                var15.startDrawingQuads();
                var15.setColorOpaque_I(0x808080);
                var15.addVertexWithUV(var13, var11 + var12 + 2, 0.0, 0.0, 1.0);
                var15.addVertexWithUV(var14, var11 + var12 + 2, 0.0, 1.0, 1.0);
                var15.addVertexWithUV(var14, var11 - 2, 0.0, 1.0, 0.0);
                var15.addVertexWithUV(var13, var11 - 2, 0.0, 0.0, 0.0);
                var15.setColorOpaque_I(0);
                var15.addVertexWithUV(var13 + 1, var11 + var12 + 1, 0.0, 0.0, 1.0);
                var15.addVertexWithUV(var14 - 1, var11 + var12 + 1, 0.0, 1.0, 1.0);
                var15.addVertexWithUV(var14 - 1, var11 - 1, 0.0, 1.0, 0.0);
                var15.addVertexWithUV(var13 + 1, var11 - 1, 0.0, 0.0, 0.0);
                var15.draw();
                GL11.glEnable((int)3553);
            }
            this.drawSlot(var9, var18, var11, var12, var15);
            ++var9;
        }
        int var17 = 4;
        this.overlayBackground(0, this.top, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        var15.startDrawingQuads();
        var15.setColorRGBA_I(0, 0);
        var15.addVertexWithUV(this.left, this.top + var17, 0.0, 0.0, 1.0);
        var15.addVertexWithUV(this.right, this.top + var17, 0.0, 1.0, 1.0);
        var15.setColorRGBA_I(0, 255);
        var15.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
        var15.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
        var15.draw();
        var15.startDrawingQuads();
        var15.setColorRGBA_I(0, 255);
        var15.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
        var15.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
        var15.setColorRGBA_I(0, 0);
        var15.addVertexWithUV(this.right, this.bottom - var17, 0.0, 1.0, 0.0);
        var15.addVertexWithUV(this.left, this.bottom - var17, 0.0, 0.0, 0.0);
        var15.draw();
        var18 = this.getContentHeight() - (this.bottom - this.top - 4);
        if (var18 > 0) {
            var11 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (var11 < 32) {
                var11 = 32;
            }
            if (var11 > this.bottom - this.top - 8) {
                var11 = this.bottom - this.top - 8;
            }
            if ((var12 = (int)this.amountScrolled * (this.bottom - this.top - var11) / var18 + this.top) < this.top) {
                var12 = this.top;
            }
            var15.startDrawingQuads();
            var15.setColorRGBA_I(0, 255);
            var15.addVertexWithUV(var5, this.bottom, 0.0, 0.0, 1.0);
            var15.addVertexWithUV(var6, this.bottom, 0.0, 1.0, 1.0);
            var15.addVertexWithUV(var6, this.top, 0.0, 1.0, 0.0);
            var15.addVertexWithUV(var5, this.top, 0.0, 0.0, 0.0);
            var15.draw();
            var15.startDrawingQuads();
            var15.setColorRGBA_I(0x808080, 255);
            var15.addVertexWithUV(var5, var12 + var11, 0.0, 0.0, 1.0);
            var15.addVertexWithUV(var6, var12 + var11, 0.0, 1.0, 1.0);
            var15.addVertexWithUV(var6, var12, 0.0, 1.0, 0.0);
            var15.addVertexWithUV(var5, var12, 0.0, 0.0, 0.0);
            var15.draw();
            var15.startDrawingQuads();
            var15.setColorRGBA_I(0xC0C0C0, 255);
            var15.addVertexWithUV(var5, var12 + var11 - 1, 0.0, 0.0, 1.0);
            var15.addVertexWithUV(var6 - 1, var12 + var11 - 1, 0.0, 1.0, 1.0);
            var15.addVertexWithUV(var6 - 1, var12, 0.0, 1.0, 0.0);
            var15.addVertexWithUV(var5, var12, 0.0, 0.0, 0.0);
            var15.draw();
        }
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3008);
        GL11.glDisable((int)3042);
    }

    private void overlayBackground(int var1, int var2, int var3, int var4) {
        Tessellator var5 = Tessellator.instance;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/background.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(0x404040, var4);
        var5.addVertexWithUV(0.0, var2, 0.0, 0.0, (float)var2 / var6);
        var5.addVertexWithUV(this.width, var2, 0.0, (float)this.width / var6, (float)var2 / var6);
        var5.setColorRGBA_I(0x404040, var3);
        var5.addVertexWithUV(this.width, var1, 0.0, (float)this.width / var6, (float)var1 / var6);
        var5.addVertexWithUV(0.0, var1, 0.0, 0.0, (float)var1 / var6);
        var5.draw();
    }
}

