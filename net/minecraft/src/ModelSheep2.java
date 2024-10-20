package net.minecraft.src;

import net.minecraft.src.ModelQuadraped;
import net.minecraft.src.ModelRenderer;

public class ModelSheep2
extends ModelQuadraped {
    public ModelSheep2() {
        super(12, 0.0f);
        this.head = new ModelRenderer(0, 0);
        this.head.addBox(-3.0f, -4.0f, -6.0f, 6, 6, 8, 0.0f);
        this.head.setPosition(0.0f, 6.0f, -8.0f);
        this.body = new ModelRenderer(28, 8);
        this.body.addBox(-4.0f, -10.0f, -7.0f, 8, 16, 6, 0.0f);
        this.body.setPosition(0.0f, 5.0f, 2.0f);
    }
}

