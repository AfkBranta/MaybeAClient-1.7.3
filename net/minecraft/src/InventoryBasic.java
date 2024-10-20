package net.minecraft.src;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInvBasic;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryBasic
implements IInventory {
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_20073_d;

    public InventoryBasic(String var1, int var2) {
        this.inventoryTitle = var1;
        this.slotsCount = var2;
        this.inventoryContents = new ItemStack[var2];
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.inventoryContents[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if (this.inventoryContents[var1] != null) {
            if (this.inventoryContents[var1].stackSize <= var2) {
                ItemStack var3 = this.inventoryContents[var1];
                this.inventoryContents[var1] = null;
                this.onInventoryChanged();
                return var3;
            }
            ItemStack var3 = this.inventoryContents[var1].splitStack(var2);
            if (this.inventoryContents[var1].stackSize == 0) {
                this.inventoryContents[var1] = null;
            }
            this.onInventoryChanged();
            return var3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.inventoryContents[var1] = var2;
        if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }

    @Override
    public String getInvName() {
        return this.inventoryTitle;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
        if (this.field_20073_d != null) {
            int var1 = 0;
            while (var1 < this.field_20073_d.size()) {
                ((IInvBasic)this.field_20073_d.get(var1)).func_20134_a(this);
                ++var1;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return true;
    }
}

