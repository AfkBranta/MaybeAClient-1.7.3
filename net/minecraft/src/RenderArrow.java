/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderArrow
extends Render {
    public void func_154_a(EntityArrow var1, double var2, double var4, double var6, float var8, float var9) {
        this.loadTexture("/item/arrows.png");
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glRotatef((float)(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9 - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9), (float)0.0f, (float)0.0f, (float)1.0f);
        Tessellator var10 = Tessellator.instance;
        int var11 = 0;
        float var12 = 0.0f;
        float var13 = 0.5f;
        float var14 = (float)(0 + var11 * 10) / 32.0f;
        float var15 = (float)(5 + var11 * 10) / 32.0f;
        float var16 = 0.0f;
        float var17 = 0.15625f;
        float var18 = (float)(5 + var11 * 10) / 32.0f;
        float var19 = (float)(10 + var11 * 10) / 32.0f;
        float var20 = 0.05625f;
        GL11.glEnable((int)32826);
        float var21 = (float)var1.arrowShake - var9;
        if (var21 > 0.0f) {
            float var22 = -MathHelper.sin(var21 * 3.0f) * var21;
            GL11.glRotatef((float)var22, (float)0.0f, (float)0.0f, (float)1.0f);
        }
        GL11.glRotatef((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)var20, (float)var20, (float)var20);
        GL11.glTranslatef((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)var20, (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var19);
        var10.draw();
        GL11.glNormal3f((float)(-var20), (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var19);
        var10.draw();
        int var23 = 0;
        while (var23 < 4) {
            GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)var20);
            var10.startDrawingQuads();
            var10.addVertexWithUV(-8.0, -2.0, 0.0, var12, var14);
            var10.addVertexWithUV(8.0, -2.0, 0.0, var13, var14);
            var10.addVertexWithUV(8.0, 2.0, 0.0, var13, var15);
            var10.addVertexWithUV(-8.0, 2.0, 0.0, var12, var15);
            var10.draw();
            ++var23;
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_154_a((EntityArrow)var1, var2, var4, var6, var8, var9);
    }
}

