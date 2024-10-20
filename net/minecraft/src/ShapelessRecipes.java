/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class ShapelessRecipes
implements IRecipe {
    private final ItemStack recipeOutput;
    private final List recipeItems;

    public ShapelessRecipes(ItemStack var1, List var2) {
        this.recipeOutput = var1;
        this.recipeItems = var2;
    }

    @Override
    public ItemStack func_25117_b() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting var1) {
        ArrayList var2 = new ArrayList(this.recipeItems);
        int var3 = 0;
        while (var3 < 3) {
            int var4 = 0;
            while (var4 < 3) {
                ItemStack var5 = var1.func_21103_b(var4, var3);
                if (var5 != null) {
                    boolean var6 = false;
                    for (ItemStack var8 : var2) {
                        if (var5.itemID != var8.itemID || var8.getItemDamage() != -1 && var5.getItemDamage() != var8.getItemDamage()) continue;
                        var6 = true;
                        var2.remove(var8);
                        break;
                    }
                    if (!var6) {
                        return false;
                    }
                }
                ++var4;
            }
            ++var3;
        }
        return var2.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}

