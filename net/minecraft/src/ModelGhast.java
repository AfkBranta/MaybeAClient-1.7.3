package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelGhast
extends ModelBase {
    ModelRenderer body;
    ModelRenderer[] tentacles = new ModelRenderer[9];

    public ModelGhast() {
        int var1 = -16;
        this.body = new ModelRenderer(0, 0);
        this.body.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        ModelRenderer var10000 = this.body;
        var10000.offsetY += (float)(24 + var1);
        Random var2 = new Random(1660L);
        int var3 = 0;
        while (var3 < this.tentacles.length) {
            this.tentacles[var3] = new ModelRenderer(0, 0);
            float var4 = (((float)(var3 % 3) - (float)(var3 / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float var5 = ((float)(var3 / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int var6 = var2.nextInt(7) + 8;
            this.tentacles[var3].addBox(-1.0f, 0.0f, -1.0f, 2, var6, 2);
            this.tentacles[var3].offsetX = var4;
            this.tentacles[var3].offsetZ = var5;
            this.tentacles[var3].offsetY = 31 + var1;
            ++var3;
        }
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        int var7 = 0;
        while (var7 < this.tentacles.length) {
            this.tentacles[var7].rotateAngleX = 0.2f * MathHelper.sin(var3 * 0.3f + (float)var7) + 0.4f;
            ++var7;
        }
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.body.render(var6);
        int var7 = 0;
        while (var7 < this.tentacles.length) {
            this.tentacles[var7].render(var6);
            ++var7;
        }
    }
}

