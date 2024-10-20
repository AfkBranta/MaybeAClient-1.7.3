package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityFallingSand
extends Entity {
    public int blockID;
    public int fallTime = 0;

    public EntityFallingSand(World var1) {
        super(var1);
    }

    public EntityFallingSand(World var1, double var2, double var4, double var6, int var8) {
        super(var1);
        this.blockID = var8;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(var2, var4, var6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = var2;
        this.prevPosY = var4;
        this.prevPosZ = var6;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        if (this.blockID == 0) {
            this.setEntityDead();
        } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= (double)0.04f;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98f;
            this.motionY *= (double)0.98f;
            this.motionZ *= (double)0.98f;
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.posY);
            int var3 = MathHelper.floor_double(this.posZ);
            if (this.worldObj.getBlockId(var1, var2, var3) == this.blockID) {
                this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
            }
            if (this.onGround) {
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
                this.motionY *= -0.5;
                this.setEntityDead();
                if (!(this.worldObj.canBlockBePlacedAt(this.blockID, var1, var2, var3, true) && this.worldObj.setBlockWithNotify(var1, var2, var3, this.blockID) || this.worldObj.multiplayerWorld)) {
                    this.dropItem(this.blockID, 1);
                }
            } else if (this.fallTime > 100 && !this.worldObj.multiplayerWorld) {
                this.dropItem(this.blockID, 1);
                this.setEntityDead();
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound var1) {
        var1.setByte("Tile", (byte)this.blockID);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound var1) {
        this.blockID = var1.getByte("Tile") & 0xFF;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public World func_465_i() {
        return this.worldObj;
    }
}

