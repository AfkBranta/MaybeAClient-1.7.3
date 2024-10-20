package net.minecraft.src;

import net.minecraft.src.ModelQuadraped;
import net.minecraft.src.ModelRenderer;

public class ModelCow
extends ModelQuadraped {
    ModelRenderer udders;
    ModelRenderer horn1;
    ModelRenderer horn2;

    public ModelCow() {
        super(12, 0.0f);
        this.head = new ModelRenderer(0, 0);
        this.head.addBox(-4.0f, -4.0f, -6.0f, 8, 8, 6, 0.0f);
        this.head.setPosition(0.0f, 4.0f, -8.0f);
        this.horn1 = new ModelRenderer(22, 0);
        this.horn1.addBox(-4.0f, -5.0f, -4.0f, 1, 3, 1, 0.0f);
        this.horn1.setPosition(0.0f, 3.0f, -7.0f);
        this.horn2 = new ModelRenderer(22, 0);
        this.horn2.addBox(3.0f, -5.0f, -4.0f, 1, 3, 1, 0.0f);
        this.horn2.setPosition(0.0f, 3.0f, -7.0f);
        this.udders = new ModelRenderer(52, 0);
        this.udders.addBox(-2.0f, -3.0f, 0.0f, 4, 6, 2, 0.0f);
        this.udders.setPosition(0.0f, 14.0f, 6.0f);
        this.udders.rotateAngleX = 1.5707964f;
        this.body = new ModelRenderer(18, 4);
        this.body.addBox(-6.0f, -10.0f, -7.0f, 12, 18, 10, 0.0f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
        this.leg1.offsetX -= 1.0f;
        this.leg2.offsetX += 1.0f;
        ModelRenderer var10000 = this.leg1;
        var10000.offsetZ += 0.0f;
        var10000 = this.leg2;
        var10000.offsetZ += 0.0f;
        this.leg3.offsetX -= 1.0f;
        this.leg4.offsetX += 1.0f;
        this.leg3.offsetZ -= 1.0f;
        this.leg4.offsetZ -= 1.0f;
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        super.render(var1, var2, var3, var4, var5, var6);
        this.horn1.render(var6);
        this.horn2.render(var6);
        this.udders.render(var6);
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.horn1.rotateAngleY = this.head.rotateAngleY;
        this.horn1.rotateAngleX = this.head.rotateAngleX;
        this.horn2.rotateAngleY = this.head.rotateAngleY;
        this.horn2.rotateAngleX = this.head.rotateAngleX;
    }
}

