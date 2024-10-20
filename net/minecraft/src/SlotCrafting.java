/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AchievementList;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.StatList;

public class SlotCrafting
extends Slot {
    private final IInventory craftMatrix;
    private EntityPlayer field_25015_e;

    public SlotCrafting(EntityPlayer var1, IInventory var2, IInventory var3, int var4, int var5, int var6) {
        super(var3, var4, var5, var6);
        this.field_25015_e = var1;
        this.craftMatrix = var2;
    }

    @Override
    public boolean isItemValid(ItemStack var1) {
        return false;
    }

    @Override
    public void onPickupFromSlot(ItemStack var1) {
        this.field_25015_e.addStat(StatList.field_25158_z[var1.itemID], 1);
        if (var1.itemID == Block.workbench.blockID) {
            this.field_25015_e.addStat(AchievementList.field_25197_d, 1);
        }
        int var2 = 0;
        while (var2 < this.craftMatrix.getSizeInventory()) {
            ItemStack var3 = this.craftMatrix.getStackInSlot(var2);
            if (var3 != null) {
                this.craftMatrix.decrStackSize(var2, 1);
                if (var3.getItem().hasContainerItem()) {
                    this.craftMatrix.setInventorySlotContents(var2, new ItemStack(var3.getItem().getContainerItem()));
                }
            }
            ++var2;
        }
    }

    @Override
    public boolean func_25014_f() {
        return true;
    }
}

