/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.BlockFluids;
import net.minecraft.src.EntityFX;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityRainFX
extends EntityFX {
    public EntityRainFX(World var1, double var2, double var4, double var6) {
        super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.3f;
        this.motionY = (float)Math.random() * 0.2f + 0.1f;
        this.motionZ *= (double)0.3f;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.particleTextureIndex = 19 + this.rand.nextInt(4);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        super.renderParticle(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public void onUpdate() {
        double var2;
        Material var1;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.98f;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)0.98f;
        if (this.particleMaxAge-- <= 0) {
            this.setEntityDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setEntityDead();
            }
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
        if (((var1 = this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))).getIsLiquid() || var1.isSolid()) && this.posY < (var2 = (double)((float)(MathHelper.floor_double(this.posY) + 1) - BlockFluids.getPercentAir(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))))) {
            this.setEntityDead();
        }
    }
}

