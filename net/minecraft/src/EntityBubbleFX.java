/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityBubbleFX
extends EntityFX {
    public EntityBubbleFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        super(var1, var2, var4, var6, var8, var10, var12);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.particleTextureIndex = 32;
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = var8 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.motionY = var10 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.motionZ = var12 * (double)0.2f + (double)((float)(Math.random() * 2.0 - 1.0) * 0.02f);
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.85f;
        this.motionY *= (double)0.85f;
        this.motionZ *= (double)0.85f;
        if (this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) != Material.water) {
            this.setEntityDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setEntityDead();
        }
    }
}

