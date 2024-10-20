/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IMobs;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityMobs
extends EntityCreature
implements IMobs {
    protected int attackStrength = 2;

    public EntityMobs(World var1) {
        super(var1);
        this.health = 20;
    }

    @Override
    public void onLivingUpdate() {
        float var1 = this.getEntityBrightness(1.0f);
        if (var1 > 0.5f) {
            this.field_9344_ag += 2;
        }
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.difficultySetting == 0) {
            this.setEntityDead();
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (super.attackEntityFrom(var1, var2)) {
            if (this.riddenByEntity != var1 && this.ridingEntity != var1) {
                if (var1 != this) {
                    this.playerToAttack = var1;
                }
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        if (this.attackTime <= 0 && var2 < 2.0f && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            var1.attackEntityFrom(this, this.attackStrength);
        }
    }

    @Override
    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return 0.5f - this.worldObj.getLightBrightness(var1, var2, var3);
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
    public boolean getCanSpawnHere() {
        int var3;
        int var2;
        int var1 = MathHelper.floor_double(this.posX);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2 = MathHelper.floor_double(this.boundingBox.minY), var3 = MathHelper.floor_double(this.posZ)) > this.rand.nextInt(32)) {
            return false;
        }
        int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
        return var4 <= this.rand.nextInt(8) && super.getCanSpawnHere();
    }
}

