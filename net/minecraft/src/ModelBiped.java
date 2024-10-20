package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelBiped
extends ModelBase {
    public ModelRenderer bipedHead;
    public ModelRenderer bipedHeadwear;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedEars;
    public ModelRenderer bipedCloak = new ModelRenderer(0, 0);
    public boolean field_1279_h = false;
    public boolean field_1278_i = false;
    public boolean isSneak = false;

    public ModelBiped() {
        this(0.0f);
    }

    public ModelBiped(float var1) {
        this(var1, 0.0f);
    }

    public ModelBiped(float var1, float var2) {
        this.bipedCloak.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, var1);
        this.bipedEars = new ModelRenderer(24, 0);
        this.bipedEars.addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, var1);
        this.bipedHead = new ModelRenderer(0, 0);
        this.bipedHead.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1);
        this.bipedHead.setPosition(0.0f, 0.0f + var2, 0.0f);
        this.bipedHeadwear = new ModelRenderer(32, 0);
        this.bipedHeadwear.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, var1 + 0.5f);
        this.bipedHeadwear.setPosition(0.0f, 0.0f + var2, 0.0f);
        this.bipedBody = new ModelRenderer(16, 16);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, var1);
        this.bipedBody.setPosition(0.0f, 0.0f + var2, 0.0f);
        this.bipedRightArm = new ModelRenderer(40, 16);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, var1);
        this.bipedRightArm.setPosition(-5.0f, 2.0f + var2, 0.0f);
        this.bipedLeftArm = new ModelRenderer(40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, var1);
        this.bipedLeftArm.setPosition(5.0f, 2.0f + var2, 0.0f);
        this.bipedRightLeg = new ModelRenderer(0, 16);
        this.bipedRightLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.bipedRightLeg.setPosition(-2.0f, 12.0f + var2, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, var1);
        this.bipedLeftLeg.setPosition(2.0f, 12.0f + var2, 0.0f);
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.bipedHead.render(var6);
        this.bipedBody.render(var6);
        this.bipedRightArm.render(var6);
        this.bipedLeftArm.render(var6);
        this.bipedRightLeg.render(var6);
        this.bipedLeftLeg.render(var6);
        this.bipedHeadwear.render(var6);
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        ModelRenderer var10000;
        this.bipedHead.rotateAngleY = var4 / 57.295776f;
        this.bipedHead.rotateAngleX = var5 / 57.295776f;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 2.0f * var2 * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(var1 * 0.6662f) * 2.0f * var2 * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662f) * 1.4f * var2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 1.4f * var2;
        this.bipedRightLeg.rotateAngleY = 0.0f;
        this.bipedLeftLeg.rotateAngleY = 0.0f;
        if (this.isRiding) {
            var10000 = this.bipedRightArm;
            var10000.rotateAngleX += -0.62831855f;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += -0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if (this.field_1279_h) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f;
        }
        if (this.field_1278_i) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedLeftArm.rotateAngleY = 0.0f;
        if (this.onGround > -9990.0f) {
            float var7 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI * 2.0f) * 0.2f;
            this.bipedRightArm.offsetZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedRightArm.offsetX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.offsetZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0f;
            this.bipedLeftArm.offsetX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0f;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleY += this.bipedBody.rotateAngleY;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleY += this.bipedBody.rotateAngleY;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += this.bipedBody.rotateAngleY;
            var7 = 1.0f - this.onGround;
            var7 *= var7;
            var7 *= var7;
            var7 = 1.0f - var7;
            float var8 = MathHelper.sin(var7 * (float)Math.PI);
            float var9 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleX = (float)((double)var10000.rotateAngleX - ((double)var8 * 1.2 + (double)var9));
            var10000 = this.bipedRightArm;
            var10000.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4f;
        }
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.5f;
            var10000 = this.bipedRightLeg;
            var10000.rotateAngleX -= 0.0f;
            var10000 = this.bipedLeftLeg;
            var10000.rotateAngleX -= 0.0f;
            var10000 = this.bipedRightArm;
            var10000.rotateAngleX += 0.4f;
            var10000 = this.bipedLeftArm;
            var10000.rotateAngleX += 0.4f;
            this.bipedRightLeg.offsetZ = 4.0f;
            this.bipedLeftLeg.offsetZ = 4.0f;
            this.bipedRightLeg.offsetY = 9.0f;
            this.bipedLeftLeg.offsetY = 9.0f;
            this.bipedHead.offsetY = 1.0f;
        } else {
            this.bipedBody.rotateAngleX = 0.0f;
            this.bipedRightLeg.offsetZ = 0.0f;
            this.bipedLeftLeg.offsetZ = 0.0f;
            this.bipedRightLeg.offsetY = 12.0f;
            this.bipedLeftLeg.offsetY = 12.0f;
            this.bipedHead.offsetY = 0.0f;
        }
        var10000 = this.bipedRightArm;
        var10000.rotateAngleZ += MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        var10000 = this.bipedLeftArm;
        var10000.rotateAngleZ -= MathHelper.cos(var3 * 0.09f) * 0.05f + 0.05f;
        var10000 = this.bipedRightArm;
        var10000.rotateAngleX += MathHelper.sin(var3 * 0.067f) * 0.05f;
        var10000 = this.bipedLeftArm;
        var10000.rotateAngleX -= MathHelper.sin(var3 * 0.067f) * 0.05f;
    }

    public void renderEars(float var1) {
        this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedEars.offsetX = 0.0f;
        this.bipedEars.offsetY = 0.0f;
        this.bipedEars.render(var1);
    }

    public void renderCloak(float var1) {
        this.bipedCloak.render(var1);
    }
}

