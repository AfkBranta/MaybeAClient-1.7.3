/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.World;

public class BlockMushroom
extends BlockFlower {
    protected BlockMushroom(int var1, int var2) {
        super(var1, var2);
        float var3 = 0.2f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var3 * 2.0f, 0.5f + var3);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int var1) {
        return Block.opaqueCubeLookup[var1];
    }

    @Override
    public boolean canBlockStay(World var1, int var2, int var3, int var4) {
        return var1.getBlockLightValue(var2, var3, var4) <= 13 && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
    }
}

