package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemLog
extends ItemBlock {
    public ItemLog(int var1) {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getIconIndex(ItemStack var1) {
        return Block.wood.getBlockTextureFromSideAndMetadata(2, var1.getItemDamage());
    }

    @Override
    public int func_21012_a(int var1) {
        return var1;
    }
}

