package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityTNTPrimed
extends Entity {
    public int fuse = 0;

    public EntityTNTPrimed(World var1) {
        super(var1);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
    }

    public EntityTNTPrimed(World var1, double var2, double var4, double var6) {
        this(var1);
        this.setPosition(var2, var4, var6);
        float var8 = (float)(Math.random() * 3.1415927410125732 * 2.0);
        this.motionX = -MathHelper.sin(var8 * (float)Math.PI / 180.0f) * 0.02f;
        this.motionY = 0.2f;
        this.motionZ = -MathHelper.cos(var8 * (float)Math.PI / 180.0f) * 0.02f;
        this.fuse = 80;
        this.prevPosX = var2;
        this.prevPosY = var4;
        this.prevPosZ = var6;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= (double)0.04f;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.98f;
        this.motionY *= (double)0.98f;
        this.motionZ *= (double)0.98f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
            this.motionY *= -0.5;
        }
        if (this.fuse-- <= 0) {
            this.setEntityDead();
            this.explode();
        } else {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    private void explode() {
        float var1 = 4.0f;
        this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, var1);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
        var1.setByte("Fuse", (byte)this.fuse);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {
        this.fuse = var1.getByte("Fuse");
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }
}

