package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotArmor;
import net.minecraft.src.SlotCrafting;

public class CraftingInventoryPlayerCB
extends CraftingInventoryCB {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public IInventory craftResult = new InventoryCraftResult();
    public boolean isSinglePlayer = false;

    public CraftingInventoryPlayerCB(InventoryPlayer var1) {
        this(var1, true);
    }

    public CraftingInventoryPlayerCB(InventoryPlayer var1, boolean var2) {
        int var4;
        this.isSinglePlayer = var2;
        this.addSlot(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        int var3 = 0;
        while (var3 < 2) {
            var4 = 0;
            while (var4 < 2) {
                this.addSlot(new Slot(this.craftMatrix, var4 + var3 * 2, 88 + var4 * 18, 26 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 4) {
            this.addSlot(new SlotArmor(this, var1, var1.getSizeInventory() - 1 - var3, 8, 8 + var3 * 18, var3));
            ++var3;
        }
        var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 9) {
                this.addSlot(new Slot(var1, var4 + (var3 + 1) * 9, 8 + var4 * 18, 84 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 9) {
            this.addSlot(new Slot(var1, var3, 8 + var3 * 18, 142));
            ++var3;
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
        while (var2 < 4) {
            ItemStack var3 = this.craftMatrix.getStackInSlot(var2);
            if (var3 != null) {
                var1.dropPlayerItem(var3);
                this.craftMatrix.setInventorySlotContents(var2, null);
            }
            ++var2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return true;
    }
}

