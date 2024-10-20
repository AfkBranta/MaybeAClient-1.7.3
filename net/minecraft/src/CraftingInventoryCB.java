/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.StatList;

public abstract class CraftingInventoryCB {
    public List field_20123_d = new ArrayList();
    public List slots = new ArrayList();
    public int windowId = 0;
    private short field_20917_a = 0;
    protected List field_20121_g = new ArrayList();
    private Set field_20918_b = new HashSet();

    protected void addSlot(Slot var1) {
        var1.slotNumber = this.slots.size();
        this.slots.add(var1);
        this.field_20123_d.add(null);
    }

    public void updateCraftingResults() {
        int var1 = 0;
        while (var1 < this.slots.size()) {
            ItemStack var2 = ((Slot)this.slots.get(var1)).getStack();
            ItemStack var3 = (ItemStack)this.field_20123_d.get(var1);
            if (!ItemStack.areItemStacksEqual(var3, var2)) {
                var3 = var2 == null ? null : var2.copy();
                this.field_20123_d.set(var1, var3);
                int var4 = 0;
                while (var4 < this.field_20121_g.size()) {
                    ((ICrafting)this.field_20121_g.get(var4)).func_20159_a(this, var1, var3);
                    ++var4;
                }
            }
            ++var1;
        }
    }

    public Slot getSlot(int var1) {
        return (Slot)this.slots.get(var1);
    }

    public ItemStack func_20116_a(int var1, int var2, EntityPlayer var3) {
        ItemStack var4 = null;
        if (var2 == 0 || var2 == 1) {
            InventoryPlayer var5 = var3.inventory;
            if (var1 == -999) {
                if (var5.getItemStack() != null && var1 == -999) {
                    if (var2 == 0) {
                        var3.dropPlayerItem(var5.getItemStack());
                        var5.setItemStack(null);
                    }
                    if (var2 == 1) {
                        var3.dropPlayerItem(var5.getItemStack().splitStack(1));
                        if (var5.getItemStack().stackSize == 0) {
                            var5.setItemStack(null);
                        }
                    }
                }
            } else {
                Slot var6 = (Slot)this.slots.get(var1);
                if (var6 != null) {
                    int var9;
                    var6.onSlotChanged();
                    ItemStack var7 = var6.getStack();
                    ItemStack var8 = var5.getItemStack();
                    if (var7 != null) {
                        var4 = var7.copy();
                    }
                    if (var7 == null) {
                        if (var8 != null && var6.isItemValid(var8)) {
                            int var92;
                            int n = var92 = var2 == 0 ? var8.stackSize : 1;
                            if (var92 > var6.getSlotStackLimit()) {
                                var92 = var6.getSlotStackLimit();
                            }
                            var6.putStack(var8.splitStack(var92));
                            if (var8.stackSize == 0) {
                                var5.setItemStack(null);
                            }
                        }
                    } else if (var8 == null) {
                        int var93 = var2 == 0 ? var7.stackSize : (var7.stackSize + 1) / 2;
                        ItemStack var10 = var6.decrStackSize(var93);
                        if (var10 != null && var6.func_25014_f()) {
                            var3.addStat(StatList.field_25158_z[var10.itemID], var10.stackSize);
                        }
                        var5.setItemStack(var10);
                        if (var7.stackSize == 0) {
                            var6.putStack(null);
                        }
                        var6.onPickupFromSlot(var5.getItemStack());
                    } else if (var6.isItemValid(var8)) {
                        if (var7.itemID != var8.itemID || var7.getHasSubtypes() && var7.getItemDamage() != var8.getItemDamage()) {
                            if (var8.stackSize <= var6.getSlotStackLimit()) {
                                var6.putStack(var8);
                                var5.setItemStack(var7);
                            }
                        } else {
                            int var94;
                            int n = var94 = var2 == 0 ? var8.stackSize : 1;
                            if (var94 > var6.getSlotStackLimit() - var7.stackSize) {
                                var94 = var6.getSlotStackLimit() - var7.stackSize;
                            }
                            if (var94 > var8.getMaxStackSize() - var7.stackSize) {
                                var94 = var8.getMaxStackSize() - var7.stackSize;
                            }
                            var8.splitStack(var94);
                            if (var8.stackSize == 0) {
                                var5.setItemStack(null);
                            }
                            var7.stackSize += var94;
                        }
                    } else if (!(var7.itemID != var8.itemID || var8.getMaxStackSize() <= 1 || var7.getHasSubtypes() && var7.getItemDamage() != var8.getItemDamage() || (var9 = var7.stackSize) <= 0 || var9 + var8.stackSize > var8.getMaxStackSize())) {
                        var8.stackSize += var9;
                        ItemStack var10 = var7.splitStack(var9);
                        if (var10 != null && var6.func_25014_f()) {
                            var3.addStat(StatList.field_25158_z[var10.itemID], var10.stackSize);
                        }
                        if (var7.stackSize == 0) {
                            var6.putStack(null);
                        }
                        var6.onPickupFromSlot(var5.getItemStack());
                    }
                }
            }
        }
        return var4;
    }

    public void onCraftGuiClosed(EntityPlayer var1) {
        InventoryPlayer var2 = var1.inventory;
        if (var2.getItemStack() != null) {
            var1.dropPlayerItem(var2.getItemStack());
            var2.setItemStack(null);
        }
    }

    public void onCraftMatrixChanged(IInventory var1) {
        this.updateCraftingResults();
    }

    public void putStackInSlot(int var1, ItemStack var2) {
        this.getSlot(var1).putStack(var2);
    }

    public void putStacksInSlots(ItemStack[] var1) {
        int var2 = 0;
        while (var2 < var1.length) {
            this.getSlot(var2).putStack(var1[var2]);
            ++var2;
        }
    }

    public void func_20112_a(int var1, int var2) {
    }

    public short func_20111_a(InventoryPlayer var1) {
        this.field_20917_a = (short)(this.field_20917_a + 1);
        return this.field_20917_a;
    }

    public void func_20113_a(short var1) {
    }

    public void func_20110_b(short var1) {
    }

    public abstract boolean isUsableByPlayer(EntityPlayer var1);
}

