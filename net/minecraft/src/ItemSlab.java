/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockStep;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemSlab
extends ItemBlock {
    public ItemSlab(int var1) {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getIconIndex(ItemStack var1) {
        return Block.stairSingle.getBlockTextureFromSideAndMetadata(2, var1.getItemDamage());
    }

    @Override
    public int func_21012_a(int var1) {
        return var1;
    }

    @Override
    public String getItemNameIS(ItemStack var1) {
        return String.valueOf(super.getItemName()) + "." + BlockStep.field_22037_a[var1.getItemDamage()];
    }
}

