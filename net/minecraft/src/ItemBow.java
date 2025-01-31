/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemBow
extends Item {
    public ItemBow(int var1) {
        super(var1);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        if (var3.inventory.consumeInventoryItem(Item.arrow.shiftedIndex)) {
            var2.playSoundAtEntity(var3, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!var2.multiplayerWorld) {
                var2.entityJoinedWorld(new EntityArrow(var2, var3));
            }
        }
        return var1;
    }
}

