package net.minecraft.src;

import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public interface IRecipe {
    public boolean matches(InventoryCrafting var1);

    public ItemStack getCraftingResult(InventoryCrafting var1);

    public int getRecipeSize();

    public ItemStack func_25117_b();
}

