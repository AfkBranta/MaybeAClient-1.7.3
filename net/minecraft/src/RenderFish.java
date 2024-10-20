/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFish;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderFish
extends Render {
    public void func_4011_a(EntityFish var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        int var10 = 1;
        int var11 = 2;
        this.loadTexture("/particles.png");
        Tessellator var12 = Tessellator.instance;
        float var13 = (float)(var10 * 8 + 0) / 128.0f;
        float var14 = (float)(var10 * 8 + 8) / 128.0f;
        float var15 = (float)(var11 * 8 + 0) / 128.0f;
        float var16 = (float)(var11 * 8 + 8) / 128.0f;
        float var17 = 1.0f;
        float var18 = 0.5f;
        float var19 = 0.5f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        var12.startDrawingQuads();
        var12.setNormal(0.0f, 1.0f, 0.0f);
        var12.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var12.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var12.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var12.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var12.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
        if (var1.angler != null) {
            float var20 = (var1.angler.prevRotationYaw + (var1.angler.rotationYaw - var1.angler.prevRotationYaw) * var9) * (float)Math.PI / 180.0f;
            float var21 = (var1.angler.prevRotationPitch + (var1.angler.rotationPitch - var1.angler.prevRotationPitch) * var9) * (float)Math.PI / 180.0f;
            double var22 = MathHelper.sin(var20);
            double var24 = MathHelper.cos(var20);
            double var26 = MathHelper.sin(var21);
            double var28 = MathHelper.cos(var21);
            double var30 = var1.angler.prevPosX + (var1.angler.posX - var1.angler.prevPosX) * (double)var9 - var24 * 0.7 - var22 * 0.5 * var28;
            double var32 = var1.angler.prevPosY + (var1.angler.posY - var1.angler.prevPosY) * (double)var9 - var26 * 0.5;
            double var34 = var1.angler.prevPosZ + (var1.angler.posZ - var1.angler.prevPosZ) * (double)var9 - var22 * 0.7 + var24 * 0.5 * var28;
            if (this.renderManager.options.thirdPersonView) {
                var20 = (var1.angler.prevRenderYawOffset + (var1.angler.renderYawOffset - var1.angler.prevRenderYawOffset) * var9) * (float)Math.PI / 180.0f;
                var22 = MathHelper.sin(var20);
                var24 = MathHelper.cos(var20);
                var30 = var1.angler.prevPosX + (var1.angler.posX - var1.angler.prevPosX) * (double)var9 - var24 * 0.35 - var22 * 0.85;
                var32 = var1.angler.prevPosY + (var1.angler.posY - var1.angler.prevPosY) * (double)var9 - 0.45;
                var34 = var1.angler.prevPosZ + (var1.angler.posZ - var1.angler.prevPosZ) * (double)var9 - var22 * 0.35 + var24 * 0.85;
            }
            double var36 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var9;
            double var38 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var9 + 0.25;
            double var40 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var9;
            double var42 = (float)(var30 - var36);
            double var44 = (float)(var32 - var38);
            double var46 = (float)(var34 - var40);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            var12.startDrawing(3);
            var12.setColorOpaque_I(0);
            int var48 = 16;
            int var49 = 0;
            while (var49 <= var48) {
                float var50 = (float)var49 / (float)var48;
                var12.addVertex(var2 + var42 * (double)var50, var4 + var44 * (double)(var50 * var50 + var50) * 0.5 + 0.25, var6 + var46 * (double)var50);
                ++var49;
            }
            var12.draw();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
        }
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_4011_a((EntityFish)var1, var2, var4, var6, var8, var9);
    }
}

