/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelMinecart
extends ModelBase {
    public ModelRenderer[] sideModels = new ModelRenderer[7];

    public ModelMinecart() {
        this.sideModels[0] = new ModelRenderer(0, 10);
        this.sideModels[1] = new ModelRenderer(0, 0);
        this.sideModels[2] = new ModelRenderer(0, 0);
        this.sideModels[3] = new ModelRenderer(0, 0);
        this.sideModels[4] = new ModelRenderer(0, 0);
        this.sideModels[5] = new ModelRenderer(44, 10);
        int var1 = 20;
        int var2 = 8;
        int var3 = 16;
        int var4 = 4;
        this.sideModels[0].addBox(-var1 / 2, -var3 / 2, -1.0f, var1, var3, 2, 0.0f);
        this.sideModels[0].setPosition(0.0f, 0 + var4, 0.0f);
        this.sideModels[5].addBox(-var1 / 2 + 1, -var3 / 2 + 1, -1.0f, var1 - 2, var3 - 2, 1, 0.0f);
        this.sideModels[5].setPosition(0.0f, 0 + var4, 0.0f);
        this.sideModels[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[1].setPosition(-var1 / 2 + 1, 0 + var4, 0.0f);
        this.sideModels[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[2].setPosition(var1 / 2 - 1, 0 + var4, 0.0f);
        this.sideModels[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[3].setPosition(0.0f, 0 + var4, -var3 / 2 + 1);
        this.sideModels[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.sideModels[4].setPosition(0.0f, 0 + var4, var3 / 2 - 1);
        this.sideModels[0].rotateAngleX = 1.5707964f;
        this.sideModels[1].rotateAngleY = 4.712389f;
        this.sideModels[2].rotateAngleY = 1.5707964f;
        this.sideModels[3].rotateAngleY = (float)Math.PI;
        this.sideModels[5].rotateAngleX = -1.5707964f;
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.sideModels[5].offsetY = 4.0f - var3;
        int var7 = 0;
        while (var7 < 6) {
            this.sideModels[var7].render(var6);
            ++var7;
        }
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
    }
}

