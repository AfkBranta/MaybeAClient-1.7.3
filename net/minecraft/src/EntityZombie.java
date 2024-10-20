/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityZombie
extends EntityMobs {
    public EntityZombie(World var1) {
        super(var1);
        this.texture = "/mob/zombie.png";
        this.moveSpeed = 0.5f;
        this.attackStrength = 5;
    }

    @Override
    public void onLivingUpdate() {
        float var1;
        if (this.worldObj.isDaytime() && (var1 = this.getEntityBrightness(1.0f)) > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f) {
            this.fire = 300;
        }
        super.onLivingUpdate();
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombie";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombiehurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombiedeath";
    }

    @Override
    protected int getDropItemId() {
        return Item.feather.shiftedIndex;
    }
}

