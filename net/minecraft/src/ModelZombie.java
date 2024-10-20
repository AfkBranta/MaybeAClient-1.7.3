package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.ModelRenderer;

public class ModelZombie
extends ModelBiped {
    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6);
        float var7 = MathHelper.sin(this.onGround * (float)Math.PI);
        float var8 = MathHelper.sin((1.0f - (1.0f - this.onGround) * (1.0f - this.onGround)) * (float)Math.PI);
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightArm.rotateAngleY = -(0.1f - var7 * 0.6f);
        this.bipedLeftArm.rotateAngleY = 0.1f - var7 * 0.6f;
        this.bipedRightArm.rotateAngleX = -1.5707964f;
        this.bipedLeftArm.rotateAngleX = -1.5707964f;
        ModelRenderer var10000 = this.bipedRightArm;
        var10000.rotateAngleX -= var7 * 1.2f - var8 * 0.4f;
        var10000 = this.bipedLeftArm;
        var10000.rotateAngleX -= var7 * 1.2f - var8 * 0.4f;
        var10000 = this.bipedRightArm;
        var10000.rotateAngleZ += MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        var10000 = this.bipedLeftArm;
        var10000.rotateAngleZ -= MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        var10000 = this.bipedRightArm;
        var10000.rotateAngleX += MathHelper.sin(var3 * 0.067f) * 0.05f;
        var10000 = this.bipedLeftArm;
        var10000.rotateAngleX -= MathHelper.sin(var3 * 0.067f) * 0.05f;
    }
}

