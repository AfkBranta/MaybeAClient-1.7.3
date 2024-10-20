/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class Gui {
    protected float zLevel = 0.0f;

    protected void drawRect(int var1, int var2, int var3, int var4, int var5) {
        int var6;
        if (var1 < var3) {
            var6 = var1;
            var1 = var3;
            var3 = var6;
        }
        if (var2 < var4) {
            var6 = var2;
            var2 = var4;
            var4 = var6;
        }
        float var11 = (float)(var5 >> 24 & 0xFF) / 255.0f;
        float var7 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var8 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var9 = (float)(var5 & 0xFF) / 255.0f;
        Tessellator var10 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)var7, (float)var8, (float)var9, (float)var11);
        var10.startDrawingQuads();
        var10.addVertex(var1, var4, 0.0);
        var10.addVertex(var3, var4, 0.0);
        var10.addVertex(var3, var2, 0.0);
        var10.addVertex(var1, var2, 0.0);
        var10.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    protected void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6) {
        float var7 = (float)(var5 >> 24 & 0xFF) / 255.0f;
        float var8 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var9 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var10 = (float)(var5 & 0xFF) / 255.0f;
        float var11 = (float)(var6 >> 24 & 0xFF) / 255.0f;
        float var12 = (float)(var6 >> 16 & 0xFF) / 255.0f;
        float var13 = (float)(var6 >> 8 & 0xFF) / 255.0f;
        float var14 = (float)(var6 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(var3, var2, 0.0);
        var15.addVertex(var1, var2, 0.0);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(var1, var4, 0.0);
        var15.addVertex(var3, var4, 0.0);
        var15.draw();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public void drawCenteredString(FontRenderer var1, String var2, int var3, int var4, int var5) {
        var1.drawStringWithShadow(var2, var3 - var1.getStringWidth(var2) / 2, var4, var5);
    }

    public void drawString(FontRenderer var1, String var2, int var3, int var4, int var5) {
        var1.drawStringWithShadow(var2, var3, var4, var5);
    }

    public void drawTexturedModalRect(int var1, int var2, int var3, int var4, int var5, int var6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(var1 + 0, var2 + var6, this.zLevel, (float)(var3 + 0) * var7, (float)(var4 + var6) * var8);
        var9.addVertexWithUV(var1 + var5, var2 + var6, this.zLevel, (float)(var3 + var5) * var7, (float)(var4 + var6) * var8);
        var9.addVertexWithUV(var1 + var5, var2 + 0, this.zLevel, (float)(var3 + var5) * var7, (float)(var4 + 0) * var8);
        var9.addVertexWithUV(var1 + 0, var2 + 0, this.zLevel, (float)(var3 + 0) * var7, (float)(var4 + 0) * var8);
        var9.draw();
    }
}

