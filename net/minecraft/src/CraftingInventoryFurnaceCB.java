package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;
import net.minecraft.src.TileEntityFurnace;

public class CraftingInventoryFurnaceCB
extends CraftingInventoryCB {
    private TileEntityFurnace furnace;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public CraftingInventoryFurnaceCB(IInventory var1, TileEntityFurnace var2) {
        this.furnace = var2;
        this.addSlot(new Slot(var2, 0, 56, 17));
        this.addSlot(new Slot(var2, 1, 56, 53));
        this.addSlot(new SlotFurnace(var2, 2, 116, 35));
        int var3 = 0;
        while (var3 < 3) {
            int var4 = 0;
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
    public void updateCraftingResults() {
        super.updateCraftingResults();
        int var1 = 0;
        while (var1 < this.field_20121_g.size()) {
            ICrafting var2 = (ICrafting)this.field_20121_g.get(var1);
            if (this.cookTime != this.furnace.furnaceCookTime) {
                var2.func_20158_a(this, 0, this.furnace.furnaceCookTime);
            }
            if (this.burnTime != this.furnace.furnaceBurnTime) {
                var2.func_20158_a(this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.itemBurnTime != this.furnace.currentItemBurnTime) {
                var2.func_20158_a(this, 2, this.furnace.currentItemBurnTime);
            }
            ++var1;
        }
        this.cookTime = this.furnace.furnaceCookTime;
        this.burnTime = this.furnace.furnaceBurnTime;
        this.itemBurnTime = this.furnace.currentItemBurnTime;
    }

    @Override
    public void func_20112_a(int var1, int var2) {
        if (var1 == 0) {
            this.furnace.furnaceCookTime = var2;
        }
        if (var1 == 1) {
            this.furnace.furnaceBurnTime = var2;
        }
        if (var1 == 2) {
            this.furnace.currentItemBurnTime = var2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer var1) {
        return this.furnace.canInteractWith(var1);
    }
}

