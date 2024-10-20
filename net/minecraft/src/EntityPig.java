package net.minecraft.src;

import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityPig
extends EntityAnimals {
    public EntityPig(World var1) {
        super(var1);
        this.texture = "/mob/pig.png";
        this.setSize(0.9f, 0.9f);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setBoolean("Saddle", this.getSaddled());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.setSaddled(var1.getBoolean("Saddle"));
    }

    @Override
    protected String getLivingSound() {
        return "mob.pig";
    }

    @Override
    protected String getHurtSound() {
        return "mob.pig";
    }

    @Override
    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    @Override
    public boolean interact(EntityPlayer var1) {
        if (!this.getSaddled() || this.worldObj.multiplayerWorld || this.riddenByEntity != null && this.riddenByEntity != var1) {
            return false;
        }
        var1.mountEntity(this);
        return true;
    }

    @Override
    protected int getDropItemId() {
        return Item.porkRaw.shiftedIndex;
    }

    public boolean getSaddled() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setSaddled(boolean var1) {
        if (var1) {
            this.dataWatcher.updateObject(16, (byte)1);
        } else {
            this.dataWatcher.updateObject(16, (byte)0);
        }
    }
}

