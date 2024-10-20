package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityWaterMob;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySquid
extends EntityWaterMob {
    public float field_21089_a = 0.0f;
    public float field_21088_b = 0.0f;
    public float field_21087_c = 0.0f;
    public float field_21086_f = 0.0f;
    public float field_21085_g = 0.0f;
    public float field_21084_h = 0.0f;
    public float field_21083_i = 0.0f;
    public float field_21082_j = 0.0f;
    private float randomMotionSpeed = 0.0f;
    private float field_21080_l = 0.0f;
    private float field_21079_m = 0.0f;
    private float randomMotionVecX = 0.0f;
    private float randomMotionVecY = 0.0f;
    private float randomMotionVecZ = 0.0f;

    public EntitySquid(World var1) {
        super(var1);
        this.texture = "/mob/squid.png";
        this.setSize(0.95f, 0.95f);
        this.field_21080_l = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected int getDropItemId() {
        return 0;
    }

    @Override
    protected void dropFewItems() {
        int var1 = this.rand.nextInt(3) + 1;
        int var2 = 0;
        while (var2 < var1) {
            this.entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0f);
            ++var2;
        }
    }

    @Override
    public boolean interact(EntityPlayer var1) {
        return false;
    }

    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.6f, 0.0), Material.water, this);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_21088_b = this.field_21089_a;
        this.field_21086_f = this.field_21087_c;
        this.field_21084_h = this.field_21085_g;
        this.field_21082_j = this.field_21083_i;
        this.field_21085_g += this.field_21080_l;
        if (this.field_21085_g > (float)Math.PI * 2) {
            this.field_21085_g -= (float)Math.PI * 2;
            if (this.rand.nextInt(10) == 0) {
                this.field_21080_l = 1.0f / (this.rand.nextFloat() + 1.0f) * 0.2f;
            }
        }
        if (this.handleWaterMovement()) {
            float var1;
            if (this.field_21085_g < (float)Math.PI) {
                var1 = this.field_21085_g / (float)Math.PI;
                this.field_21083_i = MathHelper.sin(var1 * var1 * (float)Math.PI) * (float)Math.PI * 0.25f;
                if ((double)var1 > 0.75) {
                    this.randomMotionSpeed = 1.0f;
                    this.field_21079_m = 1.0f;
                } else {
                    this.field_21079_m *= 0.8f;
                }
            } else {
                this.field_21083_i = 0.0f;
                this.randomMotionSpeed *= 0.9f;
                this.field_21079_m *= 0.99f;
            }
            if (!this.field_9343_G) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }
            var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float)Math.atan2(this.motionX, this.motionZ)) * 180.0f / (float)Math.PI - this.renderYawOffset) * 0.1f;
            this.rotationYaw = this.renderYawOffset;
            this.field_21087_c += (float)Math.PI * this.field_21079_m * 1.5f;
            this.field_21089_a += (-((float)Math.atan2(var1, this.motionY)) * 180.0f / (float)Math.PI - this.field_21089_a) * 0.1f;
        } else {
            this.field_21083_i = MathHelper.abs(MathHelper.sin(this.field_21085_g)) * (float)Math.PI * 0.25f;
            if (!this.field_9343_G) {
                this.motionX = 0.0;
                this.motionY -= 0.08;
                this.motionY *= (double)0.98f;
                this.motionZ = 0.0;
            }
            this.field_21089_a = (float)((double)this.field_21089_a + (double)(-90.0f - this.field_21089_a) * 0.02);
        }
    }

    @Override
    public void moveEntityWithHeading(float var1, float var2) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    @Override
    protected void updatePlayerActionState() {
        if (this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0f && this.randomMotionVecY == 0.0f && this.randomMotionVecZ == 0.0f) {
            float var1 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
            this.randomMotionVecX = MathHelper.cos(var1) * 0.2f;
            this.randomMotionVecY = -0.1f + this.rand.nextFloat() * 0.2f;
            this.randomMotionVecZ = MathHelper.sin(var1) * 0.2f;
        }
    }
}

