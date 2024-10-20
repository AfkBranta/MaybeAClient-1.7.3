/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.Item;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderFireball
extends Render {
    public void func_4012_a(EntityFireball var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glEnable((int)32826);
        float var10 = 2.0f;
        GL11.glScalef((float)(var10 / 1.0f), (float)(var10 / 1.0f), (float)(var10 / 1.0f));
        int var11 = Item.snowball.getIconIndex(null);
        this.loadTexture("/gui/items.png");
        Tessellator var12 = Tessellator.instance;
        float var13 = (float)(var11 % 16 * 16 + 0) / 256.0f;
        float var14 = (float)(var11 % 16 * 16 + 16) / 256.0f;
        float var15 = (float)(var11 / 16 * 16 + 0) / 256.0f;
        float var16 = (float)(var11 / 16 * 16 + 16) / 256.0f;
        float var17 = 1.0f;
        float var18 = 0.5f;
        float var19 = 0.25f;
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
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_4012_a((EntityFireball)var1, var2, var4, var6, var8, var9);
    }
}

