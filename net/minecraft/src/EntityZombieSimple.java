/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityMobs;
import net.minecraft.src.World;

public class EntityZombieSimple
extends EntityMobs {
    public EntityZombieSimple(World var1) {
        super(var1);
        this.texture = "/mob/zombie.png";
        this.moveSpeed = 0.5f;
        this.attackStrength = 50;
        this.health *= 10;
        this.yOffset *= 6.0f;
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }

    @Override
    protected float getBlockPathWeight(int var1, int var2, int var3) {
        return this.worldObj.getLightBrightness(var1, var2, var3) - 0.5f;
    }
}

