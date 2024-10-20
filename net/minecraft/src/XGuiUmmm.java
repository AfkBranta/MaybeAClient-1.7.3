/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Gui;
import net.minecraft.src.XErrr;
import org.lwjgl.opengl.GL11;

public class XGuiUmmm
extends Gui {
    private List field_25090_a = new ArrayList();
    private Minecraft field_25089_b;

    public XGuiUmmm(Minecraft var1) {
        this.field_25089_b = var1;
    }

    public void func_25088_a() {
        int var1 = 0;
        while (var1 < this.field_25090_a.size()) {
            XErrr var2 = (XErrr)this.field_25090_a.get(var1);
            var2.func_25127_a();
            var2.func_25125_a(this);
            if (var2.field_25139_h) {
                this.field_25090_a.remove(var1--);
            }
            ++var1;
        }
    }

    public void func_25087_a(float var1) {
        this.field_25089_b.renderEngine.bindTexture(this.field_25089_b.renderEngine.getTexture("/gui/particles.png"));
        int var2 = 0;
        while (var2 < this.field_25090_a.size()) {
            XErrr var3 = (XErrr)this.field_25090_a.get(var2);
            int var4 = (int)(var3.field_25144_c + (var3.field_25146_a - var3.field_25144_c) * (double)var1 - 4.0);
            int var5 = (int)(var3.field_25143_d + (var3.field_25145_b - var3.field_25143_d) * (double)var1 - 4.0);
            float var6 = (float)(var3.field_25129_r + (var3.field_25133_n - var3.field_25129_r) * (double)var1);
            float var7 = (float)(var3.field_25132_o + (var3.field_25136_k - var3.field_25132_o) * (double)var1);
            float var8 = (float)(var3.field_25131_p + (var3.field_25135_l - var3.field_25131_p) * (double)var1);
            float var9 = (float)(var3.field_25130_q + (var3.field_25134_m - var3.field_25130_q) * (double)var1);
            GL11.glColor4f((float)var7, (float)var8, (float)var9, (float)var6);
            this.drawTexturedModalRect(var4, var5, 40, 0, 8, 8);
            ++var2;
        }
    }
}

