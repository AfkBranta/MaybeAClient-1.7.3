/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EnumArt;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderPainting
extends Render {
    private Random rand = new Random();

    public void func_158_a(EntityPainting var1, double var2, double var4, double var6, float var8, float var9) {
        this.rand.setSeed(187L);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glRotatef((float)var8, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glEnable((int)32826);
        this.loadTexture("/art/kz.png");
        EnumArt var10 = var1.art;
        float var11 = 0.0625f;
        GL11.glScalef((float)var11, (float)var11, (float)var11);
        this.func_159_a(var1, var10.sizeX, var10.sizeY, var10.offsetX, var10.offsetY);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    private void func_159_a(EntityPainting var1, int var2, int var3, int var4, int var5) {
        float var6 = (float)(-var2) / 2.0f;
        float var7 = (float)(-var3) / 2.0f;
        float var8 = -0.5f;
        float var9 = 0.5f;
        int var10 = 0;
        while (var10 < var2 / 16) {
            int var11 = 0;
            while (var11 < var3 / 16) {
                float var12 = var6 + (float)((var10 + 1) * 16);
                float var13 = var6 + (float)(var10 * 16);
                float var14 = var7 + (float)((var11 + 1) * 16);
                float var15 = var7 + (float)(var11 * 16);
                this.func_160_a(var1, (var12 + var13) / 2.0f, (var14 + var15) / 2.0f);
                float var16 = (float)(var4 + var2 - var10 * 16) / 256.0f;
                float var17 = (float)(var4 + var2 - (var10 + 1) * 16) / 256.0f;
                float var18 = (float)(var5 + var3 - var11 * 16) / 256.0f;
                float var19 = (float)(var5 + var3 - (var11 + 1) * 16) / 256.0f;
                float var20 = 0.75f;
                float var21 = 0.8125f;
                float var22 = 0.0f;
                float var23 = 0.0625f;
                float var24 = 0.75f;
                float var25 = 0.8125f;
                float var26 = 0.001953125f;
                float var27 = 0.001953125f;
                float var28 = 0.7519531f;
                float var29 = 0.7519531f;
                float var30 = 0.0f;
                float var31 = 0.0625f;
                Tessellator var32 = Tessellator.instance;
                var32.startDrawingQuads();
                var32.setNormal(0.0f, 0.0f, -1.0f);
                var32.addVertexWithUV(var12, var15, var8, var17, var18);
                var32.addVertexWithUV(var13, var15, var8, var16, var18);
                var32.addVertexWithUV(var13, var14, var8, var16, var19);
                var32.addVertexWithUV(var12, var14, var8, var17, var19);
                var32.setNormal(0.0f, 0.0f, 1.0f);
                var32.addVertexWithUV(var12, var14, var9, var20, var22);
                var32.addVertexWithUV(var13, var14, var9, var21, var22);
                var32.addVertexWithUV(var13, var15, var9, var21, var23);
                var32.addVertexWithUV(var12, var15, var9, var20, var23);
                var32.setNormal(0.0f, -1.0f, 0.0f);
                var32.addVertexWithUV(var12, var14, var8, var24, var26);
                var32.addVertexWithUV(var13, var14, var8, var25, var26);
                var32.addVertexWithUV(var13, var14, var9, var25, var27);
                var32.addVertexWithUV(var12, var14, var9, var24, var27);
                var32.setNormal(0.0f, 1.0f, 0.0f);
                var32.addVertexWithUV(var12, var15, var9, var24, var26);
                var32.addVertexWithUV(var13, var15, var9, var25, var26);
                var32.addVertexWithUV(var13, var15, var8, var25, var27);
                var32.addVertexWithUV(var12, var15, var8, var24, var27);
                var32.setNormal(-1.0f, 0.0f, 0.0f);
                var32.addVertexWithUV(var12, var14, var9, var29, var30);
                var32.addVertexWithUV(var12, var15, var9, var29, var31);
                var32.addVertexWithUV(var12, var15, var8, var28, var31);
                var32.addVertexWithUV(var12, var14, var8, var28, var30);
                var32.setNormal(1.0f, 0.0f, 0.0f);
                var32.addVertexWithUV(var13, var14, var8, var29, var30);
                var32.addVertexWithUV(var13, var15, var8, var29, var31);
                var32.addVertexWithUV(var13, var15, var9, var28, var31);
                var32.addVertexWithUV(var13, var14, var9, var28, var30);
                var32.draw();
                ++var11;
            }
            ++var10;
        }
    }

    private void func_160_a(EntityPainting var1, float var2, float var3) {
        int var4 = MathHelper.floor_double(var1.posX);
        int var5 = MathHelper.floor_double(var1.posY + (double)(var3 / 16.0f));
        int var6 = MathHelper.floor_double(var1.posZ);
        if (var1.direction == 0) {
            var4 = MathHelper.floor_double(var1.posX + (double)(var2 / 16.0f));
        }
        if (var1.direction == 1) {
            var6 = MathHelper.floor_double(var1.posZ - (double)(var2 / 16.0f));
        }
        if (var1.direction == 2) {
            var4 = MathHelper.floor_double(var1.posX - (double)(var2 / 16.0f));
        }
        if (var1.direction == 3) {
            var6 = MathHelper.floor_double(var1.posZ + (double)(var2 / 16.0f));
        }
        float var7 = this.renderManager.worldObj.getLightBrightness(var4, var5, var6);
        GL11.glColor3f((float)var7, (float)var7, (float)var7);
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_158_a((EntityPainting)var1, var2, var4, var6, var8, var9);
    }
}

