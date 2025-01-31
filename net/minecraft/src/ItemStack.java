/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StatList;
import net.minecraft.src.World;

public final class ItemStack {
    public int stackSize = 0;
    public int animationsToGo;
    public int itemID;
    private int itemDamage;

    public ItemStack(Block var1) {
        this(var1, 1);
    }

    public ItemStack(Block var1, int var2) {
        this(var1.blockID, var2, 0);
    }

    public ItemStack(Block var1, int var2, int var3) {
        this(var1.blockID, var2, var3);
    }

    public ItemStack(Item var1) {
        this(var1.shiftedIndex, 1, 0);
    }

    public ItemStack(Item var1, int var2) {
        this(var1.shiftedIndex, var2, 0);
    }

    public ItemStack(Item var1, int var2, int var3) {
        this(var1.shiftedIndex, var2, var3);
    }

    public ItemStack(int var1, int var2, int var3) {
        this.itemID = var1;
        this.stackSize = var2;
        this.itemDamage = var3;
    }

    public ItemStack(NBTTagCompound var1) {
        this.readFromNBT(var1);
    }

    public ItemStack splitStack(int var1) {
        this.stackSize -= var1;
        return new ItemStack(this.itemID, var1, this.itemDamage);
    }

    public Item getItem() {
        return Item.itemsList[this.itemID];
    }

    public int getIconIndex() {
        return this.getItem().getIconIndex(this);
    }

    public boolean useItem(EntityPlayer var1, World var2, int var3, int var4, int var5, int var6) {
        boolean var7 = this.getItem().onItemUse(this, var1, var2, var3, var4, var5, var6);
        if (var7) {
            var1.addStat(StatList.field_25172_A[this.itemID], 1);
        }
        return var7;
    }

    public float getStrVsBlock(Block var1) {
        return this.getItem().getStrVsBlock(this, var1);
    }

    public ItemStack useItemRightClick(World var1, EntityPlayer var2) {
        return this.getItem().onItemRightClick(this, var1, var2);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound var1) {
        var1.setShort("id", (short)this.itemID);
        var1.setByte("Count", (byte)this.stackSize);
        var1.setShort("Damage", (short)this.itemDamage);
        return var1;
    }

    public void readFromNBT(NBTTagCompound var1) {
        this.itemID = var1.getShort("id");
        this.stackSize = var1.getByte("Count");
        this.itemDamage = var1.getShort("Damage");
    }

    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }

    public boolean func_21180_d() {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }

    public boolean isItemStackDamageable() {
        return Item.itemsList[this.itemID].getMaxDamage() > 0;
    }

    public boolean getHasSubtypes() {
        return Item.itemsList[this.itemID].getHasSubtypes();
    }

    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }

    public int getItemDamageForDisplay() {
        return this.itemDamage;
    }

    public int getItemDamage() {
        return this.itemDamage;
    }

    public int getMaxDamage() {
        return Item.itemsList[this.itemID].getMaxDamage();
    }

    public void func_25190_a(int var1, Entity var2) {
        if (this.isItemStackDamageable()) {
            this.itemDamage += var1;
            if (this.itemDamage > this.getMaxDamage()) {
                if (var2 instanceof EntityPlayer) {
                    ((EntityPlayer)var2).addStat(StatList.field_25170_B[this.itemID], 1);
                }
                --this.stackSize;
                if (this.stackSize < 0) {
                    this.stackSize = 0;
                }
                this.itemDamage = 0;
            }
        }
    }

    public void hitEntity(EntityLiving var1, EntityPlayer var2) {
        boolean var3 = Item.itemsList[this.itemID].hitEntity(this, var1, var2);
        if (var3) {
            var2.addStat(StatList.field_25172_A[this.itemID], 1);
        }
    }

    public void func_25191_a(int var1, int var2, int var3, int var4, EntityPlayer var5) {
        boolean var6 = Item.itemsList[this.itemID].func_25008_a(this, var1, var2, var3, var4, var5);
        if (var6) {
            var5.addStat(StatList.field_25172_A[this.itemID], 1);
        }
    }

    public int getDamageVsEntity(Entity var1) {
        return Item.itemsList[this.itemID].getDamageVsEntity(var1);
    }

    public boolean canHarvestBlock(Block var1) {
        return Item.itemsList[this.itemID].canHarvestBlock(var1);
    }

    public void func_1097_a(EntityPlayer var1) {
    }

    public void useItemOnEntity(EntityLiving var1) {
        Item.itemsList[this.itemID].saddleEntity(this, var1);
    }

    public ItemStack copy() {
        return new ItemStack(this.itemID, this.stackSize, this.itemDamage);
    }

    public static boolean areItemStacksEqual(ItemStack var0, ItemStack var1) {
        if (var0 == null && var1 == null) {
            return true;
        }
        return var0 != null && var1 != null ? var0.isItemStackEqual(var1) : false;
    }

    private boolean isItemStackEqual(ItemStack var1) {
        if (this.stackSize != var1.stackSize) {
            return false;
        }
        if (this.itemID != var1.itemID) {
            return false;
        }
        return this.itemDamage == var1.itemDamage;
    }

    public boolean isItemEqual(ItemStack var1) {
        return this.itemID == var1.itemID && this.itemDamage == var1.itemDamage;
    }

    public String func_20109_f() {
        return Item.itemsList[this.itemID].getItemNameIS(this);
    }

    public String toString() {
        return String.valueOf(this.stackSize) + "x" + Item.itemsList[this.itemID].getItemName() + "@" + this.itemDamage;
    }
}

