/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public abstract class EntityAnimals
extends EntityCreature {
    public EntityAnimals(World var1) {
        super(var1);
    }

    @Override
    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return this.worldObj.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID ? 10.0f : this.worldObj.getLightBrightness(var1, var2, var3) - 0.5f;
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
        return this.worldObj.getBlockId(var1, (var2 = MathHelper.floor_double(this.boundingBox.minY)) - 1, var3 = MathHelper.floor_double(this.posZ)) == Block.grass.blockID && this.worldObj.getBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }
}

