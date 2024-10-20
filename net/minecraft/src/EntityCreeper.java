package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityCreeper
extends EntityMobs {
    int timeSinceIgnited;
    int lastActiveTime;

    public EntityCreeper(World var1) {
        super(var1);
        this.texture = "/mob/creeper.png";
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)-1);
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
    public void onUpdate() {
        this.lastActiveTime = this.timeSinceIgnited;
        if (this.worldObj.multiplayerWorld) {
            int var1 = this.func_21091_q();
            if (var1 > 0 && this.timeSinceIgnited == 0) {
                this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += var1;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= 30) {
                this.timeSinceIgnited = 30;
            }
        }
        super.onUpdate();
    }

    @Override
    protected String getHurtSound() {
        return "mob.creeper";
    }

    @Override
    protected String getDeathSound() {
        return "mob.creeperdeath";
    }

    @Override
    public void onDeath(Entity var1) {
        super.onDeath(var1);
        if (var1 instanceof EntitySkeleton) {
            this.dropItem(Item.record13.shiftedIndex + this.rand.nextInt(2), 1);
        }
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        int var3 = this.func_21091_q();
        if (var3 <= 0 && var2 < 3.0f || var3 > 0 && var2 < 7.0f) {
            if (this.timeSinceIgnited == 0) {
                this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 0.5f);
            }
            this.func_21090_e(1);
            ++this.timeSinceIgnited;
            if (this.timeSinceIgnited >= 30) {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0f);
                this.setEntityDead();
            }
            this.hasAttacked = true;
        } else {
            this.func_21090_e(-1);
            --this.timeSinceIgnited;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
        }
    }

    public float setCreeperFlashTime(float var1) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * var1) / 28.0f;
    }

    @Override
    protected int getDropItemId() {
        return Item.gunpowder.shiftedIndex;
    }

    private int func_21091_q() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    private void func_21090_e(int var1) {
        this.dataWatcher.updateObject(16, (byte)var1);
    }
}

