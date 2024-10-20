/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityZombieSimple;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderZombieSimple
extends RenderLiving {
    private float scale;

    public RenderZombieSimple(ModelBase var1, float var2, float var3) {
        super(var1, var2 * var3);
        this.scale = var3;
    }

    protected void preRenderScale(EntityZombieSimple var1, float var2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    @Override
    protected void preRenderCallback(EntityLiving var1, float var2) {
        this.preRenderScale((EntityZombieSimple)var1, var2);
    }
}

