/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSpider
extends ModelBase {
    public ModelRenderer spiderHead;
    public ModelRenderer spiderNeck;
    public ModelRenderer spiderBody;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;

    public ModelSpider() {
        float var1 = 0.0f;
        int var2 = 15;
        this.spiderHead = new ModelRenderer(32, 4);
        this.spiderHead.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, var1);
        this.spiderHead.setPosition(0.0f, 0 + var2, -3.0f);
        this.spiderNeck = new ModelRenderer(0, 0);
        this.spiderNeck.addBox(-3.0f, -3.0f, -3.0f, 6, 6, 6, var1);
        this.spiderNeck.setPosition(0.0f, var2, 0.0f);
        this.spiderBody = new ModelRenderer(0, 12);
        this.spiderBody.addBox(-5.0f, -4.0f, -6.0f, 10, 8, 12, var1);
        this.spiderBody.setPosition(0.0f, 0 + var2, 9.0f);
        this.spiderLeg1 = new ModelRenderer(18, 0);
        this.spiderLeg1.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg1.setPosition(-4.0f, 0 + var2, 2.0f);
        this.spiderLeg2 = new ModelRenderer(18, 0);
        this.spiderLeg2.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg2.setPosition(4.0f, 0 + var2, 2.0f);
        this.spiderLeg3 = new ModelRenderer(18, 0);
        this.spiderLeg3.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg3.setPosition(-4.0f, 0 + var2, 1.0f);
        this.spiderLeg4 = new ModelRenderer(18, 0);
        this.spiderLeg4.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg4.setPosition(4.0f, 0 + var2, 1.0f);
        this.spiderLeg5 = new ModelRenderer(18, 0);
        this.spiderLeg5.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg5.setPosition(-4.0f, 0 + var2, 0.0f);
        this.spiderLeg6 = new ModelRenderer(18, 0);
        this.spiderLeg6.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg6.setPosition(4.0f, 0 + var2, 0.0f);
        this.spiderLeg7 = new ModelRenderer(18, 0);
        this.spiderLeg7.addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg7.setPosition(-4.0f, 0 + var2, -1.0f);
        this.spiderLeg8 = new ModelRenderer(18, 0);
        this.spiderLeg8.addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, var1);
        this.spiderLeg8.setPosition(4.0f, 0 + var2, -1.0f);
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.spiderHead.render(var6);
        this.spiderNeck.render(var6);
        this.spiderBody.render(var6);
        this.spiderLeg1.render(var6);
        this.spiderLeg2.render(var6);
        this.spiderLeg3.render(var6);
        this.spiderLeg4.render(var6);
        this.spiderLeg5.render(var6);
        this.spiderLeg6.render(var6);
        this.spiderLeg7.render(var6);
        this.spiderLeg8.render(var6);
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.spiderHead.rotateAngleY = var4 / 57.295776f;
        this.spiderHead.rotateAngleX = var5 / 57.295776f;
        float var7 = 0.7853982f;
        this.spiderLeg1.rotateAngleZ = -var7;
        this.spiderLeg2.rotateAngleZ = var7;
        this.spiderLeg3.rotateAngleZ = -var7 * 0.74f;
        this.spiderLeg4.rotateAngleZ = var7 * 0.74f;
        this.spiderLeg5.rotateAngleZ = -var7 * 0.74f;
        this.spiderLeg6.rotateAngleZ = var7 * 0.74f;
        this.spiderLeg7.rotateAngleZ = -var7;
        this.spiderLeg8.rotateAngleZ = var7;
        float var8 = -0.0f;
        float var9 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = var9 * 2.0f + var8;
        this.spiderLeg2.rotateAngleY = -var9 * 2.0f - var8;
        this.spiderLeg3.rotateAngleY = var9 * 1.0f + var8;
        this.spiderLeg4.rotateAngleY = -var9 * 1.0f - var8;
        this.spiderLeg5.rotateAngleY = -var9 * 1.0f + var8;
        this.spiderLeg6.rotateAngleY = var9 * 1.0f - var8;
        this.spiderLeg7.rotateAngleY = -var9 * 2.0f + var8;
        this.spiderLeg8.rotateAngleY = var9 * 2.0f - var8;
        float var10 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + 0.0f) * 0.4f) * var2;
        float var11 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * var2;
        float var12 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * var2;
        float var13 = -(MathHelper.cos(var1 * 0.6662f * 2.0f + 4.712389f) * 0.4f) * var2;
        float var14 = Math.abs(MathHelper.sin(var1 * 0.6662f + 0.0f) * 0.4f) * var2;
        float var15 = Math.abs(MathHelper.sin(var1 * 0.6662f + (float)Math.PI) * 0.4f) * var2;
        float var16 = Math.abs(MathHelper.sin(var1 * 0.6662f + 1.5707964f) * 0.4f) * var2;
        float var17 = Math.abs(MathHelper.sin(var1 * 0.6662f + 4.712389f) * 0.4f) * var2;
        ModelRenderer var10000 = this.spiderLeg1;
        var10000.rotateAngleY += var10;
        var10000 = this.spiderLeg2;
        var10000.rotateAngleY += -var10;
        var10000 = this.spiderLeg3;
        var10000.rotateAngleY += var11;
        var10000 = this.spiderLeg4;
        var10000.rotateAngleY += -var11;
        var10000 = this.spiderLeg5;
        var10000.rotateAngleY += var12;
        var10000 = this.spiderLeg6;
        var10000.rotateAngleY += -var12;
        var10000 = this.spiderLeg7;
        var10000.rotateAngleY += var13;
        var10000 = this.spiderLeg8;
        var10000.rotateAngleY += -var13;
        var10000 = this.spiderLeg1;
        var10000.rotateAngleZ += var14;
        var10000 = this.spiderLeg2;
        var10000.rotateAngleZ += -var14;
        var10000 = this.spiderLeg3;
        var10000.rotateAngleZ += var15;
        var10000 = this.spiderLeg4;
        var10000.rotateAngleZ += -var15;
        var10000 = this.spiderLeg5;
        var10000.rotateAngleZ += var16;
        var10000 = this.spiderLeg6;
        var10000.rotateAngleZ += -var16;
        var10000 = this.spiderLeg7;
        var10000.rotateAngleZ += var17;
        var10000 = this.spiderLeg8;
        var10000.rotateAngleZ += -var17;
    }
}

