/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityEgg
extends Entity {
    private int field_20056_b = -1;
    private int field_20055_c = -1;
    private int field_20054_d = -1;
    private int field_20053_e = 0;
    private boolean field_20052_f = false;
    public int field_20057_a = 0;
    private EntityLiving field_20051_g;
    private int field_20050_h;
    private int field_20049_i = 0;

    public EntityEgg(World var1) {
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

    public EntityEgg(World var1, EntityLiving var2) {
        super(var1);
        this.field_20051_g = var2;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * var3;
        this.func_20048_a(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    public EntityEgg(World var1, double var2, double var4, double var6) {
        super(var1);
        this.field_20050_h = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(var2, var4, var6);
        this.yOffset = 0.0f;
    }

    public void func_20048_a(double var1, double var3, double var5, float var7, float var8) {
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
        this.field_20050_h = 0;
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
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.field_20057_a > 0) {
            --this.field_20057_a;
        }
        if (this.field_20052_f) {
            int var1 = this.worldObj.getBlockId(this.field_20056_b, this.field_20055_c, this.field_20054_d);
            if (var1 == this.field_20053_e) {
                ++this.field_20050_h;
                if (this.field_20050_h == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.field_20052_f = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.field_20050_h = 0;
            this.field_20049_i = 0;
        } else {
            ++this.field_20049_i;
        }
        Vec3D var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var15, var2);
        var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (var3 != null) {
            var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
        }
        if (!this.worldObj.multiplayerWorld) {
            Entity var4 = null;
            List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;
            int var8 = 0;
            while (var8 < var5.size()) {
                double var13;
                float var10;
                AxisAlignedBB var11;
                MovingObjectPosition var12;
                Entity var9 = (Entity)var5.get(var8);
                if (var9.canBeCollidedWith() && (var9 != this.field_20051_g || this.field_20049_i >= 5) && (var12 = (var11 = var9.boundingBox.expand(var10 = 0.3f, var10, var10)).func_1169_a(var15, var2)) != null && ((var13 = var15.distanceTo(var12.hitVec)) < var6 || var6 == 0.0)) {
                    var4 = var9;
                    var6 = var13;
                }
                ++var8;
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
        }
        if (var3 != null) {
            if (var3.entityHit != null) {
                var3.entityHit.attackEntityFrom(this.field_20051_g, 0);
            }
            if (!this.worldObj.multiplayerWorld && this.rand.nextInt(8) == 0) {
                int var16 = 1;
                if (this.rand.nextInt(32) == 0) {
                    var16 = 4;
                }
                int var17 = 0;
                while (var17 < var16) {
                    EntityChicken var21 = new EntityChicken(this.worldObj);
                    var21.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    this.worldObj.entityJoinedWorld(var21);
                    ++var17;
                }
            }
            int var18 = 0;
            while (var18 < 8) {
                this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                ++var18;
            }
            this.setEntityDead();
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var20) * 180.0 / 3.1415927410125732);
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
        float var19 = 0.99f;
        float var22 = 0.03f;
        if (this.handleWaterMovement()) {
            int var7 = 0;
            while (var7 < 4) {
                float var23 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var23, this.posY - this.motionY * (double)var23, this.posZ - this.motionZ * (double)var23, this.motionX, this.motionY, this.motionZ);
                ++var7;
            }
            var19 = 0.8f;
        }
        this.motionX *= (double)var19;
        this.motionY *= (double)var19;
        this.motionZ *= (double)var19;
        this.motionY -= (double)var22;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        var1.setShort("xTile", (short)this.field_20056_b);
        var1.setShort("yTile", (short)this.field_20055_c);
        var1.setShort("zTile", (short)this.field_20054_d);
        var1.setByte("inTile", (byte)this.field_20053_e);
        var1.setByte("shake", (byte)this.field_20057_a);
        var1.setByte("inGround", (byte)(this.field_20052_f ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        this.field_20056_b = var1.getShort("xTile");
        this.field_20055_c = var1.getShort("yTile");
        this.field_20054_d = var1.getShort("zTile");
        this.field_20053_e = var1.getByte("inTile") & 0xFF;
        this.field_20057_a = var1.getByte("shake") & 0xFF;
        this.field_20052_f = var1.getByte("inGround") == 1;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer var1) {
        if (this.field_20052_f && this.field_20051_g == var1 && this.field_20057_a <= 0 && var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
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

