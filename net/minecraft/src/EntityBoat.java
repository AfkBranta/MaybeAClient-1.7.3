package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityBoat
extends Entity {
    public int boatCurrentDamage = 0;
    public int boatTimeSinceHit = 0;
    public int boatRockDirection = 1;
    private int field_9394_d;
    private double field_9393_e;
    private double field_9392_f;
    private double field_9391_g;
    private double field_9390_h;
    private double field_9389_i;
    private double field_9388_j;
    private double field_9387_k;
    private double field_9386_l;

    public EntityBoat(World var1) {
        super(var1);
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity var1) {
        return var1.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntityBoat(World var1, double var2, double var4, double var6) {
        this(var1);
        this.setPosition(var2, var4 + (double)this.yOffset, var6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = var2;
        this.prevPosY = var4;
        this.prevPosZ = var6;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - (double)0.3f;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (!this.worldObj.multiplayerWorld && !this.isDead) {
            this.boatRockDirection = -this.boatRockDirection;
            this.boatTimeSinceHit = 10;
            this.boatCurrentDamage += var2 * 10;
            this.setBeenAttacked();
            if (this.boatCurrentDamage > 40) {
                int var3 = 0;
                while (var3 < 3) {
                    this.dropItemWithOffset(Block.planks.blockID, 1, 0.0f);
                    ++var3;
                }
                var3 = 0;
                while (var3 < 2) {
                    this.dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0f);
                    ++var3;
                }
                this.setEntityDead();
            }
            return true;
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.boatRockDirection = -this.boatRockDirection;
        this.boatTimeSinceHit = 10;
        this.boatCurrentDamage += this.boatCurrentDamage * 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9) {
        this.field_9393_e = var1;
        this.field_9392_f = var3;
        this.field_9391_g = var5;
        this.field_9390_h = var7;
        this.field_9389_i = var8;
        this.field_9394_d = var9 + 4;
        this.motionX = this.field_9388_j;
        this.motionY = this.field_9387_k;
        this.motionZ = this.field_9386_l;
    }

    @Override
    public void setVelocity(double var1, double var3, double var5) {
        this.field_9388_j = this.motionX = var1;
        this.field_9387_k = this.motionY = var3;
        this.field_9386_l = this.motionZ = var5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.boatTimeSinceHit > 0) {
            --this.boatTimeSinceHit;
        }
        if (this.boatCurrentDamage > 0) {
            --this.boatCurrentDamage;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        int var1 = 5;
        double var2 = 0.0;
        int var4 = 0;
        while (var4 < var1) {
            double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 0) / (double)var1 - 0.125;
            double var7 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(var4 + 1) / (double)var1 - 0.125;
            AxisAlignedBB var9 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, var5, this.boundingBox.minZ, this.boundingBox.maxX, var7, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var9, Material.water)) {
                var2 += 1.0 / (double)var1;
            }
            ++var4;
        }
        if (this.worldObj.multiplayerWorld) {
            if (this.field_9394_d > 0) {
                double var23 = this.posX + (this.field_9393_e - this.posX) / (double)this.field_9394_d;
                double var6 = this.posY + (this.field_9392_f - this.posY) / (double)this.field_9394_d;
                double var8 = this.posZ + (this.field_9391_g - this.posZ) / (double)this.field_9394_d;
                double var10 = this.field_9390_h - (double)this.rotationYaw;
                while (var10 < -180.0) {
                    var10 += 360.0;
                }
                while (var10 >= 180.0) {
                    var10 -= 360.0;
                }
                this.rotationYaw = (float)((double)this.rotationYaw + var10 / (double)this.field_9394_d);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9389_i - (double)this.rotationPitch) / (double)this.field_9394_d);
                --this.field_9394_d;
                this.setPosition(var23, var6, var8);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                double var23 = this.posX + this.motionX;
                double var6 = this.posY + this.motionY;
                double var8 = this.posZ + this.motionZ;
                this.setPosition(var23, var6, var8);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= (double)0.99f;
                this.motionY *= (double)0.95f;
                this.motionZ *= (double)0.99f;
            }
        } else {
            double var12;
            double var10;
            double var6;
            double var23 = var2 * 2.0 - 1.0;
            this.motionY += (double)0.04f * var23;
            if (this.riddenByEntity != null) {
                this.motionX += this.riddenByEntity.motionX * 0.2;
                this.motionZ += this.riddenByEntity.motionZ * 0.2;
            }
            if (this.motionX < -(var6 = 0.4)) {
                this.motionX = -var6;
            }
            if (this.motionX > var6) {
                this.motionX = var6;
            }
            if (this.motionZ < -var6) {
                this.motionZ = -var6;
            }
            if (this.motionZ > var6) {
                this.motionZ = var6;
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            double var8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var8 > 0.15) {
                var10 = Math.cos((double)this.rotationYaw * Math.PI / 180.0);
                var12 = Math.sin((double)this.rotationYaw * Math.PI / 180.0);
                int var14 = 0;
                while ((double)var14 < 1.0 + var8 * 60.0) {
                    double var21;
                    double var19;
                    double var15 = this.rand.nextFloat() * 2.0f - 1.0f;
                    double var17 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                    if (this.rand.nextBoolean()) {
                        var19 = this.posX - var10 * var15 * 0.8 + var12 * var17;
                        var21 = this.posZ - var12 * var15 * 0.8 - var10 * var17;
                        this.worldObj.spawnParticle("splash", var19, this.posY - 0.125, var21, this.motionX, this.motionY, this.motionZ);
                    } else {
                        var19 = this.posX + var10 + var12 * var15 * 0.7;
                        var21 = this.posZ + var12 - var10 * var15 * 0.7;
                        this.worldObj.spawnParticle("splash", var19, this.posY - 0.125, var21, this.motionX, this.motionY, this.motionZ);
                    }
                    ++var14;
                }
            }
            if (this.isCollidedHorizontally && var8 > 0.15) {
                if (!this.worldObj.multiplayerWorld) {
                    this.setEntityDead();
                    int var24 = 0;
                    while (var24 < 3) {
                        this.dropItemWithOffset(Block.planks.blockID, 1, 0.0f);
                        ++var24;
                    }
                    var24 = 0;
                    while (var24 < 2) {
                        this.dropItemWithOffset(Item.stick.shiftedIndex, 1, 0.0f);
                        ++var24;
                    }
                }
            } else {
                this.motionX *= (double)0.99f;
                this.motionY *= (double)0.95f;
                this.motionZ *= (double)0.99f;
            }
            this.rotationPitch = 0.0f;
            var10 = this.rotationYaw;
            var12 = this.prevPosX - this.posX;
            double var25 = this.prevPosZ - this.posZ;
            if (var12 * var12 + var25 * var25 > 0.001) {
                var10 = (float)(Math.atan2(var25, var12) * 180.0 / Math.PI);
            }
            double var16 = var10 - (double)this.rotationYaw;
            while (var16 >= 180.0) {
                var16 -= 360.0;
            }
            while (var16 < -180.0) {
                var16 += 360.0;
            }
            if (var16 > 20.0) {
                var16 = 20.0;
            }
            if (var16 < -20.0) {
                var16 = -20.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + var16);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            List var18 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
            if (var18 != null && var18.size() > 0) {
                int var26 = 0;
                while (var26 < var18.size()) {
                    Entity var20 = (Entity)var18.get(var26);
                    if (var20 != this.riddenByEntity && var20.canBePushed() && var20 instanceof EntityBoat) {
                        var20.applyEntityCollision(this);
                    }
                    ++var26;
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }
        }
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double var1 = Math.cos((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
            double var3 = Math.sin((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var3);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public boolean interact(EntityPlayer var1) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != var1) {
            return true;
        }
        if (!this.worldObj.multiplayerWorld) {
            var1.mountEntity(this);
        }
        return true;
    }
}

