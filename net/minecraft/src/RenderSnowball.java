/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderSnowball
extends Render {
    private int field_20003_a;

    public RenderSnowball(int var1) {
        this.field_20003_a = var1;
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.loadTexture("/gui/items.png");
        Tessellator var10 = Tessellator.instance;
        float var11 = (float)(this.field_20003_a % 16 * 16 + 0) / 256.0f;
        float var12 = (float)(this.field_20003_a % 16 * 16 + 16) / 256.0f;
        float var13 = (float)(this.field_20003_a / 16 * 16 + 0) / 256.0f;
        float var14 = (float)(this.field_20003_a / 16 * 16 + 16) / 256.0f;
        float var15 = 1.0f;
        float var16 = 0.5f;
        float var17 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        var10.startDrawingQuads();
        var10.setNormal(0.0f, 1.0f, 0.0f);
        var10.addVertexWithUV(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
        var10.addVertexWithUV(var15 - var16, 0.0f - var17, 0.0, var12, var14);
        var10.addVertexWithUV(var15 - var16, 1.0f - var17, 0.0, var12, var13);
        var10.addVertexWithUV(0.0f - var16, 1.0f - var17, 0.0, var11, var13);
        var10.draw();
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }
}

