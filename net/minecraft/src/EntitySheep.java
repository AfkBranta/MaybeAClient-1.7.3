/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySheep
extends EntityAnimals {
    public static final float[][] fleeceColorTable = new float[][]{{1.0f, 1.0f, 1.0f}, {0.95f, 0.7f, 0.2f}, {0.9f, 0.5f, 0.85f}, {0.6f, 0.7f, 0.95f}, {0.9f, 0.9f, 0.2f}, {0.5f, 0.8f, 0.1f}, {0.95f, 0.7f, 0.8f}, {0.3f, 0.3f, 0.3f}, {0.6f, 0.6f, 0.6f}, {0.3f, 0.6f, 0.7f}, {0.7f, 0.4f, 0.9f}, {0.2f, 0.4f, 0.8f}, {0.5f, 0.4f, 0.3f}, {0.4f, 0.5f, 0.2f}, {0.8f, 0.3f, 0.3f}, {0.1f, 0.1f, 0.1f}};

    public EntitySheep(World var1) {
        super(var1);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9f, 1.3f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (!this.worldObj.multiplayerWorld && !this.getSheared() && var1 instanceof EntityLiving) {
            this.setSheared(true);
            int var3 = 1 + this.rand.nextInt(3);
            int var4 = 0;
            while (var4 < var3) {
                EntityItem var5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0f);
                var5.motionY += (double)(this.rand.nextFloat() * 0.05f);
                var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                ++var4;
            }
        }
        return super.attackEntityFrom(var1, var2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setBoolean("Sheared", this.getSheared());
        var1.setByte("Color", (byte)this.getFleeceColor());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.setSheared(var1.getBoolean("Sheared"));
        this.setFleeceColor(var1.getByte("Color"));
    }

    @Override
    protected String getLivingSound() {
        return "mob.sheep";
    }

    @Override
    protected String getHurtSound() {
        return "mob.sheep";
    }

    @Override
    protected String getDeathSound() {
        return "mob.sheep";
    }

    public int getFleeceColor() {
        return this.dataWatcher.getWatchableObjectByte(16) & 0xF;
    }

    public void setFleeceColor(int var1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte)(var2 & 0xF0 | var1 & 0xF));
    }

    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0;
    }

    public void setSheared(boolean var1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (var1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x10));
        } else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFEF));
        }
    }

    public static int getRandomFleeceColor(Random var0) {
        int var1 = var0.nextInt(100);
        if (var1 < 5) {
            return 15;
        }
        if (var1 < 10) {
            return 7;
        }
        if (var1 < 15) {
            return 8;
        }
        if (var1 < 18) {
            return 12;
        }
        return var0.nextInt(500) == 0 ? 6 : 0;
    }
}

