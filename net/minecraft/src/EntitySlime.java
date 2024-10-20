/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Chunk;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySlime
extends EntityLiving
implements IMobs {
    public float field_768_a;
    public float field_767_b;
    private int slimeJumpDelay = 0;

    public EntitySlime(World var1) {
        super(var1);
        this.texture = "/mob/slime.png";
        int var2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0f;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(var2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(1));
    }

    public void setSlimeSize(int var1) {
        this.dataWatcher.updateObject(16, new Byte((byte)var1));
        this.setSize(0.6f * (float)var1, 0.6f * (float)var1);
        this.health = var1 * var1;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setInteger("Size", this.getSlimeSize() - 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.setSlimeSize(var1.getInteger("Size") + 1);
    }

    @Override
    public void onUpdate() {
        this.field_767_b = this.field_768_a;
        boolean var1 = this.onGround;
        super.onUpdate();
        if (this.onGround && !var1) {
            int var2 = this.getSlimeSize();
            int var3 = 0;
            while (var3 < var2 * 8) {
                float var4 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                float var5 = this.rand.nextFloat() * 0.5f + 0.5f;
                float var6 = MathHelper.sin(var4) * (float)var2 * 0.5f * var5;
                float var7 = MathHelper.cos(var4) * (float)var2 * 0.5f * var5;
                this.worldObj.spawnParticle("slime", this.posX + (double)var6, this.boundingBox.minY, this.posZ + (double)var7, 0.0, 0.0, 0.0);
                ++var3;
            }
            if (var2 > 2) {
                this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.field_768_a = -0.5f;
        }
        this.field_768_a *= 0.6f;
    }

    @Override
    protected void updatePlayerActionState() {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
        if (var1 != null) {
            this.faceEntity(var1, 10.0f, 20.0f);
        }
        if (this.onGround && this.slimeJumpDelay-- <= 0) {
            this.slimeJumpDelay = this.rand.nextInt(20) + 10;
            if (var1 != null) {
                this.slimeJumpDelay /= 3;
            }
            this.isJumping = true;
            if (this.getSlimeSize() > 1) {
                this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.field_768_a = 1.0f;
            this.moveStrafing = 1.0f - this.rand.nextFloat() * 2.0f;
            this.moveForward = 1 * this.getSlimeSize();
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveForward = 0.0f;
                this.moveStrafing = 0.0f;
            }
        }
    }

    @Override
    public void setEntityDead() {
        int var1 = this.getSlimeSize();
        if (!this.worldObj.multiplayerWorld && var1 > 1 && this.health == 0) {
            int var2 = 0;
            while (var2 < 4) {
                float var3 = ((float)(var2 % 2) - 0.5f) * (float)var1 / 4.0f;
                float var4 = ((float)(var2 / 2) - 0.5f) * (float)var1 / 4.0f;
                EntitySlime var5 = new EntitySlime(this.worldObj);
                var5.setSlimeSize(var1 / 2);
                var5.setLocationAndAngles(this.posX + (double)var3, this.posY + 0.5, this.posZ + (double)var4, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.entityJoinedWorld(var5);
                ++var2;
            }
        }
        super.setEntityDead();
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer var1) {
        int var2 = this.getSlimeSize();
        if (var2 > 1 && this.canEntityBeSeen(var1) && (double)this.getDistanceToEntity(var1) < 0.6 * (double)var2 && var1.attackEntityFrom(this, var2)) {
            this.worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Override
    protected String getHurtSound() {
        return "mob.slime";
    }

    @Override
    protected String getDeathSound() {
        return "mob.slime";
    }

    @Override
    protected int getDropItemId() {
        return this.getSlimeSize() == 1 ? Item.slimeBall.shiftedIndex : 0;
    }

    @Override
    public boolean getCanSpawnHere() {
        Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        return (this.getSlimeSize() == 1 || this.worldObj.difficultySetting > 0) && this.rand.nextInt(10) == 0 && var1.func_997_a(987234911L).nextInt(10) == 0 && this.posY < 16.0;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }
}

