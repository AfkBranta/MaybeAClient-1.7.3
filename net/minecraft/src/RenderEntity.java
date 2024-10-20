/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import org.lwjgl.opengl.GL11;

public class RenderEntity
extends Render {
    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        RenderEntity.renderOffsetAABB(var1.boundingBox, var2 - var1.lastTickPosX, var4 - var1.lastTickPosY, var6 - var1.lastTickPosZ);
        GL11.glPopMatrix();
    }
}

