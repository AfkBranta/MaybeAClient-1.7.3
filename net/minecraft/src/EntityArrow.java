/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityArrow
extends Entity {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private boolean inGround = false;
    public int arrowShake = 0;
    public EntityLiving archer;
    private int timeTillDeath;
    private int flyTime = 0;

    public EntityArrow(World var1) {
        super(var1);
        this.setSize(0.5f, 0.5f);
    }

    public EntityArrow(World var1, double var2, double var4, double var6) {
        super(var1);
        this.setSize(0.5f, 0.5f);
        this.setPosition(var2, var4, var6);
        this.yOffset = 0.0f;
    }

    public EntityArrow(World var1, EntityLiving var2) {
        super(var1);
        this.archer = var2;
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI);
        this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    @Override
    protected void entityInit() {
    }

    public void setArrowHeading(double var1, double var3, double var5, float var7, float var8) {
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
        this.timeTillDeath = 0;
    }

    @Override
    public void setVelocity(double var1, double var3, double var5) {
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var7 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0 / 3.1415927410125732);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, var7) * 180.0 / 3.1415927410125732);
        }
    }

    @Override
    public void onUpdate() {
        float var10;
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.1415927410125732);
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            int var15 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
            if (var15 == this.inTile) {
                ++this.timeTillDeath;
                if (this.timeTillDeath == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.timeTillDeath = 0;
            this.flyTime = 0;
        } else {
            ++this.flyTime;
        }
        Vec3D var16 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var16, var2);
        var16 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (var3 != null) {
            var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
        }
        Entity var4 = null;
        List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
        double var6 = 0.0;
        int var8 = 0;
        while (var8 < var5.size()) {
            double var13;
            AxisAlignedBB var11;
            MovingObjectPosition var12;
            Entity var9 = (Entity)var5.get(var8);
            if (var9.canBeCollidedWith() && (var9 != this.archer || this.flyTime >= 5) && (var12 = (var11 = var9.boundingBox.expand(var10 = 0.3f, var10, var10)).func_1169_a(var16, var2)) != null && ((var13 = var16.distanceTo(var12.hitVec)) < var6 || var6 == 0.0)) {
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
                if (var3.entityHit.attackEntityFrom(this.archer, 4)) {
                    this.worldObj.playSoundAtEntity(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.setEntityDead();
                } else {
                    this.motionX *= (double)-0.1f;
                    this.motionY *= (double)-0.1f;
                    this.motionZ *= (double)-0.1f;
                    this.rotationYaw += 180.0f;
                    this.prevRotationYaw += 180.0f;
                    this.flyTime = 0;
                }
            } else {
                this.xTile = var3.blockX;
                this.yTile = var3.blockY;
                this.zTile = var3.blockZ;
                this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
                this.motionX = (float)(var3.hitVec.xCoord - this.posX);
                this.motionY = (float)(var3.hitVec.yCoord - this.posY);
                this.motionZ = (float)(var3.hitVec.zCoord - this.posZ);
                float var17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double)var17 * (double)0.05f;
                this.posY -= this.motionY / (double)var17 * (double)0.05f;
                this.posZ -= this.motionZ / (double)var17 * (double)0.05f;
                this.worldObj.playSoundAtEntity(this, "random.drr", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                this.inGround = true;
                this.arrowShake = 7;
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var17) * 180.0 / 3.1415927410125732);
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
        float var18 = 0.99f;
        var10 = 0.03f;
        if (this.handleWaterMovement()) {
            int var19 = 0;
            while (var19 < 4) {
                float var20 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var20, this.posY - this.motionY * (double)var20, this.posZ - this.motionZ * (double)var20, this.motionX, this.motionY, this.motionZ);
                ++var19;
            }
            var18 = 0.8f;
        }
        this.motionX *= (double)var18;
        this.motionY *= (double)var18;
        this.motionZ *= (double)var18;
        this.motionY -= (double)var10;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setShort("xTile", (short)this.xTile);
        var1.setShort("yTile", (short)this.yTile);
        var1.setShort("zTile", (short)this.zTile);
        var1.setByte("inTile", (byte)this.inTile);
        var1.setByte("shake", (byte)this.arrowShake);
        var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.xTile = var1.getShort("xTile");
        this.yTile = var1.getShort("yTile");
        this.zTile = var1.getShort("zTile");
        this.inTile = var1.getByte("inTile") & 0xFF;
        this.arrowShake = var1.getByte("shake") & 0xFF;
        this.inGround = var1.getByte("inGround") == 1;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer var1) {
        if (!this.worldObj.multiplayerWorld && this.inGround && this.archer == var1 && this.arrowShake <= 0 && var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            var1.onItemPickup(this, 1);
            this.setEntityDead();
        }
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }
}

