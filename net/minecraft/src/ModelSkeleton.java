/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelRenderer;
import net.minecraft.src.ModelZombie;

public class ModelSkeleton
extends ModelZombie {
    public ModelSkeleton() {
        float var1 = 0.0f;
        this.bipedRightArm = new ModelRenderer(40, 16);
        this.bipedRightArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, var1);
        this.bipedRightArm.setPosition(-5.0f, 2.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer(40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, var1);
        this.bipedLeftArm.setPosition(5.0f, 2.0f, 0.0f);
        this.bipedRightLeg = new ModelRenderer(0, 16);
        this.bipedRightLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, var1);
        this.bipedRightLeg.setPosition(-2.0f, 12.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer(0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, var1);
        this.bipedLeftLeg.setPosition(2.0f, 12.0f, 0.0f);
    }
}

