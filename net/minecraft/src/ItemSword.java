/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemSword
extends Item {
    private int weaponDamage;

    public ItemSword(int var1, EnumToolMaterial var2) {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(var2.getMaxUses());
        this.weaponDamage = 4 + var2.getDamageVsEntity() * 2;
    }

    @Override
    public float getStrVsBlock(ItemStack var1, Block var2) {
        return 1.5f;
    }

    @Override
    public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3) {
        var1.func_25190_a(1, var3);
        return true;
    }

    @Override
    public boolean func_25008_a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6) {
        var1.func_25190_a(2, var6);
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity var1) {
        return this.weaponDamage;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}

