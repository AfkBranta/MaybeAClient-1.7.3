/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntityDispenser;

public class CraftingInventoryDispenserCB
extends CraftingInventoryCB {
    private TileEntityDispenser field_21149_a;

    public CraftingInventoryDispenserCB(IInventory var1, TileEntityDispenser var2) {
        int var4;
        this.field_21149_a = var2;
        int var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 3) {
                this.addSlot(new Slot(var2, var4 + var3 * 3, 61 + var4 * 18, 17 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 9) {
                this.addSlot(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 9) {
            this.addSlot(new Slot(var1, var3, 8 + var3 * 18, 142));
            ++var3;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.field_21149_a.canInteractWith(var1);
    }
}

