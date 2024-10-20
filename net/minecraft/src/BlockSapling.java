/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class BlockSapling
extends BlockFlower {
    protected BlockSapling(int var1, int var2) {
        super(var1, var2);
        float var3 = 0.4f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var3 * 2.0f, 0.5f + var3);
    }

    @Override
    public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
        super.updateTick(var1, var2, var3, var4, var5);
        if (var1.getBlockLightValue(var2, var3 + 1, var4) >= 9 && var5.nextInt(5) == 0) {
            int var6 = var1.getBlockMetadata(var2, var3, var4);
            if (var6 < 15) {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var6 + 1);
            } else {
                this.growTree(var1, var2, var3, var4, var5);
            }
        }
    }

    public void growTree(World var1, int var2, int var3, int var4, Random var5) {
        var1.setBlock(var2, var3, var4, 0);
        WorldGenerator var6 = new WorldGenTrees();
        if (var5.nextInt(10) == 0) {
            var6 = new WorldGenBigTree();
        }
        if (!((WorldGenerator)var6).generate(var1, var5, var2, var3, var4)) {
            var1.setBlock(var2, var3, var4, this.blockID);
        }
    }
}

