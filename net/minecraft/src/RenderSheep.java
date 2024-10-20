/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSheep
extends RenderLiving {
    public RenderSheep(ModelBase var1, ModelBase var2, float var3) {
        super(var1, var3);
        this.setRenderPassModel(var2);
    }

    protected boolean func_176_a(EntitySheep var1, int var2, float var3) {
        if (var2 == 0 && !var1.getSheared()) {
            this.loadTexture("/mob/sheep_fur.png");
            float var4 = var1.getEntityBrightness(var3);
            int var5 = var1.getFleeceColor();
            GL11.glColor3f((float)(var4 * EntitySheep.fleeceColorTable[var5][0]), (float)(var4 * EntitySheep.fleeceColorTable[var5][1]), (float)(var4 * EntitySheep.fleeceColorTable[var5][2]));
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving var1, int var2, float var3) {
        return this.func_176_a((EntitySheep)var1, var2, var3);
    }
}

