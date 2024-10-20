/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StatList;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityFish
extends Entity {
    private int tileX = -1;
    private int tileY = -1;
    private int tileZ = -1;
    private int field_4092_g = 0;
    private boolean field_4091_h = false;
    public int field_4098_a = 0;
    public EntityPlayer angler;
    private int field_4090_i;
    private int field_4089_j = 0;
    private int field_4088_k = 0;
    public Entity field_4096_c = null;
    private int field_6388_l;
    private double field_6387_m;
    private double field_6386_n;
    private double field_6385_o;
    private double field_6384_p;
    private double field_6383_q;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntityFish(World var1) {
        super(var1);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double var1) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        return var1 < (var3 *= 64.0) * var3;
    }

    public EntityFish(World var1, double var2, double var4, double var6) {
        this(var1);
        this.setPosition(var2, var4, var6);
    }

    public EntityFish(World var1, EntityPlayer var2) {
        super(var1);
        this.angler = var2;
        this.angler.fishEntity = this;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(var2.posX, var2.posY + 1.62 - (double)var2.yOffset, var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.func_4042_a(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    public void func_4042_a(double var1, double var3, double var5, float var7, float var8) {
        float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= (double)var9;
        var3 /= (double)var9;
        var5 /= (double)var9;
        var1 += this.rand.nextGaussian() * (double)0.0075f * (double)var8;
        var3 += this.rand.nextGaussian() * (double)0.0075f * (double)var8;
        var5 += this.rand.nextGaussian() * (double)0.0075f * (double)var8;
        this.motionX = var1 *= (double)var7;
        this.motionY = var3 *= (double)var7;
        this.motionZ = var5 *= (double)var7;
        float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0 / 3.1415927410125732);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, var10) * 180.0 / 3.1415927410125732);
        this.field_4090_i = 0;
    }

    @Override
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.field_6387_m = var1;
        this.field_6386_n = var3;
        this.field_6385_o = var5;
        this.field_6384_p = var7;
        this.field_6383_q = var8;
        this.field_6388_l = var9;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @Override
    public void setVelocity(double var1, double var3, double var5) {
        this.velocityX = this.motionX = var1;
        this.velocityY = this.motionY = var3;
        this.velocityZ = this.motionZ = var5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_6388_l > 0) {
            double var21 = this.posX + (this.field_6387_m - this.posX) / (double)this.field_6388_l;
            double var22 = this.posY + (this.field_6386_n - this.posY) / (double)this.field_6388_l;
            double var23 = this.posZ + (this.field_6385_o - this.posZ) / (double)this.field_6388_l;
            double var7 = this.field_6384_p - (double)this.rotationYaw;
            while (var7 < -180.0) {
                var7 += 360.0;
            }
            while (var7 >= 180.0) {
                var7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.field_6388_l);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.field_6383_q - (double)this.rotationPitch) / (double)this.field_6388_l);
            --this.field_6388_l;
            this.setPosition(var21, var22, var23);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else {
            double var13;
            if (!this.worldObj.multiplayerWorld) {
                ItemStack var1 = this.angler.getCurrentEquippedItem();
                if (this.angler.isDead || !this.angler.isEntityAlive() || var1 == null || var1.getItem() != Item.fishingRod || this.getDistanceSqToEntity(this.angler) > 1024.0) {
                    this.setEntityDead();
                    this.angler.fishEntity = null;
                    return;
                }
                if (this.field_4096_c != null) {
                    if (!this.field_4096_c.isDead) {
                        this.posX = this.field_4096_c.posX;
                        this.posY = this.field_4096_c.boundingBox.minY + (double)this.field_4096_c.height * 0.8;
                        this.posZ = this.field_4096_c.posZ;
                        return;
                    }
                    this.field_4096_c = null;
                }
            }
            if (this.field_4098_a > 0) {
                --this.field_4098_a;
            }
            if (this.field_4091_h) {
                int var19 = this.worldObj.getBlockId(this.tileX, this.tileY, this.tileZ);
                if (var19 == this.field_4092_g) {
                    ++this.field_4090_i;
                    if (this.field_4090_i == 1200) {
                        this.setEntityDead();
                    }
                    return;
                }
                this.field_4091_h = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.field_4090_i = 0;
                this.field_4089_j = 0;
            } else {
                ++this.field_4089_j;
            }
            Vec3D var20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
            Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var20, var2);
            var20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
            var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var3 != null) {
                var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
            }
            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;
            int var8 = 0;
            while (var8 < var5.size()) {
                float var10;
                AxisAlignedBB var11;
                MovingObjectPosition var12;
                Entity var9 = (Entity)var5.get(var8);
                if (var9.canBeCollidedWith() && (var9 != this.angler || this.field_4089_j >= 5) && (var12 = (var11 = var9.boundingBox.expand(var10 = 0.3f, var10, var10)).func_1169_a(var20, var2)) != null && ((var13 = var20.distanceTo(var12.hitVec)) < var6 || var6 == 0.0)) {
                    var4 = var9;
                    var6 = var13;
                }
                ++var8;
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
            if (var3 != null) {
                if (var3.entityHit != null) {
                    if (var3.entityHit.attackEntityFrom(this.angler, 0)) {
                        this.field_4096_c = var3.entityHit;
                    }
                } else {
                    this.field_4091_h = true;
                }
            }
            if (!this.field_4091_h) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                float var24 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
                this.rotationPitch = (float)(Math.atan2(this.motionY, var24) * 180.0 / 3.1415927410125732);
                while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                    this.prevRotationPitch -= 360.0f;
                }
                while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                    this.prevRotationPitch += 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                    this.prevRotationYaw -= 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                    this.prevRotationYaw += 360.0f;
                }
                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
                float var25 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    var25 = 0.5f;
                }
                int var26 = 5;
                double var27 = 0.0;
                int var28 = 0;
                while (var28 < var26) {
                    double var14 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var28 + 0) / (double)var26 - 0.125 + 0.125;
                    double var16 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var28 + 1) / (double)var26 - 0.125 + 0.125;
                    AxisAlignedBB var18 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, var14, this.boundingBox.minZ, this.boundingBox.maxX, var16, this.boundingBox.maxZ);
                    if (this.worldObj.isAABBInMaterial(var18, Material.water)) {
                        var27 += 1.0 / (double)var26;
                    }
                    ++var28;
                }
                if (var27 > 0.0) {
                    if (this.field_4088_k > 0) {
                        --this.field_4088_k;
                    } else if (this.rand.nextInt(500) == 0) {
                        this.field_4088_k = this.rand.nextInt(30) + 10;
                        this.motionY -= (double)0.2f;
                        this.worldObj.playSoundAtEntity(this, "random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                        float var29 = MathHelper.floor_double(this.boundingBox.minY);
                        int var30 = 0;
                        while ((float)var30 < 1.0f + this.width * 20.0f) {
                            float var15 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            float var31 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.worldObj.spawnParticle("bubble", this.posX + (double)var15, var29 + 1.0f, this.posZ + (double)var31, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ);
                            ++var30;
                        }
                        var30 = 0;
                        while ((float)var30 < 1.0f + this.width * 20.0f) {
                            float var15 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            float var31 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.worldObj.spawnParticle("splash", this.posX + (double)var15, var29 + 1.0f, this.posZ + (double)var31, this.motionX, this.motionY, this.motionZ);
                            ++var30;
                        }
                    }
                }
                if (this.field_4088_k > 0) {
                    this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
                }
                var13 = var27 * 2.0 - 1.0;
                this.motionY += (double)0.04f * var13;
                if (var27 > 0.0) {
                    var25 = (float)((double)var25 * 0.9);
                    this.motionY *= 0.8;
                }
                this.motionX *= (double)var25;
                this.motionY *= (double)var25;
                this.motionZ *= (double)var25;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setShort("xTile", (short)this.tileX);
        var1.setShort("yTile", (short)this.tileY);
        var1.setShort("zTile", (short)this.tileZ);
        var1.setByte("inTile", (byte)this.field_4092_g);
        var1.setByte("shake", (byte)this.field_4098_a);
        var1.setByte("inGround", (byte)(this.field_4091_h ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.tileX = var1.getShort("xTile");
        this.tileY = var1.getShort("yTile");
        this.tileZ = var1.getShort("zTile");
        this.field_4092_g = var1.getByte("inTile") & 0xFF;
        this.field_4098_a = var1.getByte("shake") & 0xFF;
        this.field_4091_h = var1.getByte("inGround") == 1;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public int catchFish() {
        int var1 = 0;
        if (this.field_4096_c != null) {
            double var2 = this.angler.posX - this.posX;
            double var4 = this.angler.posY - this.posY;
            double var6 = this.angler.posZ - this.posZ;
            double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
            double var10 = 0.1;
            Entity var10000 = this.field_4096_c;
            var10000.motionX += var2 * var10;
            var10000 = this.field_4096_c;
            var10000.motionY += var4 * var10 + (double)MathHelper.sqrt_double(var8) * 0.08;
            var10000 = this.field_4096_c;
            var10000.motionZ += var6 * var10;
            var1 = 3;
        } else if (this.field_4088_k > 0) {
            EntityItem var13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.fishRaw));
            double var3 = this.angler.posX - this.posX;
            double var5 = this.angler.posY - this.posY;
            double var7 = this.angler.posZ - this.posZ;
            double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 0.1;
            var13.motionX = var3 * var11;
            var13.motionY = var5 * var11 + (double)MathHelper.sqrt_double(var9) * 0.08;
            var13.motionZ = var7 * var11;
            this.worldObj.entityJoinedWorld(var13);
            this.angler.addStat(StatList.field_25160_x, 1);
            var1 = 1;
        }
        if (this.field_4091_h) {
            var1 = 2;
        }
        this.setEntityDead();
        this.angler.fishEntity = null;
        return var1;
    }
}

