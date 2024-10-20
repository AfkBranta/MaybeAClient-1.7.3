/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelGhast;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderGhast
extends RenderLiving {
    public RenderGhast() {
        super(new ModelGhast(), 0.5f);
    }

    protected void func_4014_a(EntityGhast var1, float var2) {
        float var4 = ((float)var1.prevAttackCounter + (float)(var1.attackCounter - var1.prevAttackCounter) * var2) / 20.0f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        var4 = 1.0f / (var4 * var4 * var4 * var4 * var4 * 2.0f + 1.0f);
        float var5 = (8.0f + var4) / 2.0f;
        float var6 = (8.0f + 1.0f / var4) / 2.0f;
        GL11.glScalef((float)var6, (float)var5, (float)var6);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Override
    protected void preRenderCallback(EntityLiving var1, float var2) {
        this.func_4014_a((EntityGhast)var1, var2);
    }
}

