/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelCreeper;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderCreeper
extends RenderLiving {
    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
    }

    protected void updateCreeperScale(EntityCreeper var1, float var2) {
        float var4 = var1.setCreeperFlashTime(var2);
        float var5 = 1.0f + MathHelper.sin(var4 * 100.0f) * var4 * 0.01f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        var4 *= var4;
        var4 *= var4;
        float var6 = (1.0f + var4 * 0.4f) * var5;
        float var7 = (1.0f + var4 * 0.1f) / var5;
        GL11.glScalef((float)var6, (float)var7, (float)var6);
    }

    protected int updateCreeperColorMultiplier(EntityCreeper var1, float var2, float var3) {
        float var5 = var1.setCreeperFlashTime(var3);
        if ((int)(var5 * 10.0f) % 2 == 0) {
            return 0;
        }
        int var6 = (int)(var5 * 0.2f * 255.0f);
        if (var6 < 0) {
            var6 = 0;
        }
        if (var6 > 255) {
            var6 = 255;
        }
        int var7 = 255;
        int var8 = 255;
        int var9 = 255;
        return var6 << 24 | var7 << 16 | var8 << 8 | var9;
    }

    @Override
    protected void preRenderCallback(EntityLiving var1, float var2) {
        this.updateCreeperScale((EntityCreeper)var1, var2);
    }

    @Override
    protected int getColorMultiplier(EntityLiving var1, float var2, float var3) {
        return this.updateCreeperColorMultiplier((EntityCreeper)var1, var2, var3);
    }
}

