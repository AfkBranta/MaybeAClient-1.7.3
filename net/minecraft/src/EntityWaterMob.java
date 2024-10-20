/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityCreature;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityWaterMob
extends EntityCreature {
    public EntityWaterMob(World var1) {
        super(var1);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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
        return this.worldObj.checkIfAABBIsClear(this.boundingBox);
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }
}

