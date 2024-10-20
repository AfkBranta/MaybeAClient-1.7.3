/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class GuiAchievement
extends Gui {
    private Minecraft field_25082_a;
    private int field_25081_b;
    private int field_25086_c;
    private String field_25085_d;
    private String field_25084_e;
    private long field_25083_f;

    public GuiAchievement(Minecraft var1) {
        this.field_25082_a = var1;
    }

    private void func_25079_b() {
        GL11.glViewport((int)0, (int)0, (int)this.field_25082_a.displayWidth, (int)this.field_25082_a.displayHeight);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        this.field_25081_b = this.field_25082_a.displayWidth;
        this.field_25086_c = this.field_25082_a.displayHeight;
        ScaledResolution var1 = new ScaledResolution(this.field_25082_a.gameSettings, this.field_25082_a.displayWidth, this.field_25082_a.displayHeight);
        this.field_25081_b = var1.getScaledWidth();
        this.field_25086_c = var1.getScaledHeight();
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.field_25081_b, (double)this.field_25086_c, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public void func_25080_a() {
        if (this.field_25083_f != 0L) {
            double var1 = (double)(System.currentTimeMillis() - this.field_25083_f) / 3000.0;
            if (var1 >= 0.0 && var1 <= 1.0) {
                this.func_25079_b();
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                double var3 = var1 * 2.0;
                if (var3 > 1.0) {
                    var3 = 2.0 - var3;
                }
                var3 *= 4.0;
                if ((var3 = 1.0 - var3) < 0.0) {
                    var3 = 0.0;
                }
                var3 *= var3;
                var3 *= var3;
                int var5 = this.field_25081_b - 160;
                int var6 = 0 - (int)(var3 * 36.0);
                int var7 = this.field_25082_a.renderEngine.getTexture("/achievement/bg.png");
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
                GL11.glBindTexture((int)3553, (int)var7);
                this.drawTexturedModalRect(var5, var6, 0, 188, 160, 32);
                this.field_25082_a.fontRenderer.drawString(this.field_25085_d, var5 + 30, var6 + 7, -256);
                this.field_25082_a.fontRenderer.drawString(this.field_25084_e, var5 + 30, var6 + 18, -1);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2929);
            } else {
                this.field_25083_f = 0L;
            }
        }
    }
}

