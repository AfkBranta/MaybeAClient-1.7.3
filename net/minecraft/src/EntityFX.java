/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityFX
extends Entity {
    protected int particleTextureIndex;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge = 0;
    protected int particleMaxAge = 0;
    protected float particleScale;
    protected float particleGravity;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;

    public EntityFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        super(var1);
        this.setSize(0.2f, 0.2f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(var2, var4, var6);
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.motionX = var8 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionY = var10 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        this.motionZ = var12 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.4f);
        float var14 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)var15 * (double)var14 * (double)0.4f;
        this.motionY = this.motionY / (double)var15 * (double)var14 * (double)0.4f + (double)0.1f;
        this.motionZ = this.motionZ / (double)var15 * (double)var14 * (double)0.4f;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }

    public EntityFX func_407_b(float var1) {
        this.motionX *= (double)var1;
        this.motionY = (this.motionY - (double)0.1f) * (double)var1 + (double)0.1f;
        this.motionZ *= (double)var1;
        return this;
    }

    public EntityFX func_405_d(float var1) {
        this.setSize(0.2f * var1, 0.2f * var1);
        this.particleScale *= var1;
        return this;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.motionY -= 0.04 * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.98f;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)0.98f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }

    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = (float)(this.particleTextureIndex % 16) / 16.0f;
        float var9 = var8 + 0.0624375f;
        float var10 = (float)(this.particleTextureIndex / 16) / 16.0f;
        float var11 = var10 + 0.0624375f;
        float var12 = 0.1f * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var2 - interpPosZ);
        float var16 = this.getEntityBrightness(var2);
        var1.setColorOpaque_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16);
        var1.addVertexWithUV(var13 - var3 * var12 - var6 * var12, var14 - var4 * var12, var15 - var5 * var12 - var7 * var12, var9, var11);
        var1.addVertexWithUV(var13 - var3 * var12 + var6 * var12, var14 + var4 * var12, var15 - var5 * var12 + var7 * var12, var9, var10);
        var1.addVertexWithUV(var13 + var3 * var12 + var6 * var12, var14 + var4 * var12, var15 + var5 * var12 + var7 * var12, var8, var10);
        var1.addVertexWithUV(var13 + var3 * var12 - var6 * var12, var14 - var4 * var12, var15 + var5 * var12 - var7 * var12, var8, var11);
    }

    public int getFXLayer() {
        return 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
    }
}

