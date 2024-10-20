/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;

public class ItemSpade
extends ItemTool {
    private static Block[] blocksEffectiveAgainst = new Block[]{Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay};

    public ItemSpade(int var1, EnumToolMaterial var2) {
        super(var1, 1, var2, blocksEffectiveAgainst);
    }

    @Override
    public boolean canHarvestBlock(Block var1) {
        if (var1 == Block.snow) {
            return true;
        }
        return var1 == Block.blockSnow;
    }
}

