/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class ShapedRecipes
implements IRecipe {
    private int recipeWidth;
    private int recipeHeight;
    private ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    public final int recipeOutputItemID;

    public ShapedRecipes(int var1, int var2, ItemStack[] var3, ItemStack var4) {
        this.recipeOutputItemID = var4.itemID;
        this.recipeWidth = var1;
        this.recipeHeight = var2;
        this.recipeItems = var3;
        this.recipeOutput = var4;
    }

    @Override
    public ItemStack func_25117_b() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting var1) {
        int var2 = 0;
        while (var2 <= 3 - this.recipeWidth) {
            int var3 = 0;
            while (var3 <= 3 - this.recipeHeight) {
                if (this.func_21137_a(var1, var2, var3, true)) {
                    return true;
                }
                if (this.func_21137_a(var1, var2, var3, false)) {
                    return true;
                }
                ++var3;
            }
            ++var2;
        }
        return false;
    }

    private boolean func_21137_a(InventoryCrafting var1, int var2, int var3, boolean var4) {
        int var5 = 0;
        while (var5 < 3) {
            int var6 = 0;
            while (var6 < 3) {
                ItemStack var10;
                int var7 = var5 - var2;
                int var8 = var6 - var3;
                ItemStack var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
                    var9 = var4 ? this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth] : this.recipeItems[var7 + var8 * this.recipeWidth];
                }
                if ((var10 = var1.func_21103_b(var5, var6)) != null || var9 != null) {
                    if (var10 == null && var9 != null || var10 != null && var9 == null) {
                        return false;
                    }
                    if (var9.itemID != var10.itemID) {
                        return false;
                    }
                    if (var9.getItemDamage() != -1 && var9.getItemDamage() != var10.getItemDamage()) {
                        return false;
                    }
                }
                ++var6;
            }
            ++var5;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        return new ItemStack(this.recipeOutput.itemID, this.recipeOutput.stackSize, this.recipeOutput.getItemDamage());
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
}

