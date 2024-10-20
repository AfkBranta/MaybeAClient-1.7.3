/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityReddustFX
extends EntityFX {
    float field_673_a;

    public EntityReddustFX(World var1, double var2, double var4, double var6, float var8, float var9, float var10) {
        this(var1, var2, var4, var6, 1.0f, var8, var9, var10);
    }

    public EntityReddustFX(World var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
        super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.1f;
        this.motionY *= (double)0.1f;
        this.motionZ *= (double)0.1f;
        if (var9 == 0.0f) {
            var9 = 1.0f;
        }
        float var12 = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * (double)0.2f) + 0.8f) * var9 * var12;
        this.particleGreen = ((float)(Math.random() * (double)0.2f) + 0.8f) * var10 * var12;
        this.particleBlue = ((float)(Math.random() * (double)0.2f) + 0.8f) * var11 * var12;
        this.particleScale *= 0.75f;
        this.particleScale *= var8;
        this.field_673_a = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int)((float)this.particleMaxAge * var8);
        this.noClip = false;
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = ((float)this.particleAge + var2) / (float)this.particleMaxAge * 32.0f;
        if (var8 < 0.0f) {
            var8 = 0.0f;
        }
        if (var8 > 1.0f) {
            var8 = 1.0f;
        }
        this.particleScale = this.field_673_a * var8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= (double)0.96f;
        this.motionY *= (double)0.96f;
        this.motionZ *= (double)0.96f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

