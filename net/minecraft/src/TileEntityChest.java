/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityChest
extends TileEntity
implements IInventory {
    private ItemStack[] chestContents = new ItemStack[36];

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.chestContents[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        if (this.chestContents[var1] != null) {
            if (this.chestContents[var1].stackSize <= var2) {
                ItemStack var3 = this.chestContents[var1];
                this.chestContents[var1] = null;
                this.onInventoryChanged();
                return var3;
            }
            ItemStack var3 = this.chestContents[var1].splitStack(var2);
            if (this.chestContents[var1].stackSize == 0) {
                this.chestContents[var1] = null;
            }
            this.onInventoryChanged();
            return var3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.chestContents[var1] = var2;
        if (var2 != null && var2.stackSize > this.getInventoryStackLimit()) {
            var2.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Chest";
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];
        int var3 = 0;
        while (var3 < var2.tagCount()) {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.chestContents.length) {
                this.chestContents[var5] = new ItemStack(var4);
            }
            ++var3;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        NBTTagList var2 = new NBTTagList();
        int var3 = 0;
        while (var3 < this.chestContents.length) {
            if (this.chestContents[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.chestContents[var3].writeToNBT(var4);
                var2.setTag(var4);
            }
            ++var3;
        }
        var1.setTag("Items", var2);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
            return false;
        }
        return var1.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void onInventoryChanged() {
    }
}

