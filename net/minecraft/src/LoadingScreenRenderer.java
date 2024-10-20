/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class LoadingScreenRenderer
implements IProgressUpdate {
    private String field_1004_a = "";
    private Minecraft mc;
    private String field_1007_c = "";
    private long field_1006_d = System.currentTimeMillis();
    private boolean field_1005_e = false;

    public LoadingScreenRenderer(Minecraft var1) {
        this.mc = var1;
    }

    public void printText(String var1) {
        this.field_1005_e = false;
        this.func_597_c(var1);
    }

    @Override
    public void func_594_b(String var1) {
        this.field_1005_e = true;
        this.func_597_c(this.field_1007_c);
    }

    public void func_597_c(String var1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            this.field_1007_c = var1;
            ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            GL11.glClear((int)256);
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            GL11.glOrtho((double)0.0, (double)var2.field_25121_a, (double)var2.field_25120_b, (double)0.0, (double)100.0, (double)300.0);
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
        }
    }

    @Override
    public void displayLoadingString(String var1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            this.field_1006_d = 0L;
            this.field_1004_a = var1;
            this.setLoadingProgress(-1);
            this.field_1006_d = 0L;
        }
    }

    @Override
    public void setLoadingProgress(int var1) {
        if (!this.mc.running) {
            if (!this.field_1005_e) {
                throw new MinecraftError();
            }
        } else {
            long var2 = System.currentTimeMillis();
            if (var2 - this.field_1006_d >= 20L) {
                this.field_1006_d = var2;
                ScaledResolution var4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
                int var5 = var4.getScaledWidth();
                int var6 = var4.getScaledHeight();
                GL11.glClear((int)256);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                GL11.glOrtho((double)0.0, (double)var4.field_25121_a, (double)var4.field_25120_b, (double)0.0, (double)100.0, (double)300.0);
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-200.0f);
                GL11.glClear((int)16640);
                Tessellator var7 = Tessellator.instance;
                int var8 = this.mc.renderEngine.getTexture("/gui/background.png");
                GL11.glBindTexture((int)3553, (int)var8);
                float var9 = 32.0f;
                var7.startDrawingQuads();
                var7.setColorOpaque_I(0x404040);
                var7.addVertexWithUV(0.0, var6, 0.0, 0.0, (float)var6 / var9);
                var7.addVertexWithUV(var5, var6, 0.0, (float)var5 / var9, (float)var6 / var9);
                var7.addVertexWithUV(var5, 0.0, 0.0, (float)var5 / var9, 0.0);
                var7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                var7.draw();
                if (var1 >= 0) {
                    int var10 = 100;
                    int var11 = 2;
                    int var12 = var5 / 2 - var10 / 2;
                    int var13 = var6 / 2 + 16;
                    GL11.glDisable((int)3553);
                    var7.startDrawingQuads();
                    var7.setColorOpaque_I(0x808080);
                    var7.addVertex(var12, var13, 0.0);
                    var7.addVertex(var12, var13 + var11, 0.0);
                    var7.addVertex(var12 + var10, var13 + var11, 0.0);
                    var7.addVertex(var12 + var10, var13, 0.0);
                    var7.setColorOpaque_I(0x80FF80);
                    var7.addVertex(var12, var13, 0.0);
                    var7.addVertex(var12, var13 + var11, 0.0);
                    var7.addVertex(var12 + var1, var13 + var11, 0.0);
                    var7.addVertex(var12 + var1, var13, 0.0);
                    var7.draw();
                    GL11.glEnable((int)3553);
                }
                this.mc.fontRenderer.drawStringWithShadow(this.field_1007_c, (var5 - this.mc.fontRenderer.getStringWidth(this.field_1007_c)) / 2, var6 / 2 - 4 - 16, 0xFFFFFF);
                this.mc.fontRenderer.drawStringWithShadow(this.field_1004_a, (var5 - this.mc.fontRenderer.getStringWidth(this.field_1004_a)) / 2, var6 / 2 - 4 + 8, 0xFFFFFF);
                Display.update();
                try {
                    Thread.yield();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }
}

