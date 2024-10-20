/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

public class CraftingInventoryChestCB
extends CraftingInventoryCB {
    private IInventory field_20125_a;

    public CraftingInventoryChestCB(IInventory var1, IInventory var2) {
        int var6;
        this.field_20125_a = var2;
        int var3 = var2.getSizeInventory() / 9;
        int var4 = (var3 - 4) * 18;
        int var5 = 0;
        while (var5 < var3) {
            var6 = 0;
            while (var6 < 9) {
                this.addSlot(new Slot(var2, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
                ++var6;
            }
            ++var5;
        }
        var5 = 0;
        while (var5 < 3) {
            var6 = 0;
            while (var6 < 9) {
                this.addSlot(new Slot(var1, var6 + var5 * 9 + 9, 8 + var6 * 18, 103 + var5 * 18 + var4));
                ++var6;
            }
            ++var5;
        }
        var5 = 0;
        while (var5 < 9) {
            this.addSlot(new Slot(var1, var5, 8 + var5 * 18, 161 + var4));
            ++var5;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.field_20125_a.canInteractWith(var1);
    }
}

