/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.World;

public class EntityWolf
extends EntityAnimals {
    private boolean looksWithInterest = false;
    private float field_25048_b;
    private float field_25054_c;
    private boolean field_25053_f;
    private boolean field_25052_g;
    private float timeWolfIsShaking;
    private float field_25050_i;

    public EntityWolf(World var1) {
        super(var1);
        this.texture = "/mob/wolf.png";
        this.setSize(0.8f, 0.8f);
        this.moveSpeed = 1.1f;
        this.health = 8;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
        this.dataWatcher.addObject(17, "");
        this.dataWatcher.addObject(18, new Integer(this.health));
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public String getEntityTexture() {
        if (this.isWolfTamed()) {
            return "/mob/wolf_tame.png";
        }
        return this.isWolfAngry() ? "/mob/wolf_angry.png" : super.getEntityTexture();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setBoolean("Angry", this.isWolfAngry());
        var1.setBoolean("Sitting", this.isWolfSitting());
        if (this.getWolfOwner() == null) {
            var1.setString("Owner", "");
        } else {
            var1.setString("Owner", this.getWolfOwner());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.setWolfAngry(var1.getBoolean("Angry"));
        this.setWolfSitting(var1.getBoolean("Sitting"));
        String var2 = var1.getString("Owner");
        if (var2.length() > 0) {
            this.setWolfOwner(var2);
            this.func_25038_d(true);
        }
    }

    @Override
    protected boolean canDespawn() {
        return !this.isWolfTamed();
    }

    @Override
    protected String getLivingSound() {
        if (this.isWolfAngry()) {
            return "mob.wolf.growl";
        }
        if (this.rand.nextInt(3) == 0) {
            return this.isWolfTamed() && this.health < 10 ? "mob.wolf.whine" : "mob.wolf.panting";
        }
        return "mob.wolf.bark";
    }

    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected int getDropItemId() {
        return -1;
    }

    @Override
    protected void updatePlayerActionState() {
        List var1;
        super.updatePlayerActionState();
        if (!this.hasAttacked && !this.hasPath() && this.isWolfTamed()) {
            EntityPlayer var3 = this.worldObj.getPlayerEntityByName(this.getWolfOwner());
            if (var3 != null) {
                float var2 = var3.getDistanceToEntity(this);
                if (var2 > 5.0f) {
                    this.getPathOrWalkableBlock(var3, var2);
                }
            } else if (!this.handleWaterMovement()) {
                this.setWolfSitting(true);
            }
        } else if (!(this.playerToAttack != null || this.hasPath() || this.isWolfTamed() || this.worldObj.rand.nextInt(100) != 0 || (var1 = this.worldObj.getEntitiesWithinAABB(EntitySheep.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0))).isEmpty())) {
            this.setTarget((Entity)var1.get(this.worldObj.rand.nextInt(var1.size())));
        }
        if (this.handleWaterMovement()) {
            this.setWolfSitting(false);
        }
        if (!this.worldObj.multiplayerWorld) {
            this.dataWatcher.updateObject(18, this.health);
        }
    }

    @Override
    public void onLivingUpdate() {
        Entity var1;
        super.onLivingUpdate();
        this.looksWithInterest = false;
        if (this.func_25025_V() && !this.hasPath() && !this.isWolfAngry() && (var1 = this.getCurrentTarget()) instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)var1;
            ItemStack var3 = var2.inventory.getCurrentItem();
            if (var3 != null) {
                if (!this.isWolfTamed() && var3.itemID == Item.bone.shiftedIndex) {
                    this.looksWithInterest = true;
                } else if (this.isWolfTamed() && Item.itemsList[var3.itemID] instanceof ItemFood) {
                    this.looksWithInterest = ((ItemFood)Item.itemsList[var3.itemID]).getIsWolfsFavoriteMeat();
                }
            }
        }
        if (!this.field_9343_G && this.field_25053_f && !this.field_25052_g && !this.hasPath()) {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
            this.worldObj.func_9425_a(this, (byte)8);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_25054_c = this.field_25048_b;
        this.field_25048_b = this.looksWithInterest ? (this.field_25048_b += (1.0f - this.field_25048_b) * 0.4f) : (this.field_25048_b += (0.0f - this.field_25048_b) * 0.4f);
        if (this.looksWithInterest) {
            this.numTicksToChaseTarget = 10;
        }
        if (this.handleWaterMovement()) {
            this.field_25053_f = true;
            this.field_25052_g = false;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
        } else if ((this.field_25053_f || this.field_25052_g) && this.field_25052_g) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.worldObj.playSoundAtEntity(this, "mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.field_25050_i = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.field_25050_i >= 2.0f) {
                this.field_25053_f = false;
                this.field_25052_g = false;
                this.field_25050_i = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                float var1 = (float)this.boundingBox.minY;
                int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * (float)Math.PI) * 7.0f);
                int var3 = 0;
                while (var3 < var2) {
                    float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.worldObj.spawnParticle("splash", this.posX + (double)var4, var1 + 0.8f, this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
                    ++var3;
                }
            }
        }
    }

    public boolean func_25039_v() {
        return this.field_25053_f;
    }

    public float func_25043_b_(float var1) {
        return 0.75f + (this.field_25050_i + (this.timeWolfIsShaking - this.field_25050_i) * var1) / 2.0f * 0.25f;
    }

    public float func_25042_a(float var1, float var2) {
        float var3 = (this.field_25050_i + (this.timeWolfIsShaking - this.field_25050_i) * var1 + var2) / 1.8f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        } else if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return MathHelper.sin(var3 * (float)Math.PI) * MathHelper.sin(var3 * (float)Math.PI * 11.0f) * 0.15f * (float)Math.PI;
    }

    public float func_25033_c(float var1) {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * var1) * 0.15f * (float)Math.PI;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }

    @Override
    protected int func_25026_x() {
        return this.isWolfSitting() ? 20 : super.func_25026_x();
    }

    private void getPathOrWalkableBlock(Entity var1, float var2) {
        PathEntity var3 = this.worldObj.getPathToEntity(this, var1, 16.0f);
        if (var3 == null && var2 > 12.0f) {
            int var4 = MathHelper.floor_double(var1.posX) - 2;
            int var5 = MathHelper.floor_double(var1.posZ) - 2;
            int var6 = MathHelper.floor_double(var1.boundingBox.minY);
            int var7 = 0;
            while (var7 <= 4) {
                int var8 = 0;
                while (var8 <= 4) {
                    if (!(var7 >= 1 && var8 >= 1 && var7 <= 3 && var8 <= 3 || !this.worldObj.isBlockOpaqueCube(var4 + var7, var6 - 1, var5 + var8) || this.worldObj.isBlockOpaqueCube(var4 + var7, var6, var5 + var8) || this.worldObj.isBlockOpaqueCube(var4 + var7, var6 + 1, var5 + var8))) {
                        this.setLocationAndAngles((float)(var4 + var7) + 0.5f, var6, (float)(var5 + var8) + 0.5f, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                    ++var8;
                }
                ++var7;
            }
        } else {
            this.setPathToEntity(var3);
        }
    }

    @Override
    protected boolean func_25028_d_() {
        return this.isWolfSitting() || this.field_25052_g;
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        this.setWolfSitting(false);
        if (var1 != null && !(var1 instanceof EntityPlayer) && !(var1 instanceof EntityArrow)) {
            var2 = (var2 + 1) / 2;
        }
        if (!super.attackEntityFrom(var1, var2)) {
            return false;
        }
        if (!this.isWolfTamed() && !this.isWolfAngry()) {
            if (var1 instanceof EntityPlayer) {
                this.setWolfAngry(true);
                this.playerToAttack = var1;
            }
            if (var1 instanceof EntityArrow && ((EntityArrow)var1).archer != null) {
                var1 = ((EntityArrow)var1).archer;
            }
            if (var1 instanceof EntityLiving) {
                List var3 = this.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0));
                for (Entity var5 : var3) {
                    EntityWolf var6 = (EntityWolf)var5;
                    if (var6.isWolfTamed() || var6.playerToAttack != null) continue;
                    var6.playerToAttack = var1;
                    if (!(var1 instanceof EntityPlayer)) continue;
                    var6.setWolfAngry(true);
                }
            }
        } else if (var1 != this && var1 != null) {
            if (this.isWolfTamed() && var1 instanceof EntityPlayer && ((EntityPlayer)var1).username.equals(this.getWolfOwner())) {
                return true;
            }
            this.playerToAttack = var1;
        }
        return true;
    }

    @Override
    protected Entity findPlayerToAttack() {
        return this.isWolfAngry() ? this.worldObj.getClosestPlayerToEntity(this, 16.0) : null;
    }

    @Override
    protected void attackEntity(Entity var1, float var2) {
        if (var2 > 2.0f && var2 < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                double var8 = var1.posX - this.posX;
                double var5 = var1.posZ - this.posZ;
                float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5);
                this.motionX = var8 / (double)var7 * 0.5 * (double)0.8f + this.motionX * (double)0.2f;
                this.motionZ = var5 / (double)var7 * 0.5 * (double)0.8f + this.motionZ * (double)0.2f;
                this.motionY = 0.4f;
            }
        } else if ((double)var2 < 1.5 && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            int var3 = 2;
            if (this.isWolfTamed()) {
                var3 = 4;
            }
            var1.attackEntityFrom(this, var3);
        }
    }

    @Override
    public boolean interact(EntityPlayer var1) {
        ItemStack var2 = var1.inventory.getCurrentItem();
        if (!this.isWolfTamed()) {
            if (var2 != null && var2.itemID == Item.bone.shiftedIndex && !this.isWolfAngry()) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    var1.inventory.setInventorySlotContents(var1.inventory.currentItem, null);
                }
                if (!this.worldObj.multiplayerWorld) {
                    if (this.rand.nextInt(3) == 0) {
                        this.func_25038_d(true);
                        this.setPathToEntity(null);
                        this.setWolfSitting(true);
                        this.health = 20;
                        this.setWolfOwner(var1.username);
                        this.showHeartsOrSmokeFX(true);
                        this.worldObj.func_9425_a(this, (byte)7);
                    } else {
                        this.showHeartsOrSmokeFX(false);
                        this.worldObj.func_9425_a(this, (byte)6);
                    }
                }
                return true;
            }
        } else {
            ItemFood var3;
            if (var2 != null && Item.itemsList[var2.itemID] instanceof ItemFood && (var3 = (ItemFood)Item.itemsList[var2.itemID]).getIsWolfsFavoriteMeat() && this.dataWatcher.func_25115_b(18) < 20) {
                --var2.stackSize;
                if (var2.stackSize <= 0) {
                    var1.inventory.setInventorySlotContents(var1.inventory.currentItem, null);
                }
                this.heal(((ItemFood)Item.porkRaw).getHealAmount());
                return true;
            }
            if (var1.username.equals(this.getWolfOwner())) {
                if (!this.worldObj.multiplayerWorld) {
                    this.setWolfSitting(!this.isWolfSitting());
                    this.isJumping = false;
                    this.setPathToEntity(null);
                }
                return true;
            }
        }
        return false;
    }

    void showHeartsOrSmokeFX(boolean var1) {
        String var2 = "heart";
        if (!var1) {
            var2 = "smoke";
        }
        int var3 = 0;
        while (var3 < 7) {
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(var2, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, this.posY + 0.5 + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0f) - (double)this.width, var4, var6, var8);
            ++var3;
        }
    }

    @Override
    public void handleHealthUpdate(byte var1) {
        if (var1 == 7) {
            this.showHeartsOrSmokeFX(true);
        } else if (var1 == 6) {
            this.showHeartsOrSmokeFX(false);
        } else if (var1 == 8) {
            this.field_25052_g = true;
            this.timeWolfIsShaking = 0.0f;
            this.field_25050_i = 0.0f;
        } else {
            super.handleHealthUpdate(var1);
        }
    }

    public float func_25037_z() {
        if (this.isWolfAngry()) {
            return 1.5393804f;
        }
        return this.isWolfTamed() ? (0.55f - (float)(20 - this.dataWatcher.func_25115_b(18)) * 0.02f) * (float)Math.PI : 0.62831855f;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    public String getWolfOwner() {
        return this.dataWatcher.func_25116_c(17);
    }

    public void setWolfOwner(String var1) {
        this.dataWatcher.updateObject(17, var1);
    }

    public boolean isWolfSitting() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setWolfSitting(boolean var1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (var1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }

    public boolean isWolfAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setWolfAngry(boolean var1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (var1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 2));
        } else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFD));
        }
    }

    public boolean isWolfTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void func_25038_d(boolean var1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (var1) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 4));
        } else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFB));
        }
    }
}

