/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class RenderFallingSand
extends Render {
    private RenderBlocks field_197_d = new RenderBlocks();

    public RenderFallingSand() {
        this.shadowSize = 0.5f;
    }

    public void func_156_a(EntityFallingSand var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        this.loadTexture("/terrain.png");
        Block var10 = Block.blocksList[var1.blockID];
        World var11 = var1.func_465_i();
        GL11.glDisable((int)2896);
        this.field_197_d.renderBlockFallingSand(var10, var11, MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
        GL11.glEnable((int)2896);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_156_a((EntityFallingSand)var1, var2, var4, var6, var8, var9);
    }
}

