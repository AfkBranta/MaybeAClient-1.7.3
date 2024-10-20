package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenTaiga1
extends WorldGenerator {
    @Override
    public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
        int var6 = var2.nextInt(5) + 7;
        int var7 = var6 - var2.nextInt(2) - 3;
        int var8 = var6 - var7;
        int var9 = 1 + var2.nextInt(var8 + 1);
        boolean var10 = true;
        if (var4 >= 1 && var4 + var6 + 1 <= 128) {
            int var15;
            int var14;
            int var13;
            int var18;
            int var11 = var4;
            while (var11 <= var4 + 1 + var6 && var10) {
                boolean var12 = true;
                var18 = var11 - var4 < var7 ? 0 : var9;
                var13 = var3 - var18;
                while (var13 <= var3 + var18 && var10) {
                    var14 = var5 - var18;
                    while (var14 <= var5 + var18 && var10) {
                        if (var11 >= 0 && var11 < 128) {
                            var15 = var1.getBlockId(var13, var11, var14);
                            if (var15 != 0 && var15 != Block.leaves.blockID) {
                                var10 = false;
                            }
                        } else {
                            var10 = false;
                        }
                        ++var14;
                    }
                    ++var13;
                }
                ++var11;
            }
            if (!var10) {
                return false;
            }
            var11 = var1.getBlockId(var3, var4 - 1, var5);
            if ((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && var4 < 128 - var6 - 1) {
                var1.setBlock(var3, var4 - 1, var5, Block.dirt.blockID);
                var18 = 0;
                var13 = var4 + var6;
                while (var13 >= var4 + var7) {
                    var14 = var3 - var18;
                    while (var14 <= var3 + var18) {
                        var15 = var14 - var3;
                        int var16 = var5 - var18;
                        while (var16 <= var5 + var18) {
                            int var17 = var16 - var5;
                            if (!(Math.abs(var15) == var18 && Math.abs(var17) == var18 && var18 > 0 || Block.opaqueCubeLookup[var1.getBlockId(var14, var13, var16)])) {
                                var1.setBlockAndMetadata(var14, var13, var16, Block.leaves.blockID, 1);
                            }
                            ++var16;
                        }
                        ++var14;
                    }
                    if (var18 >= 1 && var13 == var4 + var7 + 1) {
                        --var18;
                    } else if (var18 < var9) {
                        ++var18;
                    }
                    --var13;
                }
                var13 = 0;
                while (var13 < var6 - 1) {
                    var14 = var1.getBlockId(var3, var4 + var13, var5);
                    if (var14 == 0 || var14 == Block.leaves.blockID) {
                        var1.setBlockAndMetadata(var3, var4 + var13, var5, Block.wood.blockID, 1);
                    }
                    ++var13;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

