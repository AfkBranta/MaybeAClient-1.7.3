/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import net.minecraft.src.World;

public class CraftingInventoryWorkbenchCB
extends CraftingInventoryCB {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World field_20133_c;
    private int field_20132_h;
    private int field_20131_i;
    private int field_20130_j;

    public CraftingInventoryWorkbenchCB(InventoryPlayer var1, World var2, int var3, int var4, int var5) {
        int var7;
        this.field_20133_c = var2;
        this.field_20132_h = var3;
        this.field_20131_i = var4;
        this.field_20130_j = var5;
        this.addSlot(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int var6 = 0;
        while (var6 < 3) {
            var7 = 0;
            while (var7 < 3) {
                this.addSlot(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
                ++var7;
            }
            ++var6;
        }
        var6 = 0;
        while (var6 < 3) {
            var7 = 0;
            while (var7 < 9) {
                this.addSlot(new Slot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
                ++var7;
            }
            ++var6;
        }
        var6 = 0;
        while (var6 < 9) {
            this.addSlot(new Slot(var1, var6, 8 + var6 * 18, 142));
            ++var6;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory var1) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix));
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer var1) {
        super.onCraftGuiClosed(var1);
        int var2 = 0;
        while (var2 < 9) {
            ItemStack var3 = this.craftMatrix.getStackInSlot(var2);
            if (var3 != null) {
                var1.dropPlayerItem(var3);
            }
            ++var2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        if (this.field_20133_c.getBlockId(this.field_20132_h, this.field_20131_i, this.field_20130_j) != Block.workbench.blockID) {
            return false;
        }
        return var1.getDistanceSq((double)this.field_20132_h + 0.5, (double)this.field_20131_i + 0.5, (double)this.field_20130_j + 0.5) <= 64.0;
    }
}

