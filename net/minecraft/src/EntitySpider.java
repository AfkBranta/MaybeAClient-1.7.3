package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySpider
extends EntityMobs {
    public EntitySpider(World var1) {
        super(var1);
        this.texture = "/mob/spider.png";
        this.setSize(1.4f, 0.9f);
        this.moveSpeed = 0.8f;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.75 - 0.5;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected Entity findPlayerToAttack() {
        float var1 = this.getEntityBrightness(1.0f);
        if (var1 < 0.5f) {
            double var2 = 16.0;
            return this.worldObj.getClosestPlayerToEntity(this, var2);
        }
        return null;
    }

    @Override
    protected String getLivingSound() {
        return "mob.spider";
    }

    @Override
    protected String getHurtSound() {
        return "mob.spider";
    }

    @Override
    protected String getDeathSound() {
        return "mob.spiderdeath";
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        float var3 = this.getEntityBrightness(1.0f);
        if (var3 > 0.5f && this.rand.nextInt(100) == 0) {
            this.playerToAttack = null;
        } else if (var2 > 2.0f && var2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double var4 = var1.posX - this.posX;
                double var6 = var1.posZ - this.posZ;
                float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
                this.motionX = var4 / (double)var8 * 0.5 * (double)0.8f + this.motionX * (double)0.2f;
                this.motionZ = var6 / (double)var8 * 0.5 * (double)0.8f + this.motionZ * (double)0.2f;
                this.motionY = 0.4f;
            }
        } else {
            super.attackEntity(var1, var2);
        }
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
    protected int getDropItemId() {
        return Item.silk.shiftedIndex;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }
}

