/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelMinecart;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Vec3D;
import org.lwjgl.opengl.GL11;

public class RenderMinecart
extends Render {
    protected ModelBase modelMinecart;

    public RenderMinecart() {
        this.shadowSize = 0.5f;
        this.modelMinecart = new ModelMinecart();
    }

    public void func_152_a(EntityMinecart var1, double var2, double var4, double var6, float var8, float var9) {
        GL11.glPushMatrix();
        double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var9;
        double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var9;
        double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var9;
        double var16 = 0.3f;
        Vec3D var18 = var1.func_514_g(var10, var12, var14);
        float var19 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
        if (var18 != null) {
            Vec3D var20 = var1.func_515_a(var10, var12, var14, var16);
            Vec3D var21 = var1.func_515_a(var10, var12, var14, -var16);
            if (var20 == null) {
                var20 = var18;
            }
            if (var21 == null) {
                var21 = var18;
            }
            var2 += var18.xCoord - var10;
            var4 += (var20.yCoord + var21.yCoord) / 2.0 - var12;
            var6 += var18.zCoord - var14;
            Vec3D var22 = var21.addVector(-var20.xCoord, -var20.yCoord, -var20.zCoord);
            if (var22.lengthVector() != 0.0) {
                var22 = var22.normalize();
                var8 = (float)(Math.atan2(var22.zCoord, var22.xCoord) * 180.0 / Math.PI);
                var19 = (float)(Math.atan(var22.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)((float)var2), (float)((float)var4), (float)((float)var6));
        GL11.glRotatef((float)(180.0f - var8), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-var19), (float)0.0f, (float)0.0f, (float)1.0f);
        float var23 = (float)var1.minecartTimeSinceHit - var9;
        float var24 = (float)var1.minecartCurrentDamage - var9;
        if (var24 < 0.0f) {
            var24 = 0.0f;
        }
        if (var23 > 0.0f) {
            GL11.glRotatef((float)(MathHelper.sin(var23) * var23 * var24 / 10.0f * (float)var1.minecartRockDirection), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        if (var1.minecartType != 0) {
            this.loadTexture("/terrain.png");
            float var25 = 0.75f;
            GL11.glScalef((float)var25, (float)var25, (float)var25);
            GL11.glTranslatef((float)0.0f, (float)0.3125f, (float)0.0f);
            GL11.glRotatef((float)90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            if (var1.minecartType == 1) {
                new RenderBlocks().renderBlockOnInventory(Block.crate, 0);
            } else if (var1.minecartType == 2) {
                new RenderBlocks().renderBlockOnInventory(Block.stoneOvenIdle, 0);
            }
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)0.0f);
            GL11.glScalef((float)(1.0f / var25), (float)(1.0f / var25), (float)(1.0f / var25));
        }
        this.loadTexture("/item/cart.png");
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
        this.modelMinecart.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.func_152_a((EntityMinecart)var1, var2, var4, var6, var8, var9);
    }
}

