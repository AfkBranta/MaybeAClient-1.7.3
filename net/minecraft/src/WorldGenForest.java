/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenForest
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var6 = var2.nextInt(3) + 5;
        boolean var7 = true;
        if (var4 >= 1 && var4 + var6 + 1 <= 128) {
            int var12;
            int var11;
            int var10;
            int var8 = var4;
            while (var8 <= var4 + 1 + var6) {
                int var9 = 1;
                if (var8 == var4) {
                    var9 = 0;
                }
                if (var8 >= var4 + 1 + var6 - 2) {
                    var9 = 2;
                }
                var10 = var3 - var9;
                while (var10 <= var3 + var9 && var7) {
                    var11 = var5 - var9;
                    while (var11 <= var5 + var9 && var7) {
                        if (var8 >= 0 && var8 < 128) {
                            var12 = var1.getBlockId(var10, var8, var11);
                            if (var12 != 0 && var12 != Block.leaves.blockID) {
                                var7 = false;
                            }
                        } else {
                            var7 = false;
                        }
                        ++var11;
                    }
                    ++var10;
                }
                ++var8;
            }
            if (!var7) {
                return false;
            }
            var8 = var1.getBlockId(var3, var4 - 1, var5);
            if ((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && var4 < 128 - var6 - 1) {
                var1.setBlock(var3, var4 - 1, var5, Block.dirt.blockID);
                int var16 = var4 - 3 + var6;
                while (var16 <= var4 + var6) {
                    var10 = var16 - (var4 + var6);
                    var11 = 1 - var10 / 2;
                    var12 = var3 - var11;
                    while (var12 <= var3 + var11) {
                        int var13 = var12 - var3;
                        int var14 = var5 - var11;
                        while (var14 <= var5 + var11) {
                            int var15 = var14 - var5;
                            if ((Math.abs(var13) != var11 || Math.abs(var15) != var11 || var2.nextInt(2) != 0 && var10 != 0) && !Block.opaqueCubeLookup[var1.getBlockId(var12, var16, var14)]) {
                                var1.setBlockAndMetadata(var12, var16, var14, Block.leaves.blockID, 2);
                            }
                            ++var14;
                        }
                        ++var12;
                    }
                    ++var16;
                }
                var16 = 0;
                while (var16 < var6) {
                    var10 = var1.getBlockId(var3, var4 + var16, var5);
                    if (var10 == 0 || var10 == Block.leaves.blockID) {
                        var1.setBlockAndMetadata(var3, var4 + var16, var5, Block.wood.blockID, 2);
                    }
                    ++var16;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

