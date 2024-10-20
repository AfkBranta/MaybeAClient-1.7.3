/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemDye;
import net.minecraft.src.ItemStack;

public class ItemCloth
extends ItemBlock {
    public ItemCloth(int var1) {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getIconIndex(ItemStack var1) {
        return Block.cloth.getBlockTextureFromSideAndMetadata(2, BlockCloth.func_21034_c(var1.getItemDamage()));
    }

    @Override
    public int func_21012_a(int var1) {
        return var1;
    }

    @Override
    public String getItemNameIS(ItemStack var1) {
        return String.valueOf(super.getItemName()) + "." + ItemDye.dyeColors[BlockCloth.func_21034_c(var1.getItemDamage())];
    }
}

