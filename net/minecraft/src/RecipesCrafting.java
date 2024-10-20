/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;

public class RecipesCrafting {
    public void addRecipes(CraftingManager var1) {
        var1.addRecipe(new ItemStack(Block.crate), "###", "# #", "###", Character.valueOf('#'), Block.planks);
        var1.addRecipe(new ItemStack(Block.stoneOvenIdle), "###", "# #", "###", Character.valueOf('#'), Block.cobblestone);
        var1.addRecipe(new ItemStack(Block.workbench), "##", "##", Character.valueOf('#'), Block.planks);
        var1.addRecipe(new ItemStack(Block.sandStone), "##", "##", Character.valueOf('#'), Block.sand);
    }
}

