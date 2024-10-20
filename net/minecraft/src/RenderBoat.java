/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBoat;
import net.minecraft.src.Render;
import net.skidcode.gh.maybeaclient.hacks.NoRenderHack;
import org.lwjgl.opengl.GL11;

public class RenderBoat
extends Render {
    protected ModelBase modelBoat;

    public RenderBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }

    public void func_157_a(EntityBoat var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glRotatef((float)(180.0f - var8), (float)0.0f, (float)1.0f, (float)0.0f);
        float var10 = (float)var1.boatTimeSinceHit - var9;
        float var11 = (float)var1.boatCurrentDamage - var9;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(var10) * var10 * var11 / 10.0f * (float)var1.boatRockDirection), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        this.loadTexture("/terrain.png");
        float var12 = 0.75f;
        GL11.glScalef((float)var12, (float)var12, (float)var12);
        GL11.glScalef((float)(1.0f / var12), (float)(1.0f / var12), (float)(1.0f / var12));
        this.loadTexture("/item/boat.png");
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelBoat.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        if (NoRenderHack.instance.status && NoRenderHack.instance.boats.value) {
            return;
        }
        this.func_157_a((EntityBoat)var1, var2, var4, var6, var8, var9);
    }
}

