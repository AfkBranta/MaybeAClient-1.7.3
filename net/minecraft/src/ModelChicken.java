package net.minecraft.src;

import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelChicken
extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer rightWing;
    public ModelRenderer leftWing;
    public ModelRenderer bill;
    public ModelRenderer chin;

    public ModelChicken() {
        int var1 = 16;
        this.head = new ModelRenderer(0, 0);
        this.head.addBox(-2.0f, -6.0f, -2.0f, 4, 6, 3, 0.0f);
        this.head.setPosition(0.0f, -1 + var1, -4.0f);
        this.bill = new ModelRenderer(14, 0);
        this.bill.addBox(-2.0f, -4.0f, -4.0f, 4, 2, 2, 0.0f);
        this.bill.setPosition(0.0f, -1 + var1, -4.0f);
        this.chin = new ModelRenderer(14, 4);
        this.chin.addBox(-1.0f, -2.0f, -3.0f, 2, 2, 2, 0.0f);
        this.chin.setPosition(0.0f, -1 + var1, -4.0f);
        this.body = new ModelRenderer(0, 9);
        this.body.addBox(-3.0f, -4.0f, -3.0f, 6, 8, 6, 0.0f);
        this.body.setPosition(0.0f, 0 + var1, 0.0f);
        this.rightLeg = new ModelRenderer(26, 0);
        this.rightLeg.addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.rightLeg.setPosition(-2.0f, 3 + var1, 1.0f);
        this.leftLeg = new ModelRenderer(26, 0);
        this.leftLeg.addBox(-1.0f, 0.0f, -3.0f, 3, 5, 3);
        this.leftLeg.setPosition(1.0f, 3 + var1, 1.0f);
        this.rightWing = new ModelRenderer(24, 13);
        this.rightWing.addBox(0.0f, 0.0f, -3.0f, 1, 4, 6);
        this.rightWing.setPosition(-4.0f, -3 + var1, 0.0f);
        this.leftWing = new ModelRenderer(24, 13);
        this.leftWing.addBox(-1.0f, 0.0f, -3.0f, 1, 4, 6);
        this.leftWing.setPosition(4.0f, -3 + var1, 0.0f);
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.head.render(var6);
        this.bill.render(var6);
        this.chin.render(var6);
        this.body.render(var6);
        this.rightLeg.render(var6);
        this.leftLeg.render(var6);
        this.rightWing.render(var6);
        this.leftWing.render(var6);
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.head.rotateAngleX = -(var5 / 57.295776f);
        this.head.rotateAngleY = var4 / 57.295776f;
        this.bill.rotateAngleX = this.head.rotateAngleX;
        this.bill.rotateAngleY = this.head.rotateAngleY;
        this.chin.rotateAngleX = this.head.rotateAngleX;
        this.chin.rotateAngleY = this.head.rotateAngleY;
        this.body.rotateAngleX = 1.5707964f;
        this.rightLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662f) * 1.4f * var2;
        this.leftLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662f + (float)Math.PI) * 1.4f * var2;
        this.rightWing.rotateAngleZ = var3;
        this.leftWing.rotateAngleZ = -var3;
    }
}

