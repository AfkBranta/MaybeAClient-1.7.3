/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingInventoryPlayerCB;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

class SlotArmor
extends Slot {
    final int armorType;
    final CraftingInventoryPlayerCB inventory;

    SlotArmor(CraftingInventoryPlayerCB var1, IInventory var2, int var3, int var4, int var5, int var6) {
        super(var2, var3, var4, var5);
        this.inventory = var1;
        this.armorType = var6;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack var1) {
        if (var1.getItem() instanceof ItemArmor) {
            return ((ItemArmor)var1.getItem()).armorType == this.armorType;
        }
        if (var1.getItem().shiftedIndex == Block.pumpkin.blockID) {
            return this.armorType == 0;
        }
        return false;
    }
}

