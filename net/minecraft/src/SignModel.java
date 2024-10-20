package net.minecraft.src;

import net.minecraft.src.ModelRenderer;

public class SignModel {
    public ModelRenderer field_1346_a = new ModelRenderer(0, 0);
    public ModelRenderer field_1345_b;

    public SignModel() {
        this.field_1346_a.addBox(-12.0f, -14.0f, -1.0f, 24, 12, 2, 0.0f);
        this.field_1345_b = new ModelRenderer(0, 14);
        this.field_1345_b.addBox(-1.0f, -2.0f, -1.0f, 2, 14, 2, 0.0f);
    }

    public void func_887_a() {
        this.field_1346_a.render(0.0625f);
        this.field_1345_b.render(0.0625f);
    }
}

