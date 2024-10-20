/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityPortalFX
extends EntityFX {
    private float field_4083_a;
    private double field_4086_p;
    private double field_4085_q;
    private double field_4084_r;

    public EntityPortalFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        super(var1, var2, var4, var6, var8, var10, var12);
        this.motionX = var8;
        this.motionY = var10;
        this.motionZ = var12;
        this.field_4086_p = this.posX = var2;
        this.field_4085_q = this.posY = var4;
        this.field_4084_r = this.posZ = var6;
        float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        this.field_4083_a = this.particleScale = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleGreen = this.particleBlue = 1.0f * var14;
        this.particleRed = this.particleBlue;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
        this.noClip = true;
        this.particleTextureIndex = (int)(Math.random() * 8.0);
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = ((float)this.particleAge + var2) / (float)this.particleMaxAge;
        var8 = 1.0f - var8;
        var8 *= var8;
        var8 = 1.0f - var8;
        this.particleScale = this.field_4083_a * var8;
        super.renderParticle(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public float getEntityBrightness(float var1) {
        float var2 = super.getEntityBrightness(var1);
        float var3 = (float)this.particleAge / (float)this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        return var2 * (1.0f - var3) + var3;
    }

    @Override
    public void onUpdate() {
        float var1;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var2 = var1 = (float)this.particleAge / (float)this.particleMaxAge;
        var1 = -var1 + var1 * var1 * 2.0f;
        var1 = 1.0f - var1;
        this.posX = this.field_4086_p + this.motionX * (double)var1;
        this.posY = this.field_4085_q + this.motionY * (double)var1 + (double)(1.0f - var2);
        this.posZ = this.field_4084_r + this.motionZ * (double)var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
    }
}

