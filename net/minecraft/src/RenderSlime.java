/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSlime
extends RenderLiving {
    private ModelBase scaleAmount;

    public RenderSlime(ModelBase var1, ModelBase var2, float var3) {
        super(var1, var3);
        this.scaleAmount = var2;
    }

    protected boolean func_179_a(EntitySlime var1, int var2, float var3) {
        if (var2 == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable((int)2977);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            return true;
        }
        if (var2 == 1) {
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        return false;
    }

    protected void func_178_a(EntitySlime var1, float var2) {
        int var3 = var1.getSlimeSize();
        float var4 = (var1.field_767_b + (var1.field_768_a - var1.field_767_b) * var2) / ((float)var3 * 0.5f + 1.0f);
        float var5 = 1.0f / (var4 + 1.0f);
        float var6 = var3;
        GL11.glScalef((float)(var5 * var6), (float)(1.0f / var5 * var6), (float)(var5 * var6));
    }

    @Override
    protected void preRenderCallback(EntityLiving var1, float var2) {
        this.func_178_a((EntitySlime)var1, var2);
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving var1, int var2, float var3) {
        return this.func_179_a((EntitySlime)var1, var2, var3);
    }
}

