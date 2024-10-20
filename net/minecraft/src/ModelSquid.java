/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSquid
extends ModelBase {
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles = new ModelRenderer[8];

    public ModelSquid() {
        int var1 = -16;
        this.squidBody = new ModelRenderer(0, 0);
        this.squidBody.addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        ModelRenderer var10000 = this.squidBody;
        var10000.offsetY += (float)(24 + var1);
        int var2 = 0;
        while (var2 < this.squidTentacles.length) {
            this.squidTentacles[var2] = new ModelRenderer(48, 0);
            double var3 = (double)var2 * Math.PI * 2.0 / (double)this.squidTentacles.length;
            float var5 = (float)Math.cos(var3) * 5.0f;
            float var6 = (float)Math.sin(var3) * 5.0f;
            this.squidTentacles[var2].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[var2].offsetX = var5;
            this.squidTentacles[var2].offsetZ = var6;
            this.squidTentacles[var2].offsetY = 31 + var1;
            var3 = (double)var2 * Math.PI * -2.0 / (double)this.squidTentacles.length + 1.5707963267948966;
            this.squidTentacles[var2].rotateAngleY = (float)var3;
            ++var2;
        }
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        int var7 = 0;
        while (var7 < this.squidTentacles.length) {
            this.squidTentacles[var7].rotateAngleX = var3;
            ++var7;
        }
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.squidBody.render(var6);
        int var7 = 0;
        while (var7 < this.squidTentacles.length) {
            this.squidTentacles[var7].render(var6);
            ++var7;
        }
    }
}

