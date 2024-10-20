package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSlime
extends ModelBase {
    ModelRenderer slimeBodies;
    ModelRenderer slimeRightEye;
    ModelRenderer slimeLeftEye;
    ModelRenderer slimeMouth;

    public ModelSlime(int var1) {
        this.slimeBodies = new ModelRenderer(0, var1);
        this.slimeBodies.addBox(-4.0f, 16.0f, -4.0f, 8, 8, 8);
        if (var1 > 0) {
            this.slimeBodies = new ModelRenderer(0, var1);
            this.slimeBodies.addBox(-3.0f, 17.0f, -3.0f, 6, 6, 6);
            this.slimeRightEye = new ModelRenderer(32, 0);
            this.slimeRightEye.addBox(-3.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeLeftEye = new ModelRenderer(32, 4);
            this.slimeLeftEye.addBox(1.25f, 18.0f, -3.5f, 2, 2, 2);
            this.slimeMouth = new ModelRenderer(32, 8);
            this.slimeMouth.addBox(0.0f, 21.0f, -3.5f, 1, 1, 1);
        }
    }

    @Override
    public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
    }

    @Override
    public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
        this.setRotationAngles(var1, var2, var3, var4, var5, var6);
        this.slimeBodies.render(var6);
        if (this.slimeRightEye != null) {
            this.slimeRightEye.render(var6);
            this.slimeLeftEye.render(var6);
            this.slimeMouth.render(var6);
        }
    }
}

