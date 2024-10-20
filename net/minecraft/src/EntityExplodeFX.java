/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityExplodeFX
extends EntityFX {
    public EntityExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        super(var1, var2, var4, var6, var8, var10, var12);
        this.motionX = var8 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.motionY = var10 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.motionZ = var12 + (double)((float)(Math.random() * 2.0 - 1.0) * 0.05f);
        this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleRed = this.particleBlue;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int)(16.0 / ((double)this.rand.nextFloat() * 0.8 + 0.2)) + 2;
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        super.renderParticle(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.9f;
        this.motionY *= (double)0.9f;
        this.motionZ *= (double)0.9f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

