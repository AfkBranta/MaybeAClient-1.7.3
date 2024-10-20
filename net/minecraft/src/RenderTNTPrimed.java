/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import org.lwjgl.opengl.GL11;

public class RenderTNTPrimed
extends Render {
    private RenderBlocks field_196_d = new RenderBlocks();

    public RenderTNTPrimed() {
        this.shadowSize = 0.5f;
    }

    public void func_153_a(EntityTNTPrimed var1, double var2, double var4, double var6, float var8, float var9) {
        float var10;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        if ((float)var1.fuse - var9 + 1.0f < 10.0f) {
            var10 = 1.0f - ((float)var1.fuse - var9 + 1.0f) / 10.0f;
            if (var10 < 0.0f) {
                var10 = 0.0f;
            }
            if (var10 > 1.0f) {
                var10 = 1.0f;
            }
            var10 *= var10;
            var10 *= var10;
            float var11 = 1.0f + var10 * 0.3f;
            GL11.glScalef((float)var11, (float)var11, (float)var11);
        }
        var10 = (1.0f - ((float)var1.fuse - var9 + 1.0f) / 100.0f) * 0.8f;
        this.loadTexture("/terrain.png");
        this.field_196_d.renderBlockOnInventory(Block.tnt, 0);
        if (var1.fuse / 5 % 2 == 0) {
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)772);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)var10);
            this.field_196_d.renderBlockOnInventory(Block.tnt, 0);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_153_a((EntityTNTPrimed)var1, var2, var4, var6, var8, var9);
    }
}

