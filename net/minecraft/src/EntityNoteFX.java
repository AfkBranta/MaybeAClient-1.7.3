/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityNoteFX
extends EntityFX {
    float field_21065_a;

    public EntityNoteFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
        this(var1, var2, var4, var6, var8, var10, var12, 2.0f);
    }

    public EntityNoteFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14) {
        super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.01f;
        this.motionY *= (double)0.01f;
        this.motionZ *= (double)0.01f;
        this.motionY += 0.2;
        this.particleRed = MathHelper.sin(((float)var8 + 0.0f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleGreen = MathHelper.sin(((float)var8 + 0.33333334f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleBlue = MathHelper.sin(((float)var8 + 0.6666667f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleScale *= 0.75f;
        this.particleScale *= var14;
        this.field_21065_a = this.particleScale;
        this.particleMaxAge = 6;
        this.noClip = false;
        this.particleTextureIndex = 64;
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
        this.particleScale = this.field_21065_a * var8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= (double)0.66f;
        this.motionY *= (double)0.66f;
        this.motionZ *= (double)0.66f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

