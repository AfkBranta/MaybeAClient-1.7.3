/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityMobSpawner
extends TileEntity {
    public int delay = 20;
    private String mobID = "Pig";
    public double yaw;
    public double yaw2 = 0.0;

    public String getMobID() {
        return this.mobID;
    }

    public void setMobID(String var1) {
        this.mobID = var1;
    }

    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5, 16.0) != null;
    }

    @Override
    public void updateEntity() {
        this.yaw2 = this.yaw;
        if (this.anyPlayerInRange()) {
            double var1 = (float)this.xCoord + this.worldObj.rand.nextFloat();
            double var3 = (float)this.yCoord + this.worldObj.rand.nextFloat();
            double var5 = (float)this.zCoord + this.worldObj.rand.nextFloat();
            this.worldObj.spawnParticle("smoke", var1, var3, var5, 0.0, 0.0, 0.0);
            this.worldObj.spawnParticle("flame", var1, var3, var5, 0.0, 0.0, 0.0);
            this.yaw += (double)(1000.0f / ((float)this.delay + 200.0f));
            while (this.yaw > 360.0) {
                this.yaw -= 360.0;
                this.yaw2 -= 360.0;
            }
            if (this.delay == -1) {
                this.updateDelay();
            }
            if (this.delay > 0) {
                --this.delay;
            } else {
                int var7 = 4;
                int var8 = 0;
                while (var8 < var7) {
                    EntityLiving var9 = (EntityLiving)EntityList.createEntityInWorld(this.mobID, this.worldObj);
                    if (var9 == null) {
                        return;
                    }
                    int var10 = this.worldObj.getEntitiesWithinAABB(var9.getClass(), AxisAlignedBB.getBoundingBoxFromPool(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(8.0, 4.0, 8.0)).size();
                    if (var10 >= 6) {
                        this.updateDelay();
                        return;
                    }
                    if (var9 != null) {
                        double var11 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                        double var13 = this.yCoord + this.worldObj.rand.nextInt(3) - 1;
                        double var15 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                        var9.setLocationAndAngles(var11, var13, var15, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
                        if (var9.getCanSpawnHere()) {
                            this.worldObj.entityJoinedWorld(var9);
                            int var17 = 0;
                            while (var17 < 20) {
                                var1 = (double)this.xCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                var3 = (double)this.yCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                var5 = (double)this.zCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                this.worldObj.spawnParticle("smoke", var1, var3, var5, 0.0, 0.0, 0.0);
                                this.worldObj.spawnParticle("flame", var1, var3, var5, 0.0, 0.0, 0.0);
                                ++var17;
                            }
                            var9.spawnExplosionParticle();
                            this.updateDelay();
                        }
                    }
                    ++var8;
                }
                super.updateEntity();
            }
        }
    }

    private void updateDelay() {
        this.delay = 200 + this.worldObj.rand.nextInt(600);
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.mobID = var1.getString("EntityId");
        this.delay = var1.getShort("Delay");
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setString("EntityId", this.mobID);
        var1.setShort("Delay", (short)this.delay);
    }
}

