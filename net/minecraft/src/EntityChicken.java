/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityAnimals;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityChicken
extends EntityAnimals {
    public boolean field_753_a = false;
    public float field_752_b = 0.0f;
    public float field_758_c = 0.0f;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h = 1.0f;
    public int timeUntilNextEgg;

    public EntityChicken(World var1) {
        super(var1);
        this.texture = "/mob/chicken.png";
        this.setSize(0.3f, 0.4f);
        this.health = 4;
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_756_e = this.field_752_b;
        this.field_757_d = this.field_758_c;
        this.field_758_c = (float)((double)this.field_758_c + (double)(this.onGround ? -1 : 4) * 0.3);
        if (this.field_758_c < 0.0f) {
            this.field_758_c = 0.0f;
        }
        if (this.field_758_c > 1.0f) {
            this.field_758_c = 1.0f;
        }
        if (!this.onGround && this.field_755_h < 1.0f) {
            this.field_755_h = 1.0f;
        }
        this.field_755_h = (float)((double)this.field_755_h * 0.9);
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.field_752_b += this.field_755_h * 2.0f;
        if (!this.worldObj.multiplayerWorld && --this.timeUntilNextEgg <= 0) {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.dropItem(Item.egg.shiftedIndex, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }

    @Override
    protected void fall(float var1) {
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
    protected String getLivingSound() {
        return "mob.chicken";
    }

    @Override
    protected String getHurtSound() {
        return "mob.chickenhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.chickenhurt";
    }

    @Override
    protected int getDropItemId() {
        return Item.feather.shiftedIndex;
    }
}

