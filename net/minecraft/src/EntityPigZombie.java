/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityPigZombie
extends EntityZombie {
    private int angerLevel = 0;
    private int randomSoundDelay = 0;
    private static final ItemStack defaultHeldItem = new ItemStack(Item.swordGold, 1);

    public EntityPigZombie(World var1) {
        super(var1);
        this.texture = "/mob/pigzombie.png";
        this.moveSpeed = 0.5f;
        this.attackStrength = 5;
        this.isImmuneToFire = true;
    }

    @Override
    public void onUpdate() {
        float f = this.moveSpeed = this.playerToAttack != null ? 0.95f : 0.5f;
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.worldObj.playSoundAtEntity(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.onUpdate();
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setShort("Anger", (short)this.angerLevel);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.angerLevel = var1.getShort("Anger");
    }

    @Override
    protected Entity findPlayerToAttack() {
        return this.angerLevel == 0 ? null : super.findPlayerToAttack();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(Entity var1, int var2) {
        if (var1 instanceof EntityPlayer) {
            List var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            int var4 = 0;
            while (var4 < var3.size()) {
                Entity var5 = (Entity)var3.get(var4);
                if (var5 instanceof EntityPigZombie) {
                    EntityPigZombie var6 = (EntityPigZombie)var5;
                    var6.becomeAngryAt(var1);
                }
                ++var4;
            }
            this.becomeAngryAt(var1);
        }
        return super.attackEntityFrom(var1, var2);
    }

    private void becomeAngryAt(Entity var1) {
        this.playerToAttack = var1;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    @Override
    protected int getDropItemId() {
        return Item.porkCooked.shiftedIndex;
    }

    @Override
    public ItemStack getHeldItem() {
        return defaultHeldItem;
    }
}

