/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesIngots {
    private Object[][] recipeItems = new Object[][]{{Block.blockGold, new ItemStack(Item.ingotGold, 9)}, {Block.blockSteel, new ItemStack(Item.ingotIron, 9)}, {Block.blockDiamond, new ItemStack(Item.diamond, 9)}, {Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)}};

    public void addRecipes(CraftingManager var1) {
        int var2 = 0;
        while (var2 < this.recipeItems.length) {
            Block var3 = (Block)this.recipeItems[var2][0];
            ItemStack var4 = (ItemStack)this.recipeItems[var2][1];
            var1.addRecipe(new ItemStack(var3), "###", "###", "###", Character.valueOf('#'), var4);
            var1.addRecipe(var4, "#", Character.valueOf('#'), var3);
            ++var2;
        }
    }
}

