package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityFlameFX
extends EntityFX {
    private float field_672_a;

    public EntityFlameFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        super(var1, var2, var4, var6, var8, var10, var12);
        this.motionX = this.motionX * (double)0.01f + var8;
        this.motionY = this.motionY * (double)0.01f + var10;
        this.motionZ = this.motionZ * (double)0.01f + var12;
        double var10000 = var2 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = var4 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        var10000 = var6 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.field_672_a = this.particleScale;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.particleTextureIndex = 48;
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = ((float)this.particleAge + var2) / (float)this.particleMaxAge;
        this.particleScale = this.field_672_a * (1.0f - var8 * var8 * 0.5f);
        super.renderParticle(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public float getEntityBrightness(float var1) {
        float var2 = ((float)this.particleAge + var1) / (float)this.particleMaxAge;
        if (var2 < 0.0f) {
            var2 = 0.0f;
        }
        if (var2 > 1.0f) {
            var2 = 1.0f;
        }
        float var3 = super.getEntityBrightness(var1);
        return var3 * var2 + (1.0f - var2);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.96f;
        this.motionY *= (double)0.96f;
        this.motionZ *= (double)0.96f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

