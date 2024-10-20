/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelBoat
extends ModelBase {
    public ModelRenderer[] boatSides = new ModelRenderer[5];

    public ModelBoat() {
        this.boatSides[0] = new ModelRenderer(0, 8);
        this.boatSides[1] = new ModelRenderer(0, 0);
        this.boatSides[2] = new ModelRenderer(0, 0);
        this.boatSides[3] = new ModelRenderer(0, 0);
        this.boatSides[4] = new ModelRenderer(0, 0);
        int var1 = 24;
        int var2 = 6;
        int var3 = 20;
        int var4 = 4;
        this.boatSides[0].addBox(-var1 / 2, -var3 / 2 + 2, -3.0f, var1, var3 - 4, 4, 0.0f);
        this.boatSides[0].setPosition(0.0f, 0 + var4, 0.0f);
        this.boatSides[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[1].setPosition(-var1 / 2 + 1, 0 + var4, 0.0f);
        this.boatSides[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[2].setPosition(var1 / 2 - 1, 0 + var4, 0.0f);
        this.boatSides[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[3].setPosition(0.0f, 0 + var4, -var3 / 2 + 1);
        this.boatSides[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0f, var1 - 4, var2, 2, 0.0f);
        this.boatSides[4].setPosition(0.0f, 0 + var4, var3 / 2 - 1);
        this.boatSides[0].rotateAngleX = 1.5707964f;
        this.boatSides[1].rotateAngleY = 4.712389f;
        this.boatSides[2].rotateAngleY = 1.5707964f;
        this.boatSides[3].rotateAngleY = (float)Math.PI;
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        int var7 = 0;
        while (var7 < 5) {
            this.boatSides[var7].render(var6);
            ++var7;
        }
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
    }
}

