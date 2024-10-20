package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityDiggingFX
extends EntityFX {
    private Block field_4082_a;

    public EntityDiggingFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, Block var14) {
        super(var1, var2, var4, var6, var8, var10, var12);
        this.field_4082_a = var14;
        this.particleTextureIndex = var14.blockIndexInTexture;
        this.particleGravity = var14.blockParticleGravity;
        this.particleBlue = 0.6f;
        this.particleGreen = 0.6f;
        this.particleRed = 0.6f;
        this.particleScale /= 2.0f;
    }

    public EntityDiggingFX func_4041_a(int var1, int var2, int var3) {
        if (this.field_4082_a == Block.grass) {
            return this;
        }
        int var4 = this.field_4082_a.colorMultiplier(this.worldObj, var1, var2, var3);
        this.particleRed *= (float)(var4 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (float)(var4 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (float)(var4 & 0xFF) / 255.0f;
        return this;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = ((float)(this.particleTextureIndex % 16) + this.particleTextureJitterX / 4.0f) / 16.0f;
        float var9 = var8 + 0.015609375f;
        float var10 = ((float)(this.particleTextureIndex / 16) + this.particleTextureJitterY / 4.0f) / 16.0f;
        float var11 = var10 + 0.015609375f;
        float var12 = 0.1f * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var2 - interpPosZ);
        float var16 = this.getEntityBrightness(var2);
        var1.setColorOpaque_F(var16 * this.particleRed, var16 * this.particleGreen, var16 * this.particleBlue);
        var1.addVertexWithUV(var13 - var3 * var12 - var6 * var12, var14 - var4 * var12, var15 - var5 * var12 - var7 * var12, var8, var11);
        var1.addVertexWithUV(var13 - var3 * var12 + var6 * var12, var14 + var4 * var12, var15 - var5 * var12 + var7 * var12, var8, var10);
        var1.addVertexWithUV(var13 + var3 * var12 + var6 * var12, var14 + var4 * var12, var15 + var5 * var12 + var7 * var12, var9, var10);
        var1.addVertexWithUV(var13 + var3 * var12 - var6 * var12, var14 - var4 * var12, var15 + var5 * var12 - var7 * var12, var9, var11);
    }
}

