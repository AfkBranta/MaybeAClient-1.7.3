/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPig;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemSaddle
extends Item {
    public ItemSaddle(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    @Override
    public void saddleEntity(ItemStack var1, EntityLiving var2) {
        EntityPig var3;
        if (var2 instanceof EntityPig && !(var3 = (EntityPig)var2).getSaddled()) {
            var3.setSaddled(true);
            --var1.stackSize;
        }
    }

    @Override
    public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3) {
        this.saddleEntity(var1, var2);
        return true;
    }
}

