/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesWeapons {
    private String[][] recipePatterns = new String[][]{{"X", "X", "#"}};
    private Object[][] recipeItems = new Object[][]{{Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.swordWood, Item.swordStone, Item.swordSteel, Item.swordDiamond, Item.swordGold}};

    public void addRecipes(CraftingManager var1) {
        int var2 = 0;
        while (var2 < this.recipeItems[0].length) {
            Object var3 = this.recipeItems[0][var2];
            int var4 = 0;
            while (var4 < this.recipeItems.length - 1) {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                var1.addRecipe(new ItemStack(var5), this.recipePatterns[var4], Character.valueOf('#'), Item.stick, Character.valueOf('X'), var3);
                ++var4;
            }
            ++var2;
        }
        var1.addRecipe(new ItemStack(Item.bow, 1), " #X", "# X", " #X", Character.valueOf('X'), Item.silk, Character.valueOf('#'), Item.stick);
        var1.addRecipe(new ItemStack(Item.arrow, 4), "X", "#", "Y", Character.valueOf('Y'), Item.feather, Character.valueOf('X'), Item.flint, Character.valueOf('#'), Item.stick);
    }
}

