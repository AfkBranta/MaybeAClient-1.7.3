/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.skidcode.gh.maybeaclient.hacks.NoClientSideDestroyHack;

public class Explosion {
    public boolean field_12257_a = false;
    private Random ExplosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public Set<ChunkPosition> destroyedBlockPositions = new HashSet<ChunkPosition>();
    public ArrayList<ChunkPosition> positions = new ArrayList();

    public Explosion(World var1, Entity var2, double var3, double var5, double var7, float var9) {
        this.worldObj = var1;
        this.exploder = var2;
        this.explosionSize = var9;
        this.explosionX = var3;
        this.explosionY = var5;
        this.explosionZ = var7;
    }

    public void doExplosionA() {
        double var19;
        double var17;
        double var15;
        int var5;
        int var4;
        if (NoClientSideDestroyHack.instance.status && NoClientSideDestroyHack.instance.noTNT.value) {
            return;
        }
        float var1 = this.explosionSize;
        int var2 = 16;
        int var3 = 0;
        while (var3 < var2) {
            var4 = 0;
            while (var4 < var2) {
                var5 = 0;
                while (var5 < var2) {
                    if (var3 == 0 || var3 == var2 - 1 || var4 == 0 || var4 == var2 - 1 || var5 == 0 || var5 == var2 - 1) {
                        double var6 = (float)var3 / ((float)var2 - 1.0f) * 2.0f - 1.0f;
                        double var8 = (float)var4 / ((float)var2 - 1.0f) * 2.0f - 1.0f;
                        double var10 = (float)var5 / ((float)var2 - 1.0f) * 2.0f - 1.0f;
                        double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
                        var6 /= var12;
                        var8 /= var12;
                        var10 /= var12;
                        float var14 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        var15 = this.explosionX;
                        var17 = this.explosionY;
                        var19 = this.explosionZ;
                        float var21 = 0.3f;
                        while (var14 > 0.0f) {
                            int var24;
                            int var23;
                            int var22 = MathHelper.floor_double(var15);
                            int var25 = this.worldObj.getBlockId(var22, var23 = MathHelper.floor_double(var17), var24 = MathHelper.floor_double(var19));
                            if (var25 > 0) {
                                var14 -= (Block.blocksList[var25].getExplosionResistance(this.exploder) + 0.3f) * var21;
                            }
                            if (var14 > 0.0f) {
                                ChunkPosition cp = new ChunkPosition(var22, var23, var24);
                                this.destroyedBlockPositions.add(cp);
                                this.positions.add(cp);
                            }
                            var15 += var6 * (double)var21;
                            var17 += var8 * (double)var21;
                            var19 += var10 * (double)var21;
                            var14 -= var21 * 0.75f;
                        }
                    }
                    ++var5;
                }
                ++var4;
            }
            ++var3;
        }
        this.explosionSize *= 2.0f;
        var3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0);
        var4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0);
        var5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0);
        int var29 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0);
        int var7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0);
        int var30 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0);
        List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBoxFromPool(var3, var5, var7, var4, var29, var30));
        Vec3D var31 = Vec3D.createVector(this.explosionX, this.explosionY, this.explosionZ);
        int var11 = 0;
        while (var11 < var9.size()) {
            Entity var33 = (Entity)var9.get(var11);
            double var13 = var33.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;
            if (var13 <= 1.0) {
                var15 = var33.posX - this.explosionX;
                var17 = var33.posY - this.explosionY;
                var19 = var33.posZ - this.explosionZ;
                double var39 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
                var15 /= var39;
                var17 /= var39;
                var19 /= var39;
                double var40 = this.worldObj.func_675_a(var31, var33.boundingBox);
                double var41 = (1.0 - var13) * var40;
                var33.attackEntityFrom(this.exploder, (int)((var41 * var41 + var41) / 2.0 * 8.0 * (double)this.explosionSize + 1.0));
                var33.motionX += var15 * var41;
                var33.motionY += var17 * var41;
                var33.motionZ += var19 * var41;
            }
            ++var11;
        }
        this.explosionSize = var1;
        if (this.field_12257_a) {
            int var34 = this.positions.size() - 1;
            while (var34 >= 0) {
                ChunkPosition var35 = this.positions.get(var34);
                int var36 = var35.x;
                int var37 = var35.y;
                int var16 = var35.z;
                int var38 = this.worldObj.getBlockId(var36, var37, var16);
                int var18 = this.worldObj.getBlockId(var36, var37 - 1, var16);
                if (var38 == 0 && Block.opaqueCubeLookup[var18] && this.ExplosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlockWithNotify(var36, var37, var16, Block.fire.blockID);
                }
                --var34;
            }
        }
    }

    public void doExplosionB() {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (NoClientSideDestroyHack.instance.status && NoClientSideDestroyHack.instance.noTNT.value) {
            return;
        }
        int var2 = this.positions.size() - 1;
        while (var2 >= 0) {
            ChunkPosition var3 = this.positions.get(var2);
            int var4 = var3.x;
            int var5 = var3.y;
            int var6 = var3.z;
            int var7 = this.worldObj.getBlockId(var4, var5, var6);
            double var9 = (float)var4 + this.worldObj.rand.nextFloat();
            double var11 = (float)var5 + this.worldObj.rand.nextFloat();
            double var13 = (float)var6 + this.worldObj.rand.nextFloat();
            double var15 = var9 - this.explosionX;
            double var17 = var11 - this.explosionY;
            double var19 = var13 - this.explosionZ;
            double var21 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
            var15 /= var21;
            var17 /= var21;
            var19 /= var21;
            double var23 = 0.5 / (var21 / (double)this.explosionSize + 0.1);
            this.worldObj.spawnParticle("explode", (var9 + this.explosionX * 1.0) / 2.0, (var11 + this.explosionY * 1.0) / 2.0, (var13 + this.explosionZ * 1.0) / 2.0, var15 *= (var23 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f)), var17 *= var23, var19 *= var23);
            this.worldObj.spawnParticle("smoke", var9, var11, var13, var15, var17, var19);
            if (var7 > 0) {
                Block.blocksList[var7].dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 0.3f);
                this.worldObj.setBlockWithNotify(var4, var5, var6, 0);
                Block.blocksList[var7].onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6);
            }
            --var2;
        }
    }
}

