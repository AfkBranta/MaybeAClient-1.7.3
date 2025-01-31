/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFlintAndSteel
extends Item {
    public ItemFlintAndSteel(int var1) {
        super(var1);
        this.maxStackSize = 1;
        this.setMaxDamage(64);
    }

    @Override
    public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
        int var8;
        if (var7 == 0) {
            --var5;
        }
        if (var7 == 1) {
            ++var5;
        }
        if (var7 == 2) {
            --var6;
        }
        if (var7 == 3) {
            ++var6;
        }
        if (var7 == 4) {
            --var4;
        }
        if (var7 == 5) {
            ++var4;
        }
        if ((var8 = var3.getBlockId(var4, var5, var6)) == 0) {
            var3.playSoundEffect((double)var4 + 0.5, (double)var5 + 0.5, (double)var6 + 0.5, "fire.ignite", 1.0f, itemRand.nextFloat() * 0.4f + 0.8f);
            var3.setBlockWithNotify(var4, var5, var6, Block.fire.blockID);
        }
        var1.func_25190_a(1, var2);
        return true;
    }
}

